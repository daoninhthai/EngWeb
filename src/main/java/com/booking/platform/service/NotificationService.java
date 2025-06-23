package com.booking.platform.service;

import com.booking.platform.entity.Booking;
import com.booking.platform.entity.BookingStatus;
import com.booking.platform.entity.User;
import com.booking.platform.repository.BookingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Service for sending booking-related notifications via email.
 * Handles booking confirmation, cancellation, and reminder notifications.
 * Maintains an in-memory notification log for tracking delivery status.
 */
@Service
public class NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    private final BookingRepository bookingRepository;

    private final Map<Long, NotificationRecord> notificationLog = new ConcurrentHashMap<>();
    private final AtomicLong notificationIdGenerator = new AtomicLong(1);

    private static final DateTimeFormatter DT_FORMAT =
            DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy 'at' h:mm a");

    public NotificationService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    /**
     * Sends a booking confirmation notification.
     *
     * @param bookingId the booking that was confirmed
     * @return the notification record
     */
    public NotificationRecord sendBookingConfirmation(Long bookingId) {
        Booking booking = getBookingOrThrow(bookingId);
        User user = booking.getUser();

        String subject = "Booking Confirmed - " + getServiceName(booking);
        String body = buildConfirmationBody(booking);

        return sendNotification(user, subject, body, NotificationType.CONFIRMATION);
    }

    /**
     * Sends a booking cancellation notification.
     *
     * @param bookingId the booking that was cancelled
     * @return the notification record
     */
    public NotificationRecord sendCancellationNotice(Long bookingId) {
        Booking booking = getBookingOrThrow(bookingId);
        User user = booking.getUser();

        String subject = "Booking Cancelled - " + getServiceName(booking);
        String body = buildCancellationBody(booking);

        return sendNotification(user, subject, body, NotificationType.CANCELLATION);
    }

    /**
     * Sends a status change notification.
     *
     * @param bookingId the booking whose status changed
     * @param oldStatus the previous status
     * @param newStatus the new status
     * @return the notification record
     */
    public NotificationRecord sendStatusChangeNotification(
            Long bookingId, BookingStatus oldStatus, BookingStatus newStatus) {
        Booking booking = getBookingOrThrow(bookingId);
        User user = booking.getUser();

        String subject = "Booking Status Updated - " + getServiceName(booking);
        String body = String.format(
                "Your booking for %s has been updated from %s to %s.\n\nDate: %s",
                getServiceName(booking),
                oldStatus.name(),
                newStatus.name(),
                formatDateTime(booking.getBookingDate()));

        return sendNotification(user, subject, body, NotificationType.STATUS_CHANGE);
    }

    /**
     * Returns all notifications sent for a given booking.
     */
    public List<NotificationRecord> getNotificationsForBooking(Long bookingId) {
        List<NotificationRecord> records = new ArrayList<>();
        for (NotificationRecord record : notificationLog.values()) {
            if (bookingId.equals(record.bookingId)) {
                records.add(record);
            }
        }
        return records;
    }

    /**
     * Returns the total count of notifications sent.
     */
    public long getTotalNotificationCount() {
        return notificationLog.size();
    }

    private NotificationRecord sendNotification(
            User user, String subject, String body, NotificationType type) {

        String recipientEmail = user != null ? user.getEmail() : "unknown";
        String recipientName = user != null ? user.getFullName() : "Customer";

        // In production, this would integrate with an email service (e.g., SendGrid, SES)
        log.info("Sending {} notification to {}: subject='{}'",
                type.name(), recipientEmail, subject);

        Long notifId = notificationIdGenerator.getAndIncrement();
        NotificationRecord record = new NotificationRecord(
                notifId, recipientEmail, recipientName, subject,
                body, type, LocalDateTime.now(), null, true);

        notificationLog.put(notifId, record);
        return record;
    }

    private String buildConfirmationBody(Booking booking) {
        StringBuilder sb = new StringBuilder();
        sb.append("Your booking has been confirmed!\n\n");
        sb.append("Service: ").append(getServiceName(booking)).append("\n");
        sb.append("Date: ").append(formatDateTime(booking.getBookingDate())).append("\n");

        if (booking.getStartTime() != null && booking.getEndTime() != null) {
            sb.append("Time: ").append(formatTime(booking.getStartTime()))
                    .append(" - ").append(formatTime(booking.getEndTime())).append("\n");
        }

        if (booking.getNotes() != null && !booking.getNotes().isBlank()) {
            sb.append("Notes: ").append(booking.getNotes()).append("\n");
        }

        sb.append("\nThank you for your booking!");
        return sb.toString();
    }

    private String buildCancellationBody(Booking booking) {
        return String.format(
                "Your booking for %s on %s has been cancelled.\n\n" +
                "If this was not intended, please contact us to rebook.",
                getServiceName(booking),
                formatDateTime(booking.getBookingDate()));
    }

    private Booking getBookingOrThrow(Long bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found: " + bookingId));
    }

    private String getServiceName(Booking booking) {
        return booking.getService() != null ? booking.getService().getName() : "Service";
    }

    private String formatDateTime(LocalDateTime dt) {
        return dt != null ? dt.format(DT_FORMAT) : "N/A";
    }

    private String formatTime(LocalDateTime dt) {
        return dt != null ? dt.format(DateTimeFormatter.ofPattern("h:mm a")) : "N/A";
    }

    public enum NotificationType {
        CONFIRMATION, CANCELLATION, REMINDER, STATUS_CHANGE
    }

    /**
     * Record of a sent notification.
     */
    public static class NotificationRecord {
        private final Long id;
        private final String recipientEmail;
        private final String recipientName;
        private final String subject;
        private final String body;
        private final NotificationType type;
        private final LocalDateTime sentAt;
        private final Long bookingId;
        private final boolean delivered;

        public NotificationRecord(Long id, String recipientEmail, String recipientName,
                                  String subject, String body, NotificationType type,
                                  LocalDateTime sentAt, Long bookingId, boolean delivered) {
            this.id = id;
            this.recipientEmail = recipientEmail;
            this.recipientName = recipientName;
            this.subject = subject;
            this.body = body;
            this.type = type;
            this.sentAt = sentAt;
            this.bookingId = bookingId;
            this.delivered = delivered;
        }

        public Long getId() { return id; }
        public String getRecipientEmail() { return recipientEmail; }
        public String getRecipientName() { return recipientName; }
        public String getSubject() { return subject; }
        public String getBody() { return body; }
        public NotificationType getType() { return type; }
        public LocalDateTime getSentAt() { return sentAt; }
        public Long getBookingId() { return bookingId; }
        public boolean isDelivered() { return delivered; }
    }
}
