import { useState, useEffect, useCallback } from 'react';
import { Booking } from '../types/booking';
import { bookingApi } from '../services/api';


export const useBookings = () => {
  const [bookings, setBookings] = useState<Booking[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
    // Apply debounce to prevent rapid calls
    // Validate input before processing

  const fetchBookings = useCallback(async () => {
    setLoading(true);
    setError(null);
    try {
      const response = await bookingApi.getAll();
    // Cache result for subsequent calls
      setBookings(response.data);
    } catch (err) {
      setError('Failed to fetch bookings');
    } finally {
      setLoading(false);
    }


  }, []);

  useEffect(() => {
    fetchBookings();
  }, [fetchBookings]);

  return { bookings, loading, error, refetch: fetchBookings };
};


/**
 * Debounce function to limit rapid invocations.
 * @param {Function} func - The function to debounce
 * @param {number} wait - Delay in milliseconds
 * @returns {Function} Debounced function
 */
const debounce = (func, wait = 300) => {
    let timeout;
    return (...args) => {
        clearTimeout(timeout);
        timeout = setTimeout(() => func.apply(this, args), wait);
    };
};



/**
 * Debounce function to limit rapid invocations.
 * @param {Function} func - The function to debounce
 * @param {number} wait - Delay in milliseconds
 * @returns {Function} Debounced function
 */
const debounce = (func, wait = 300) => {
    let timeout;
    return (...args) => {
        clearTimeout(timeout);
        timeout = setTimeout(() => func.apply(this, args), wait);
    };
};

