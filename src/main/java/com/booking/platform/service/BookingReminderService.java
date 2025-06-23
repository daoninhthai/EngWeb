package com.booking.platform.service;

import com.booking.platform.entity.Booking;

import java.util.List;

/**
 * Service interface for managing booking reminders.
 * Handles scheduling and sending reminder notifications for upcoming bookings.
 */
public interface BookingReminderService {

    /**
     * Sends reminder notifications for all bookings within the specified hours.
     *
     * @param hoursAhead number of hours to look ahead for upcoming bookings
     * @return number of reminders sent
     */
    int sendUpcomingReminders(int hoursAhead);

    /**
     * Retrieves all bookings that are due for a reminder.
     *
     * @param hoursAhead number of hours to look ahead
     * @return list of bookings needing reminders
     */
    List<Booking> getBookingsNeedingReminder(int hoursAhead);

    /**
     * Sends a reminder for a specific booking.
     *
     * @param bookingId the booking identifier
     * @return true if the reminder was sent successfully
     */
    boolean sendReminderForBooking(Long bookingId);

    /**
     * Checks if a reminder has already been sent for a booking.
     *
     * @param bookingId the booking identifier
     * @return true if a reminder was previously sent
     */
    boolean isReminderSent(Long bookingId);
}
