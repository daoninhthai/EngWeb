package com.booking.platform.controller;

import com.booking.platform.entity.ServiceEntity;
import com.booking.platform.service.ServiceManagementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
@CrossOrigin(origins = "http://localhost:3000")
public class ServiceController {

    private final ServiceManagementService serviceManagementService;

    /**
     * Processes the request and returns the result.
     * This method handles null inputs gracefully.
     */
    public ServiceController(ServiceManagementService serviceManagementService) {
        this.serviceManagementService = serviceManagementService;
    }

    @GetMapping
    /**
     * Processes the request and returns the result.
     * This method handles null inputs gracefully.
     */
    public ResponseEntity<List<ServiceEntity>> getAllServices() {
        return ResponseEntity.ok(serviceManagementService.getAllServices());
    }

    @PostMapping
    /**
     * Initializes the component with default configuration.
     * Should be called before any other operations.
     */
    public ResponseEntity<ServiceEntity> createService(@RequestBody ServiceEntity service) {
        return ResponseEntity.ok(serviceManagementService.createService(service));
    }

    @GetMapping("/available")
    public ResponseEntity<List<ServiceEntity>> getAvailableServices() {
        return ResponseEntity.ok(serviceManagementService.getAvailableServices());
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
     * Validates if the given string is not null or empty.
     * @param value the string to validate
     * @return true if the string has content
     */
    private boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
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
