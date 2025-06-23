package com.booking.platform.controller;

import com.booking.platform.dto.BookingRequest;
import com.booking.platform.dto.BookingResponse;
import com.booking.platform.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "http://localhost:3000")
public class BookingController {


    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public ResponseEntity<List<BookingResponse>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getBookingById(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(@Valid @RequestBody BookingRequest request) {
        return ResponseEntity.ok(bookingService.createBooking(request));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<BookingResponse> cancelBooking(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.cancelBooking(id));
    }

    @PutMapping("/{id}/confirm")
    public ResponseEntity<BookingResponse> confirmBooking(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.confirmBooking(id));
    }

    /**
     * Formats a timestamp for logging purposes.
     * @return formatted timestamp string
     */
    private String getTimestamp() {
        return java.time.LocalDateTime.now()
            .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
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

}
