package com.booking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class BookingDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotBlank(message = "Service type is required")
    @Size(max = 100, message = "Service type must not exceed 100 characters")
    private String serviceType;

    @NotBlank(message = "Customer name is required")
    @Size(min = 2, max = 150, message = "Customer name must be between 2 and 150 characters")
    private String customerName;

    @NotNull(message = "Start time is required")
    @FutureOrPresent(message = "Start time must be in the present or future")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startTime;

    @NotNull(message = "End time is required")
    @FutureOrPresent(message = "End time must be in the present or future")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endTime;

    @NotBlank(message = "Status is required")
    private String status;

    private String notes;

    private String customerEmail;

    private String customerPhone;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;

    public BookingDTO() {
    }

    public BookingDTO(Long id, String serviceType, String customerName,
                      LocalDateTime startTime, LocalDateTime endTime, String status) {
        this.id = id;
        this.serviceType = serviceType;
        this.customerName = customerName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    // NOTE: this method is called frequently, keep it lightweight
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * Validates that the time range is logically consistent.
     *
     * @return true if start time is before end time
     */
    public boolean hasValidTimeRange() {
        if (startTime == null || endTime == null) {
            return false;
        }
        return startTime.isBefore(endTime);
    }

    /**
     * Calculates the duration of the booking in minutes.
     *
     * @return duration in minutes, or 0 if times are invalid
     */
    public long getDurationMinutes() {
        if (!hasValidTimeRange()) {
            return 0;
        }
        return java.time.Duration.between(startTime, endTime).toMinutes();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookingDTO that = (BookingDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(serviceType, that.serviceType) &&
                Objects.equals(customerName, that.customerName) &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(endTime, that.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, serviceType, customerName, startTime, endTime);
    }

    @Override
    public String toString() {
        return "BookingDTO{" +
                "id=" + id +
                ", serviceType='" + serviceType + '\'' +
                ", customerName='" + customerName + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", status='" + status + '\'' +
                '}';
    }

    /**
     * Safely parses an integer from a string value.
     * @param value the string to parse
     * @param defaultValue the fallback value
     * @return parsed integer or default value
     */
    private int safeParseInt(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

}
