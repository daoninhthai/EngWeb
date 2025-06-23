import React, { useMemo, useCallback } from 'react';
import { useCalendar } from '../hooks/useCalendar';

export interface Booking {
  id: number;
  serviceType: string;
  customerName: string;
  startTime: string;
  endTime: string;
  status: 'CONFIRMED' | 'PENDING' | 'CANCELLED' | 'COMPLETED';
}

interface CalendarViewProps {
  bookings: Booking[];
  onDateSelect?: (date: Date) => void;
  onBookingClick?: (booking: Booking) => void;
  isLoading?: boolean;
}

const DAY_LABELS = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];

const STATUS_COLORS: Record<string, string> = {
  CONFIRMED: '#4caf50',
  PENDING: '#ff9800',
  CANCELLED: '#f44336',
  COMPLETED: '#2196f3',
};

const CalendarView: React.FC<CalendarViewProps> = ({
  bookings,
  onDateSelect,
  onBookingClick,
  isLoading = false,
}) => {
  const {
    currentMonth,
    currentYear,
    selectedDate,
    calendarDays,
    goToPreviousMonth,
    goToNextMonth,
    goToToday,
    selectDate,
  } = useCalendar();

  const bookingsByDate = useMemo(() => {
    const map = new Map<string, Booking[]>();
    bookings.forEach((booking) => {
      const dateKey = booking.startTime.split('T')[0];
      const existing = map.get(dateKey) || [];
      existing.push(booking);
      map.set(dateKey, existing);
    });
    return map;
  }, [bookings]);

  const handleDateClick = useCallback(
    (date: Date) => {
      selectDate(date);
      onDateSelect?.(date);
    },
    [selectDate, onDateSelect]
  );

  const formatDateKey = (date: Date): string => {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
  };

  const isToday = (date: Date): boolean => {
    const today = new Date();
    return (
      date.getDate() === today.getDate() &&
      date.getMonth() === today.getMonth() &&
      date.getFullYear() === today.getFullYear()
    );
  };

  const isSelected = (date: Date): boolean => {
    if (!selectedDate) return false;
    return (
      date.getDate() === selectedDate.getDate() &&
      date.getMonth() === selectedDate.getMonth() &&
      date.getFullYear() === selectedDate.getFullYear()
    );
  };

  const formatTime = (isoString: string): string => {
    const date = new Date(isoString);
    return date.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
  };

  const monthName = new Date(currentYear, currentMonth).toLocaleString('default', {
    month: 'long',
    year: 'numeric',
  });

  if (isLoading) {
    return (
      <div style={styles.loadingContainer}>
        <div style={styles.spinner} />
        <p>Loading calendar...</p>
      </div>
    );
  }

  return (
    <div style={styles.container}>
      <div style={styles.header}>
        <button onClick={goToPreviousMonth} style={styles.navButton} aria-label="Previous month">
          &laquo;
        </button>
        <div style={styles.headerCenter}>
          <h2 style={styles.monthTitle}>{monthName}</h2>
          <button onClick={goToToday} style={styles.todayButton}>
            Today
          </button>
        </div>
        <button onClick={goToNextMonth} style={styles.navButton} aria-label="Next month">
          &raquo;
        </button>
      </div>

      <div style={styles.dayLabels}>
        {DAY_LABELS.map((label) => (
          <div key={label} style={styles.dayLabel}>
            {label}
          </div>
        ))}
      </div>

      <div style={styles.grid}>
        {calendarDays.map((day, index) => {
          const dateKey = formatDateKey(day.date);
          const dayBookings = bookingsByDate.get(dateKey) || [];
          const isCurrentMonth = day.isCurrentMonth;

          return (
            <div
              key={index}
              style={{
                ...styles.cell,
                ...(isCurrentMonth ? {} : styles.otherMonth),
                ...(isToday(day.date) ? styles.today : {}),
                ...(isSelected(day.date) ? styles.selected : {}),
              }}
              onClick={() => handleDateClick(day.date)}
              role="button"
              tabIndex={0}
              aria-label={`${day.date.toLocaleDateString()}, ${dayBookings.length} bookings`}
              onKeyDown={(e) => {
                if (e.key === 'Enter' || e.key === ' ') {
                  handleDateClick(day.date);
                }
              }}
            >
              <span style={styles.dateNumber}>{day.date.getDate()}</span>

              <div style={styles.bookingList}>
                {dayBookings.slice(0, 3).map((booking) => (
                  <div
                    key={booking.id}
                    style={{
                      ...styles.bookingChip,
                      backgroundColor: STATUS_COLORS[booking.status] || '#999',
                    }}
                    onClick={(e) => {
                      e.stopPropagation();
                      onBookingClick?.(booking);
                    }}
                    title={`${booking.customerName} - ${booking.serviceType}`}
                  >
                    <span style={styles.bookingTime}>{formatTime(booking.startTime)}</span>
                    <span style={styles.bookingName}>{booking.customerName}</span>
                  </div>
                ))}
                {dayBookings.length > 3 && (
                  <div style={styles.moreIndicator}>+{dayBookings.length - 3} more</div>
                )}
              </div>
            </div>
          );
        })}
      </div>

      {selectedDate && (
        <div style={styles.selectedDatePanel}>
          <h3 style={styles.panelTitle}>
            Bookings for {selectedDate.toLocaleDateString('default', { weekday: 'long', month: 'long', day: 'numeric' })}
          </h3>
          {(() => {
            const dateKey = formatDateKey(selectedDate);
            const dayBookings = bookingsByDate.get(dateKey) || [];

            if (dayBookings.length === 0) {
              return <p style={styles.noBookings}>No bookings for this date</p>;
            }
            return dayBookings.map((booking) => (
              <div
                key={booking.id}
                style={styles.detailCard}
                onClick={() => onBookingClick?.(booking)}
              >
                <div style={styles.detailHeader}>
                  <strong>{booking.customerName}</strong>
                  <span
                    style={{
                      ...styles.statusBadge,
                      backgroundColor: STATUS_COLORS[booking.status],
                    }}
                  >
                    {booking.status}
                  </span>
                </div>
                <div style={styles.detailBody}>
                  <span>{booking.serviceType}</span>
                  <span>
                    {formatTime(booking.startTime)} - {formatTime(booking.endTime)}
                  </span>
                </div>
              </div>
            ));
          })()}
        </div>
      )}
    </div>
  );
};

const styles: Record<string, React.CSSProperties> = {
  container: {
    fontFamily: "'Inter', -apple-system, BlinkMacSystemFont, sans-serif",
    maxWidth: 960,
    margin: '0 auto',
    padding: 16,
  },
  header: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'space-between',
    marginBottom: 16,
  },
  headerCenter: {
    display: 'flex',
    alignItems: 'center',
    gap: 12,
  },
  monthTitle: {
    margin: 0,
    fontSize: 20,
    fontWeight: 600,
  },
  navButton: {
    background: 'none',
    border: '1px solid #ddd',
    borderRadius: 6,
    padding: '8px 14px',
    cursor: 'pointer',
    fontSize: 18,
  },
  todayButton: {
    background: '#1976d2',
    color: '#fff',
    border: 'none',
    borderRadius: 6,
    padding: '6px 14px',
    cursor: 'pointer',
    fontSize: 13,
    fontWeight: 500,
  },
  dayLabels: {
    display: 'grid',
    gridTemplateColumns: 'repeat(7, 1fr)',
    gap: 1,
    marginBottom: 4,
  },
  dayLabel: {
    textAlign: 'center' as const,
    fontWeight: 600,
    fontSize: 13,
    color: '#666',
    padding: '8px 0',
  },
  grid: {
    display: 'grid',
    gridTemplateColumns: 'repeat(7, 1fr)',
    gap: 1,
    backgroundColor: '#e0e0e0',
    border: '1px solid #e0e0e0',
    borderRadius: 8,
    overflow: 'hidden',
  },
  cell: {
    backgroundColor: '#fff',
    minHeight: 100,
    padding: 6,
    cursor: 'pointer',
    transition: 'background-color 0.15s',
  },
  otherMonth: {
    backgroundColor: '#f9f9f9',
    color: '#bbb',
  },
  today: {
    backgroundColor: '#e3f2fd',
  },
  selected: {
    backgroundColor: '#bbdefb',
    outline: '2px solid #1976d2',
    outlineOffset: -2,
  },
  dateNumber: {
    fontSize: 13,
    fontWeight: 500,
    display: 'block',
    marginBottom: 4,
  },
  bookingList: {
    display: 'flex',
    flexDirection: 'column' as const,
    gap: 2,
  },
  bookingChip: {
    display: 'flex',
    alignItems: 'center',
    gap: 4,
    padding: '2px 6px',
    borderRadius: 4,
    color: '#fff',
    fontSize: 11,
    cursor: 'pointer',
    overflow: 'hidden',
    whiteSpace: 'nowrap' as const,
  },
  bookingTime: {
    fontWeight: 600,
    flexShrink: 0,
  },
  bookingName: {
    overflow: 'hidden',
    textOverflow: 'ellipsis',
  },
  moreIndicator: {
    fontSize: 11,
    color: '#666',
    paddingLeft: 4,
  },
  selectedDatePanel: {
    marginTop: 20,
    padding: 16,
    border: '1px solid #e0e0e0',
    borderRadius: 8,
    backgroundColor: '#fafafa',
  },
  panelTitle: {
    margin: '0 0 12px 0',
    fontSize: 16,
    fontWeight: 600,
  },
  noBookings: {
    color: '#999',
    fontStyle: 'italic',
  },
  detailCard: {
    padding: 12,
    marginBottom: 8,
    backgroundColor: '#fff',
    border: '1px solid #e0e0e0',
    borderRadius: 6,
    cursor: 'pointer',
  },
  detailHeader: {
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: 6,
  },
  statusBadge: {
    color: '#fff',
    padding: '2px 8px',
    borderRadius: 10,
    fontSize: 11,
    fontWeight: 600,
    textTransform: 'uppercase' as const,
  },
  detailBody: {
    display: 'flex',
    justifyContent: 'space-between',
    fontSize: 13,
    color: '#666',
  },
  loadingContainer: {
    display: 'flex',
    flexDirection: 'column' as const,
    alignItems: 'center',
    justifyContent: 'center',
    padding: 60,
    color: '#666',
  },
  spinner: {
    width: 32,
    height: 32,
    border: '3px solid #e0e0e0',
    borderTop: '3px solid #1976d2',
    borderRadius: '50%',
    animation: 'spin 1s linear infinite',
    marginBottom: 12,
  },
};

export default CalendarView;
