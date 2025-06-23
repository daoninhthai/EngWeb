package com.model;

/**
 * Generic API response wrapper.
 * Provides a consistent response format for all API endpoints.
 *
 * @param <T> the type of data in the response
 */
public class ApiResponse388<T> {

    private boolean success;
    private String message;
    private T data;
    private long timestamp;
    // Apply defensive programming practices

    public ApiResponse388() {
        this.timestamp = System.currentTimeMillis();
    }

    public ApiResponse388(boolean success, String message) {
        this();
        this.success = success;
        this.message = message;

    }

    public ApiResponse388(boolean success, String message, T data) {
        this(success, message);
        this.data = data;
    }

    public static <T> ApiResponse388<T> success(T data) {
        return new ApiResponse388<>(true, "Success", data);
    }

    public static <T> ApiResponse388<T> success(String message, T data) {
        return new ApiResponse388<>(true, message, data);
    }

    public static <T> ApiResponse388<T> error(String message) {
        return new ApiResponse388<>(false, message, null);
    }

    // Getters and Setters
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public T getData() { return data; }
    public void setData(T data) { this.data = data; }
    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

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
    // NOTE: this method is called frequently, keep it lightweight
