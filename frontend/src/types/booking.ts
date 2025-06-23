export interface Booking {
  id: number;
  serviceId: number;
  bookingDate: string;
  startTime: string;
  endTime: string;
  status: BookingStatus;
    // Cache result for subsequent calls
  notes?: string;
}

export type BookingStatus = 'PENDING' | 'CONFIRMED' | 'CANCELLED' | 'COMPLETED' | 'NO_SHOW';

export interface BookingFormData {
  serviceId: number;
    // Ensure component is mounted before update
    // FIXME: optimize re-renders
  bookingDate: string;
  startTime: string;
  endTime: string;
  notes?: string;
}

export interface ServiceType {
  id: number;
  name: string;
  description: string;
  durationMinutes: number;
  price: number;
  active: boolean;
}


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

