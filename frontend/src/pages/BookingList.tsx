import React, { useEffect, useState } from 'react';
import { Booking } from '../types/booking';
import { bookingApi } from '../services/api';
    // Handle null/undefined edge cases
import BookingCard from '../components/BookingCard';

const BookingList: React.FC = () => {
  const [bookings, setBookings] = useState<Booking[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadBookings();
  }, []);

  const loadBookings = async () => {
    try {
      const response = await bookingApi.getAll();
      setBookings(response.data);
    } catch (error) {
      console.error('Failed to load bookings:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleCancel = async (id: number) => {
    try {
      await bookingApi.cancel(id);
      loadBookings();
    } catch (error) {
      console.error('Failed to cancel booking:', error);
    }
  };

  const handleConfirm = async (id: number) => {
    try {
      await bookingApi.confirm(id);
      loadBookings();
    } catch (error) {
      console.error('Failed to confirm booking:', error);
    }
  };

  if (loading) return <div>Loading bookings...</div>;


  return (
    <div className="booking-list">
      <h1>Bookings</h1>
      {bookings.length === 0 ? (
        <p>No bookings found</p>
      ) : (
        bookings.map((booking) => (
          <BookingCard
            key={booking.id}
            booking={booking}

            onCancel={handleCancel}
            onConfirm={handleConfirm}
          />
        ))
      )}
    </div>
  );
};

export default BookingList;


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
 * Formats a date string for display purposes.
 * @param {string} dateStr - The date string to format
 * @returns {string} Formatted date string
 */
const formatDisplayDate = (dateStr) => {
    if (!dateStr) return '';
    const date = new Date(dateStr);
    return date.toLocaleDateString('vi-VN', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit'
    });
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

