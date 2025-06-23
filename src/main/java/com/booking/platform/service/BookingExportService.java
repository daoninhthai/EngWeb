package com.booking.platform.service;

import com.booking.platform.entity.Booking;
import com.booking.platform.entity.BookingStatus;
import com.booking.platform.repository.BookingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for exporting booking data to CSV format.
 * Supports filtered exports by status, date range, and user.
 */
@Service
@Transactional(readOnly = true)
public class BookingExportService {

    private static final Logger log = LoggerFactory.getLogger(BookingExportService.class);

    private final BookingRepository bookingRepository;

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATETIME_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final String SEPARATOR = ",";
    private static final String NEWLINE = "\r\n";
    private static final byte[] UTF8_BOM = {(byte) 0xEF, (byte) 0xBB, (byte) 0xBF};

    private static final String[] HEADERS = {
            "Booking ID", "Customer Name", "Customer Email", "Service",
            "Booking Date", "Start Time", "End Time", "Status", "Notes"
    };

    public BookingExportService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    /**
     * Exports all bookings to CSV.
     *
     * @return byte array containing the CSV data
     */
    public byte[] exportAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        log.info("Exporting all {} bookings to CSV", bookings.size());
        return buildCsv(bookings);
    }

    /**
     * Exports bookings filtered by status.
     *
     * @param status the booking status to filter by
     * @return byte array containing the CSV data
     */
    public byte[] exportByStatus(BookingStatus status) {
        List<Booking> bookings = bookingRepository.findByStatus(status);
        log.info("Exporting {} bookings with status {} to CSV", bookings.size(), status);
        return buildCsv(bookings);
    }

    /**
     * Exports bookings within a specific date range.
     *
     * @param from start date (inclusive)
     * @param to   end date (inclusive)
     * @return byte array containing the CSV data
     */
    public byte[] exportByDateRange(LocalDate from, LocalDate to) {
        LocalDateTime start = from.atStartOfDay();
        LocalDateTime end = to.atTime(23, 59, 59);

        List<Booking> bookings = bookingRepository.findByBookingDateBetween(start, end);
        log.info("Exporting {} bookings from {} to {} to CSV", bookings.size(), from, to);
        return buildCsv(bookings);
    }

    /**
     * Exports bookings for a specific user.
     *
     * @param userId the user identifier
     * @return byte array containing the CSV data
     */
    public byte[] exportByUser(Long userId) {
        List<Booking> bookings = bookingRepository.findByUserId(userId);
        log.info("Exporting {} bookings for user {} to CSV", bookings.size(), userId);
        return buildCsv(bookings);
    }

    /**
     * Generates a timestamped filename for the export.
     */
    public String generateFilename(String prefix) {
        return String.format("%s_%s.csv", prefix, LocalDate.now().format(DATE_FMT));
    }

    /**
     * Returns the content type for CSV downloads.
     */
    public String getContentType() {
        return "text/csv; charset=UTF-8";
    }

    private byte[] buildCsv(List<Booking> bookings) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            baos.write(UTF8_BOM);

            try (PrintWriter writer = new PrintWriter(
                    new OutputStreamWriter(baos, StandardCharsets.UTF_8))) {

                writer.print(String.join(SEPARATOR, HEADERS));
                writer.print(NEWLINE);

                for (Booking booking : bookings) {
                    writer.print(formatRow(booking));
                    writer.print(NEWLINE);
                }

                writer.flush();
            }

            log.debug("CSV built: {} bytes for {} bookings", baos.size(), bookings.size());
            return baos.toByteArray();

        } catch (Exception e) {
            log.error("Failed to build booking CSV export: {}", e.getMessage(), e);
            throw new RuntimeException("Booking CSV export failed", e);
        }
    }

    private String formatRow(Booking booking) {
        String customerName = booking.getUser() != null ? booking.getUser().getFullName() : "";
        String customerEmail = booking.getUser() != null ? booking.getUser().getEmail() : "";
        String serviceName = booking.getService() != null ? booking.getService().getName() : "";

        return String.join(SEPARATOR,
                escape(String.valueOf(booking.getId())),
                escape(customerName),
                escape(customerEmail),
                escape(serviceName),
                escape(formatDateTime(booking.getBookingDate())),
                escape(formatDateTime(booking.getStartTime())),
                escape(formatDateTime(booking.getEndTime())),
                escape(booking.getStatus() != null ? booking.getStatus().name() : ""),
                escape(booking.getNotes())
        );
    }

    private String escape(String value) {
        if (value == null) return "";
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }

    private String formatDateTime(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(DATETIME_FMT) : "";
    }
}
