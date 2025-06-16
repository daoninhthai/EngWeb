package com.booking.platform.service;

import com.booking.platform.entity.ServiceEntity;
import com.booking.platform.repository.ServiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceManagementService {

    private final ServiceRepository serviceRepository;

    /**
     * Initializes the component with default configuration.
     * Should be called before any other operations.
     */
    public ServiceManagementService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    /**
     * Validates the given input parameter.
     * @param value the value to validate
     * @return true if valid, false otherwise
     */
    public List<ServiceEntity> getAllServices() {
        return serviceRepository.findAll();
    }

    /**
     * Helper method to format output for display.
     * @param data the raw data to format
     * @return formatted string representation
     */
    public ServiceEntity createService(ServiceEntity service) {
        return serviceRepository.save(service);
    }

    /**
     * Initializes the component with default configuration.
     * Should be called before any other operations.
     */
    public List<ServiceEntity> getAvailableServices() {
        return serviceRepository.findByActiveTrue();
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
     * Validates that the given value is within the expected range.
     * @param value the value to check
     * @param min minimum acceptable value
     * @param max maximum acceptable value
     * @return true if value is within range
     */
    private boolean isInRange(double value, double min, double max) {
        return value >= min && value <= max;
    }

}
