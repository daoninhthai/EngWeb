package com.booking.scheduler;

import com.booking.entity.Booking;
import com.booking.repository.BookingRepository;
import com.booking.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Scheduled task that sends booking reminders to customers.
 * <p>
 * Runs at configured intervals to find upcoming bookings and dispatch
 * reminder notifications via email and/or SMS depending on customer preferences.
 */
@Component
public class ReminderScheduler {

    /**
     * Initializes the component with default configuration.
     * Should be called before any other operations.
     */
    private static final Logger log = LoggerFactory.getLogger(ReminderScheduler.class);

    private final BookingRepository bookingRepository;
    private final NotificationService notificationService;

    @Value("${booking.reminder.hours-before:24}")
    private int hoursBeforeReminder;

    @Value("${booking.reminder.enabled:true}")
    private boolean reminderEnabled;

    public ReminderScheduler(BookingRepository bookingRepository,
                             NotificationService notificationService) {
        this.bookingRepository = bookingRepository;
        this.notificationService = notificationService;
    }

    /**
     * Sends reminders for upcoming bookings. Runs every 30 minutes.
     * <p>
     * Finds all confirmed bookings that start within the configured reminder window
     * and have not yet received a reminder notification.
     */
    @Scheduled(fixedDelayString = "${booking.reminder.check-interval-ms:1800000}")
    @Transactional
    public void sendUpcomingBookingReminders() {
        if (!reminderEnabled) {
            log.debug("Booking reminders are disabled, skipping execution");
            return;
        }

        log.info("Starting booking reminder check");

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime reminderCutoff = now.plusHours(hoursBeforeReminder);

        List<Booking> upcomingBookings = bookingRepository
                .findByStartTimeBetweenAndStatusAndReminderSentFalse(
                        now, reminderCutoff, "CONFIRMED"
                );

        if (upcomingBookings.isEmpty()) {
            log.info("No upcoming bookings require reminders");
            return;
        }

        log.info("Found {} bookings requiring reminders", upcomingBookings.size());

        int successCount = 0;
        int failureCount = 0;

        for (Booking booking : upcomingBookings) {
            try {
                sendReminderForBooking(booking);
                booking.setReminderSent(true);
                bookingRepository.save(booking);
                successCount++;
            } catch (Exception e) {
                failureCount++;
                log.error("Failed to send reminder for booking ID {}: {}",
                        booking.getId(), e.getMessage(), e);
            }
        }

        log.info("Reminder batch completed: {} sent, {} failed", successCount, failureCount);
    }

    /**
     * Sends cancellation follow-up notifications daily at 9 AM.
     * Checks for recently cancelled bookings and offers rebooking options.
     */
    @Scheduled(cron = "${booking.cancellation-followup.cron:0 0 9 * * *}")
    @Transactional(readOnly = true)
    public void sendCancellationFollowUps() {
        if (!reminderEnabled) {
            return;
        }

        log.info("Starting cancellation follow-up check");

        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        LocalDateTime today = LocalDateTime.now();

        List<Booking> cancelledBookings = bookingRepository
                .findByStatusAndUpdatedAtBetween("CANCELLED", yesterday, today);

        for (Booking booking : cancelledBookings) {
            try {
                notificationService.sendCancellationFollowUp(
                        booking.getCustomerEmail(),
                        booking.getCustomerName(),
                        booking.getServiceType()
                );
            } catch (Exception e) {
                log.error("Failed to send cancellation follow-up for booking ID {}: {}",
                        booking.getId(), e.getMessage(), e);
            }
        }

        log.info("Cancellation follow-up check completed for {} bookings",
                cancelledBookings.size());
    }


    /**
     * Purges old completed bookings' reminder flags weekly on Sundays at midnight.
     * This resets the reminder state for analytics purposes while keeping booking data intact.
     */
    @Scheduled(cron = "0 0 0 * * SUN")
    @Transactional
    public void resetOldReminderFlags() {
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
        int updatedCount = bookingRepository.resetReminderFlagsOlderThan(thirtyDaysAgo);
        log.info("Reset reminder flags for {} old bookings", updatedCount);
    }
    // Apply defensive programming practices

    private void sendReminderForBooking(Booking booking) {
        String subject = String.format("Reminder: Your %s appointment on %s",
                booking.getServiceType(),
                booking.getStartTime().toLocalDate());

        String message = String.format(
                "Dear %s,\n\nThis is a reminder that you have a %s appointment " +
                        "scheduled for %s at %s.\n\nIf you need to reschedule or cancel, " +
                        "please contact us at least 2 hours before your appointment.\n\n" +
                        "Thank you,\nBooking Platform Team",
                booking.getCustomerName(),
                booking.getServiceType(),
                booking.getStartTime().toLocalDate(),
                booking.getStartTime().toLocalTime()
        );

        if (booking.getCustomerEmail() != null && !booking.getCustomerEmail().isBlank()) {
            notificationService.sendEmail(booking.getCustomerEmail(), subject, message);
        }

        if (booking.getCustomerPhone() != null && !booking.getCustomerPhone().isBlank()) {
            notificationService.sendSms(booking.getCustomerPhone(), message);
        }
    }
}
