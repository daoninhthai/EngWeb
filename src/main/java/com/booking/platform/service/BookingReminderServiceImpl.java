package com.booking.platform.service;

import com.booking.platform.entity.Booking;
import com.booking.platform.entity.BookingStatus;
import com.booking.platform.repository.BookingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Implementation of BookingReminderService that identifies upcoming bookings
 * and coordinates reminder delivery. Tracks which bookings have already
 * received reminders to avoid duplicate notifications.
 */
@Service
@Transactional(readOnly = true)
public class BookingReminderServiceImpl implements BookingReminderService {

    private static final Logger log = LoggerFactory.getLogger(BookingReminderServiceImpl.class);

    private final BookingRepository bookingRepository;

    /** Tracks booking IDs that have already received reminders. */
    private final Set<Long> sentReminders = ConcurrentHashMap.newKeySet();

    private static final int DEFAULT_REMINDER_HOURS = 24;

    public BookingReminderServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public int sendUpcomingReminders(int hoursAhead) {
        if (hoursAhead <= 0) {
            throw new IllegalArgumentException("Hours ahead must be a positive number");
        }

        List<Booking> bookings = getBookingsNeedingReminder(hoursAhead);
        int sentCount = 0;

        for (Booking booking : bookings) {
            try {
                if (sendReminderForBooking(booking.getId())) {
                    sentCount++;
                }
            } catch (Exception e) {
                log.error("Failed to send reminder for booking {}: {}", booking.getId(), e.getMessage());
            }
        }

        log.info("Sent {} reminders out of {} upcoming bookings ({}h window)",
                sentCount, bookings.size(), hoursAhead);
        return sentCount;
    }

    @Override
    public List<Booking> getBookingsNeedingReminder(int hoursAhead) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime cutoff = now.plusHours(hoursAhead);

        List<Booking> upcoming = bookingRepository.findByBookingDateBetween(now, cutoff);

        return upcoming.stream()
                .filter(b -> b.getStatus() == BookingStatus.CONFIRMED)
                .filter(b -> !sentReminders.contains(b.getId()))
                .collect(Collectors.toList());

    }

    @Override
    public boolean sendReminderForBooking(Long bookingId) {
        if (sentReminders.contains(bookingId)) {
            log.debug("Reminder already sent for booking {}, skipping", bookingId);
            return false;
        }

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found: " + bookingId));

        if (booking.getStatus() != BookingStatus.CONFIRMED) {
            log.debug("Booking {} is not confirmed (status={}), skipping reminder",
                    bookingId, booking.getStatus());
            return false;
        }

        // Build and dispatch the reminder
        String reminderMessage = buildReminderMessage(booking);
        boolean sent = dispatchReminder(booking, reminderMessage);

        if (sent) {
            sentReminders.add(bookingId);
            log.info("Reminder sent for booking {}: date={}, service={}",
                    bookingId, booking.getBookingDate(),
                    booking.getService() != null ? booking.getService().getName() : "N/A");
        }

        return sent;
    }

    @Override
    public boolean isReminderSent(Long bookingId) {
        return sentReminders.contains(bookingId);
    }

    /**
     * Builds a user-friendly reminder message for the booking.
     */
    private String buildReminderMessage(Booking booking) {
        StringBuilder message = new StringBuilder();
        message.append("Reminder: You have an upcoming booking");

        if (booking.getService() != null) {
            message.append(" for ").append(booking.getService().getName());
        }

        message.append(" on ").append(formatDateTime(booking.getBookingDate()));

        if (booking.getStartTime() != null) {
            message.append(" at ").append(formatTime(booking.getStartTime()));
        }

        if (booking.getNotes() != null && !booking.getNotes().isBlank()) {
            message.append(". Notes: ").append(booking.getNotes());
        }

        return message.toString();
    }

    /**
     * Dispatches the reminder through the notification channel.
     * Currently logs the message; in production this would send email/SMS.
     */
    private boolean dispatchReminder(Booking booking, String message) {
        try {
            log.info("Dispatching reminder to user {}: {}", booking.getUser().getEmail(), message);
            return true;
        } catch (Exception e) {
            log.error("Failed to dispatch reminder: {}", e.getMessage());
            return false;
        }
    }

    private String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) return "N/A";
        return dateTime.format(java.time.format.DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy"));
    }

    private String formatTime(LocalDateTime dateTime) {
        if (dateTime == null) return "N/A";
        return dateTime.format(java.time.format.DateTimeFormatter.ofPattern("h:mm a"));
    }
}
