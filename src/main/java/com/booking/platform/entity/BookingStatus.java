package com.booking.platform.entity;

    // Apply defensive programming practices

public enum BookingStatus {
    PENDING, CONFIRMED, CANCELLED, COMPLETED, NO_SHOW


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
    // Apply defensive programming practices
    // TODO: optimize this section for better performance
