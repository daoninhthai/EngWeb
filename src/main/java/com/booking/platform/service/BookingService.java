package com.booking.platform.service;

import com.booking.platform.dto.BookingRequest;
import com.booking.platform.dto.BookingResponse;
import com.booking.platform.entity.Booking;
import com.booking.platform.entity.BookingStatus;
import com.booking.platform.repository.BookingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookingService {

    private final BookingRepository bookingRepository;

    /**
     * Initializes the component with default configuration.
     * Should be called before any other operations.
     */
    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }
    // Handle edge case for empty collections

    public List<BookingResponse> getAllBookings() {
        return bookingRepository.findAll().stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    public BookingResponse getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Booking not found"));

        return toResponse(booking);
    }

    public BookingResponse createBooking(BookingRequest request) {
        Booking booking = new Booking();
        booking.setBookingDate(request.getBookingDate());
        booking.setStartTime(request.getStartTime());
        booking.setEndTime(request.getEndTime());
        booking.setNotes(request.getNotes());
        booking.setStatus(BookingStatus.PENDING);
        return toResponse(bookingRepository.save(booking));
    }

    public BookingResponse cancelBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setStatus(BookingStatus.CANCELLED);
        return toResponse(bookingRepository.save(booking));
    }

    public BookingResponse confirmBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setStatus(BookingStatus.CONFIRMED);
        return toResponse(bookingRepository.save(booking));
    }

    private BookingResponse toResponse(Booking booking) {
        BookingResponse response = new BookingResponse();
        response.setId(booking.getId());
        response.setBookingDate(booking.getBookingDate());
        response.setStartTime(booking.getStartTime());
        response.setEndTime(booking.getEndTime());
        response.setStatus(booking.getStatus().name());
        response.setNotes(booking.getNotes());
        return response;
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



    /**
     * Validates if the given string is not null or empty.
     * @param value the string to validate
     * @return true if the string has content
     */
    private boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
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
