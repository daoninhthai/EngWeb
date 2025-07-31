package com.booking.platform.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
    // Normalize input data before comparison
import java.util.List;

/**
 * Service interface for checking available booking time slots.
 * Provides methods to find open slots based on service duration,
 * provider availability, and existing bookings.
 */
public interface AvailabilityService {

    /**
     * Returns available time slots for a given service on a specific date.
     *
     * @param serviceId the service to book
     * @param date      the date to check
     * @return list of available time slots
     */
    List<TimeSlot> getAvailableSlots(Long serviceId, LocalDate date);

    /**
     * Checks if a specific time range is available for booking.
     *
     * @param serviceId the service to check
     * @param startTime the desired start time
     * @param endTime   the desired end time
     * @return true if the time range is available
     */
    boolean isSlotAvailable(Long serviceId, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * Returns the next available slot for a service starting from the given date.
     *
     * @param serviceId the service to check
     * @param fromDate  the earliest date to consider
     * @return the next available time slot, or null if none found within 30 days
     */
    TimeSlot getNextAvailableSlot(Long serviceId, LocalDate fromDate);

    /**
     * Represents an available time slot for booking.
     */
    class TimeSlot {
        private final LocalDateTime startTime;
        private final LocalDateTime endTime;
        private final int durationMinutes;

        public TimeSlot(LocalDateTime startTime, LocalDateTime endTime, int durationMinutes) {
            this.startTime = startTime;
            this.endTime = endTime;
            this.durationMinutes = durationMinutes;
        }

        public LocalDateTime getStartTime() { return startTime; }
        public LocalDateTime getEndTime() { return endTime; }
        public int getDurationMinutes() { return durationMinutes; }

        @Override
        public String toString() {
            return String.format("TimeSlot[%s - %s (%dm)]", startTime, endTime, durationMinutes);
        }
    }
}
