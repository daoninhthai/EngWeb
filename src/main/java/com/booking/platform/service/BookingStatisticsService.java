package com.booking.platform.service;

import com.booking.platform.entity.Booking;
import com.booking.platform.entity.BookingStatus;
import com.booking.platform.repository.BookingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service for computing booking statistics and analytics.
 * Provides metrics such as booking trends, popular services,
 * cancellation rates, and utilization analysis.
 */
@Service
@Transactional(readOnly = true)
public class BookingStatisticsService {

    private static final Logger log = LoggerFactory.getLogger(BookingStatisticsService.class);

    private final BookingRepository bookingRepository;

    public BookingStatisticsService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    /**
     * Returns a comprehensive summary of booking statistics.
     *
     * @return the booking statistics summary
     */
    public BookingStats getOverallStats() {
        List<Booking> allBookings = bookingRepository.findAll();

        long total = allBookings.size();
        long confirmed = countByStatus(allBookings, BookingStatus.CONFIRMED);
        long completed = countByStatus(allBookings, BookingStatus.COMPLETED);
        long cancelled = countByStatus(allBookings, BookingStatus.CANCELLED);
        long pending = countByStatus(allBookings, BookingStatus.PENDING);
        long noShow = countByStatus(allBookings, BookingStatus.NO_SHOW);

        double cancellationRate = total > 0
                ? (double) cancelled / total * 100 : 0.0;
        double completionRate = total > 0
                ? (double) completed / total * 100 : 0.0;

        log.info("Booking stats: total={}, completed={}, cancelled={}, cancellationRate={}%",
                total, completed, cancelled, round(cancellationRate));

        return new BookingStats(total, confirmed, completed, cancelled, pending,
                noShow, round(cancellationRate), round(completionRate));
    }

    /**
     * Returns booking counts grouped by day of week.
     *
     * @return map of day names to booking counts
     */
    public Map<String, Long> getBookingsByDayOfWeek() {
        List<Booking> bookings = bookingRepository.findAll();
        Map<String, Long> dayCount = new LinkedHashMap<>();

        for (DayOfWeek day : DayOfWeek.values()) {
            dayCount.put(day.name(), 0L);
        }

        for (Booking booking : bookings) {
            if (booking.getBookingDate() != null) {
                String dayName = booking.getBookingDate().getDayOfWeek().name();
                dayCount.merge(dayName, 1L, Long::sum);
            }
        }

        return dayCount;
    }

    /**
     * Returns booking trends per month for the last N months.
     *
     * @param months number of months to analyze
     * @return ordered map of month labels to booking counts
     */
    public LinkedHashMap<String, Long> getMonthlyBookingTrend(int months) {
        LinkedHashMap<String, Long> trend = new LinkedHashMap<>();
        LocalDate now = LocalDate.now();

        for (int i = months - 1; i >= 0; i--) {
            LocalDate monthStart = now.minusMonths(i).withDayOfMonth(1);
            LocalDate monthEnd = monthStart.plusMonths(1).minusDays(1);

            LocalDateTime start = monthStart.atStartOfDay();
            LocalDateTime end = monthEnd.atTime(23, 59, 59);

            long count = bookingRepository.findByBookingDateBetween(start, end).size();
            String label = monthStart.getMonth().name().substring(0, 3) + " " + monthStart.getYear();
            trend.put(label, count);
        }

        return trend;
    }

    /**
     * Returns the top N most booked services.
     *
     * @param limit maximum number of services to return
     * @return list of service name and booking count pairs
     */
    public List<ServiceBookingCount> getTopServices(int limit) {
        List<Booking> bookings = bookingRepository.findAll();

        Map<String, Long> serviceCount = bookings.stream()
                .filter(b -> b.getService() != null)
                .collect(Collectors.groupingBy(
                        b -> b.getService().getName(),
                        Collectors.counting()));

        return serviceCount.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(limit)
                .map(e -> new ServiceBookingCount(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

    /**
     * Returns the average booking duration in minutes.
     */
    public double getAverageBookingDuration() {
        List<Booking> bookings = bookingRepository.findAll();

        OptionalDouble avgMinutes = bookings.stream()
                .filter(b -> b.getStartTime() != null && b.getEndTime() != null)
                .mapToLong(b -> ChronoUnit.MINUTES.between(b.getStartTime(), b.getEndTime()))
                .average();

        return round(avgMinutes.orElse(0.0));
    }

    /**
     * Returns booking counts grouped by status.
     */
    public Map<String, Long> getBookingCountByStatus() {
        List<Booking> bookings = bookingRepository.findAll();
        Map<String, Long> statusCount = new LinkedHashMap<>();

        for (BookingStatus status : BookingStatus.values()) {
            statusCount.put(status.name(), countByStatus(bookings, status));
        }

        return statusCount;
    }

    private long countByStatus(List<Booking> bookings, BookingStatus status) {
        return bookings.stream()
                .filter(b -> b.getStatus() == status)
                .count();
    }

    private double round(double value) {
        return BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    /**
     * Summary of booking statistics.
     */
    public static class BookingStats {
        private final long totalBookings;
        private final long confirmed;
        private final long completed;
        private final long cancelled;
        private final long pending;
        private final long noShow;
        private final double cancellationRate;
        private final double completionRate;

        public BookingStats(long totalBookings, long confirmed, long completed,
                            long cancelled, long pending, long noShow,
                            double cancellationRate, double completionRate) {
            this.totalBookings = totalBookings;
            this.confirmed = confirmed;
            this.completed = completed;
            this.cancelled = cancelled;
            this.pending = pending;
            this.noShow = noShow;
            this.cancellationRate = cancellationRate;
            this.completionRate = completionRate;
        }

        public long getTotalBookings() { return totalBookings; }
        public long getConfirmed() { return confirmed; }
        public long getCompleted() { return completed; }
        public long getCancelled() { return cancelled; }
        public long getPending() { return pending; }
        public long getNoShow() { return noShow; }
        public double getCancellationRate() { return cancellationRate; }
        public double getCompletionRate() { return completionRate; }
    }

    /**
     * DTO for service booking count.
     */
    public static class ServiceBookingCount {
        private final String serviceName;
        private final long bookingCount;

        public ServiceBookingCount(String serviceName, long bookingCount) {
            this.serviceName = serviceName;
            this.bookingCount = bookingCount;
        }

        public String getServiceName() { return serviceName; }
        public long getBookingCount() { return bookingCount; }
    }
}
