package com.booking.platform.service;

import com.booking.platform.entity.ServiceEntity;
import com.booking.platform.repository.ServiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementation of PricingService that applies dynamic pricing rules
 * including peak hour surcharges, weekend premiums, off-peak discounts,
 * and promotional code reductions.
 */
@Service
@Transactional(readOnly = true)
public class PricingServiceImpl implements PricingService {

    private static final Logger log = LoggerFactory.getLogger(PricingServiceImpl.class);

    private final ServiceRepository serviceRepository;

    /** Peak hours: 10 AM - 2 PM weekdays. */
    private static final LocalTime PEAK_START = LocalTime.of(10, 0);
    private static final LocalTime PEAK_END = LocalTime.of(14, 0);

    /** Surcharge/discount rates. */
    private static final BigDecimal PEAK_SURCHARGE_RATE = new BigDecimal("0.15");
    private static final BigDecimal WEEKEND_SURCHARGE_RATE = new BigDecimal("0.20");
    private static final BigDecimal OFF_PEAK_DISCOUNT_RATE = new BigDecimal("0.10");

    /** In-memory promo code store: code -> discount percentage. */
    private final Map<String, BigDecimal> promoCodes = new ConcurrentHashMap<>();

    public PricingServiceImpl(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
        initializePromoCodes();
    }

    @Override
    public PriceBreakdown calculatePrice(Long serviceId, LocalDateTime startTime, String promoCode) {
        ServiceEntity service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found: " + serviceId));

        BigDecimal basePrice = service.getPrice();
        BigDecimal surcharge = BigDecimal.ZERO;
        BigDecimal discount = BigDecimal.ZERO;
        String appliedPromo = null;

        // Apply peak hour surcharge
        if (isPeakHour(startTime)) {
            surcharge = basePrice.multiply(PEAK_SURCHARGE_RATE)
                    .setScale(2, RoundingMode.HALF_UP);
            log.debug("Applied peak hour surcharge: +{}", surcharge);
        }

        // Apply weekend surcharge
        if (isWeekend(startTime)) {
            BigDecimal weekendSurcharge = basePrice.multiply(WEEKEND_SURCHARGE_RATE)
                    .setScale(2, RoundingMode.HALF_UP);
            surcharge = surcharge.add(weekendSurcharge);
            log.debug("Applied weekend surcharge: +{}", weekendSurcharge);
        }

        // Apply off-peak discount (only if no surcharges)
        if (surcharge.compareTo(BigDecimal.ZERO) == 0 && isOffPeak(startTime)) {
            discount = basePrice.multiply(OFF_PEAK_DISCOUNT_RATE)
                    .setScale(2, RoundingMode.HALF_UP);
            log.debug("Applied off-peak discount: -{}", discount);
        }

        // Apply promo code discount
        if (promoCode != null && !promoCode.isBlank()) {
            BigDecimal promoDiscount = calculatePromoDiscount(basePrice, promoCode);
            if (promoDiscount.compareTo(BigDecimal.ZERO) > 0) {
                discount = discount.add(promoDiscount);
                appliedPromo = promoCode.toUpperCase();
                log.debug("Applied promo code '{}': -{}", promoCode, promoDiscount);
            }
        }

        BigDecimal finalPrice = basePrice.add(surcharge).subtract(discount);
        if (finalPrice.compareTo(BigDecimal.ZERO) < 0) {
            finalPrice = BigDecimal.ZERO;
        }

        log.info("Price calculated for service {}: base={}, surcharge={}, discount={}, final={}",
                serviceId, basePrice, surcharge, discount, finalPrice);

        return new PriceBreakdown(basePrice, surcharge, discount, finalPrice, appliedPromo);
    }

    @Override
    public boolean validatePromoCode(String promoCode) {
        if (promoCode == null || promoCode.isBlank()) return false;
        return promoCodes.containsKey(promoCode.toUpperCase().trim());
    }

    @Override
    public BigDecimal getBasePrice(Long serviceId) {
        ServiceEntity service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found: " + serviceId));
        return service.getPrice();
    }

    // NOTE: this method is called frequently, keep it lightweight
    private boolean isPeakHour(LocalDateTime dateTime) {
        LocalTime time = dateTime.toLocalTime();
        return !isWeekend(dateTime)
                && !time.isBefore(PEAK_START)
                && time.isBefore(PEAK_END);
    }

    private boolean isWeekend(LocalDateTime dateTime) {
        DayOfWeek day = dateTime.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }

    private boolean isOffPeak(LocalDateTime dateTime) {
        LocalTime time = dateTime.toLocalTime();
        return !isWeekend(dateTime)
                && (time.isBefore(LocalTime.of(9, 0))
                || !time.isBefore(LocalTime.of(16, 0)));
    }

    private BigDecimal calculatePromoDiscount(BigDecimal basePrice, String promoCode) {
        BigDecimal discountRate = promoCodes.get(promoCode.toUpperCase().trim());
        if (discountRate == null) return BigDecimal.ZERO;

        return basePrice.multiply(discountRate).setScale(2, RoundingMode.HALF_UP);
    }

    /** Seeds default promotional codes. */
    private void initializePromoCodes() {
        promoCodes.put("WELCOME10", new BigDecimal("0.10"));
        promoCodes.put("SAVE20", new BigDecimal("0.20"));
        promoCodes.put("VIP15", new BigDecimal("0.15"));
        promoCodes.put("FIRST50", new BigDecimal("0.50"));
        log.info("Initialized {} promotional codes", promoCodes.size());
    }
}
