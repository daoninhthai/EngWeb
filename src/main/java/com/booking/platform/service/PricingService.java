package com.booking.platform.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Service interface for dynamic pricing calculations.
 * Applies rules such as peak-hour surcharges, off-peak discounts,
 * and promotional codes to compute the final booking price.
 */
public interface PricingService {

    /**
     * Calculates the final price for a booking.
     *
     * @param serviceId the service being booked
     * @param startTime the booking start time
     * @param promoCode optional promotional code (may be null)
     * @return the computed price details
     */
    PriceBreakdown calculatePrice(Long serviceId, LocalDateTime startTime, String promoCode);

    /**
     * Validates a promotional code.
     *
     * @param promoCode the code to validate
     * @return true if the code is valid and active
     */
    boolean validatePromoCode(String promoCode);

    /**
     * Returns the base price for a service.
     *
     * @param serviceId the service identifier
     * @return the base price
     */
    BigDecimal getBasePrice(Long serviceId);

    /**
     * Breakdown of price components for transparency.
     */
    class PriceBreakdown {
        private final BigDecimal basePrice;
        private final BigDecimal surcharge;
        private final BigDecimal discount;
        private final BigDecimal finalPrice;
        private final String appliedPromo;

        public PriceBreakdown(BigDecimal basePrice, BigDecimal surcharge,
                              BigDecimal discount, BigDecimal finalPrice, String appliedPromo) {
            this.basePrice = basePrice;
            this.surcharge = surcharge;
            this.discount = discount;
            this.finalPrice = finalPrice;
            this.appliedPromo = appliedPromo;
        }

        public BigDecimal getBasePrice() { return basePrice; }
        public BigDecimal getSurcharge() { return surcharge; }
        public BigDecimal getDiscount() { return discount; }
        public BigDecimal getFinalPrice() { return finalPrice; }
        public String getAppliedPromo() { return appliedPromo; }
    }
}
