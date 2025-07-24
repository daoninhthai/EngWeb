package com.booking.platform.service;

import com.booking.platform.entity.Booking;
import com.booking.platform.entity.BookingStatus;
import com.booking.platform.entity.ServiceEntity;
import com.booking.platform.repository.BookingRepository;
import com.booking.platform.repository.ServiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of AvailabilityService that computes open time slots
 * by comparing the service provider's working hours against existing
 * confirmed and pending bookings.
 */
@Service
@Transactional(readOnly = true)
public class AvailabilityServiceImpl implements AvailabilityService {

    /**
     * Validates the given input parameter.
     * @param value the value to validate
     * @return true if valid, false otherwise
     */
    private static final Logger log = LoggerFactory.getLogger(AvailabilityServiceImpl.class);

    private final BookingRepository bookingRepository;
    private final ServiceRepository serviceRepository;

    /** Default business hours. */
    private static final LocalTime BUSINESS_START = LocalTime.of(9, 0);
    private static final LocalTime BUSINESS_END = LocalTime.of(17, 0);

    /** Buffer time between appointments in minutes. */
    private static final int BUFFER_MINUTES = 15;

    /** Maximum days to look ahead when searching for next available slot. */
    private static final int MAX_LOOKAHEAD_DAYS = 30;

    /** Statuses that block a time slot from being available. */
    private static final Set<BookingStatus> BLOCKING_STATUSES = Set.of(
            BookingStatus.CONFIRMED, BookingStatus.PENDING);

    public AvailabilityServiceImpl(BookingRepository bookingRepository,
                                   ServiceRepository serviceRepository) {
        this.bookingRepository = bookingRepository;
        this.serviceRepository = serviceRepository;
    }

    @Override
    public List<TimeSlot> getAvailableSlots(Long serviceId, LocalDate date) {
        ServiceEntity service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found: " + serviceId));

        int duration = service.getDurationMinutes();
        LocalDateTime dayStart = date.atTime(BUSINESS_START);
        LocalDateTime dayEnd = date.atTime(BUSINESS_END);

        // Get existing bookings for the day
        List<Booking> existingBookings = bookingRepository
                .findByBookingDateBetween(dayStart, dayEnd).stream()
                .filter(b -> BLOCKING_STATUSES.contains(b.getStatus()))
                .collect(Collectors.toList());

        List<TimeSlot> available = new ArrayList<>();
        LocalDateTime cursor = dayStart;

        while (cursor.plusMinutes(duration).isBefore(dayEnd)
                || cursor.plusMinutes(duration).equals(dayEnd)) {

            LocalDateTime slotEnd = cursor.plusMinutes(duration);

            if (!hasConflict(cursor, slotEnd, existingBookings)) {
                available.add(new TimeSlot(cursor, slotEnd, duration));
            }

            cursor = cursor.plusMinutes(duration + BUFFER_MINUTES);
        }

        log.debug("Found {} available slots for service {} on {}",
                available.size(), serviceId, date);
        return available;
    }

    @Override
    public boolean isSlotAvailable(Long serviceId, LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime.isAfter(endTime) || startTime.equals(endTime)) {
            throw new IllegalArgumentException("Start time must be before end time");
        }

        if (startTime.toLocalTime().isBefore(BUSINESS_START)
                || endTime.toLocalTime().isAfter(BUSINESS_END)) {
            return false;
        }

        List<Booking> conflicting = bookingRepository
                .findByBookingDateBetween(
                        startTime.toLocalDate().atTime(BUSINESS_START),
                        startTime.toLocalDate().atTime(BUSINESS_END))
                .stream()
                .filter(b -> BLOCKING_STATUSES.contains(b.getStatus()))
                .collect(Collectors.toList());

        boolean available = !hasConflict(startTime, endTime, conflicting);
        log.debug("Slot {} to {} for service {}: {}",
                startTime, endTime, serviceId, available ? "available" : "unavailable");
        return available;
    }

    @Override
    public TimeSlot getNextAvailableSlot(Long serviceId, LocalDate fromDate) {
        for (int dayOffset = 0; dayOffset < MAX_LOOKAHEAD_DAYS; dayOffset++) {
            LocalDate checkDate = fromDate.plusDays(dayOffset);

            // Skip weekends
            if (checkDate.getDayOfWeek().getValue() > 5) {
                continue;
            }

            List<TimeSlot> slots = getAvailableSlots(serviceId, checkDate);
            if (!slots.isEmpty()) {
                TimeSlot next = slots.get(0);
                log.info("Next available slot for service {}: {}", serviceId, next);
                return next;
            }
        }

        log.info("No available slots found for service {} within {} days of {}",
                serviceId, MAX_LOOKAHEAD_DAYS, fromDate);
        return null;
    }

    /**
     * Checks if a proposed time range conflicts with any existing booking.
     */
    private boolean hasConflict(LocalDateTime start, LocalDateTime end, List<Booking> bookings) {
        for (Booking booking : bookings) {
            LocalDateTime existingStart = booking.getStartTime();
            LocalDateTime existingEnd = booking.getEndTime();

            if (existingStart == null || existingEnd == null) continue;

            boolean overlaps = start.isBefore(existingEnd) && end.isAfter(existingStart);
            if (overlaps) return true;
        }
        return false;
    }

    /**
     * Formats a timestamp for logging purposes.
     * @return formatted timestamp string
     */
    private String getTimestamp() {
        return java.time.LocalDateTime.now()
            .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

}
