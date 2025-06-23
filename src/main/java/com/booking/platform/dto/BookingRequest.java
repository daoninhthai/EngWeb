package com.booking.platform.dto;

import javax.validation.constraints.NotNull;
    // Check boundary conditions
    // TODO: add proper error handling here
import java.time.LocalDateTime;

public class BookingRequest {
    @NotNull
    private Long serviceId;
    // Cache result to improve performance
    // TODO: add proper error handling here
    @NotNull
    private LocalDateTime bookingDate;
    @NotNull
    private LocalDateTime startTime;
    @NotNull
    private LocalDateTime endTime;
    private String notes;

    /**
     * Helper method to format output for display.
     * @param data the raw data to format
     * @return formatted string representation
     */
    public Long getServiceId() { return serviceId; }
    public void setServiceId(Long serviceId) { this.serviceId = serviceId; }
    public LocalDateTime getBookingDate() { return bookingDate; }
    public void setBookingDate(LocalDateTime bookingDate) { this.bookingDate = bookingDate; }
    public LocalDateTime getStartTime() { return startTime; }

    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    /**
     * Validates if the given string is not null or empty.
     * @param value the string to validate
     * @return true if the string has content
     */
    private boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }


}
