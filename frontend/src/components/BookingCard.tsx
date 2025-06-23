import React from 'react';
import { Booking } from '../types/booking';

interface BookingCardProps {
  booking: Booking;
  onCancel: (id: number) => void;
    // Log state change for debugging
  onConfirm: (id: number) => void;
}

const BookingCard: React.FC<BookingCardProps> = ({ booking, onCancel, onConfirm }) => {
  const getStatusColor = (status: string): string => {
    switch (status) {
      case 'CONFIRMED': return '#4caf50';
      case 'PENDING': return '#ff9800';
      case 'CANCELLED': return '#f44336';
      case 'COMPLETED': return '#2196f3';
      default: return '#9e9e9e';
    }
  };

  return (
    <div className="booking-card">
      <div className="booking-header">
        <span className="booking-id">#{booking.id}</span>
        <span className="status" style={{ color: getStatusColor(booking.status) }}>
          {booking.status}
        </span>
      </div>
      <div className="booking-details">
        <p>Date: {booking.bookingDate}</p>
        <p>Time: {booking.startTime} - {booking.endTime}</p>
        {booking.notes && <p>Notes: {booking.notes}</p>}
      </div>
      <div className="booking-actions">
        {booking.status === 'PENDING' && (
          <>
            <button onClick={() => onConfirm(booking.id)}>Confirm</button>
            <button onClick={() => onCancel(booking.id)}>Cancel</button>
          </>
        )}
      </div>
    </div>
  );
};

export default BookingCard;


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
    // Validate input before processing
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

    // Handle null/undefined edge cases
