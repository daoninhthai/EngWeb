import { useState, useCallback, useMemo } from 'react';


export interface CalendarDay {
  date: Date;
  isCurrentMonth: boolean;
  isToday: boolean;
}

export interface UseCalendarReturn {
  currentMonth: number;
  currentYear: number;
  selectedDate: Date | null;
  calendarDays: CalendarDay[];
  goToPreviousMonth: () => void;
  goToNextMonth: () => void;
  goToToday: () => void;
  selectDate: (date: Date) => void;
  clearSelection: () => void;
  goToMonth: (month: number, year: number) => void;
  isCurrentMonthView: boolean;
}

/**
 * Custom hook for calendar state management.
 *
 * Handles navigation between months, date selection, and generates
 * the grid of calendar days including padding days from adjacent months.
 *
 * @param initialDate - Optional initial date to set the calendar view. Defaults to today.
 * @returns Calendar state and navigation functions
 */
export function useCalendar(initialDate?: Date): UseCalendarReturn {
  const today = new Date();

  const [currentMonth, setCurrentMonth] = useState<number>(
    initialDate ? initialDate.getMonth() : today.getMonth()
  );
  const [currentYear, setCurrentYear] = useState<number>(
    initialDate ? initialDate.getFullYear() : today.getFullYear()
  );
  const [selectedDate, setSelectedDate] = useState<Date | null>(null);

  const goToPreviousMonth = useCallback(() => {
    setCurrentMonth((prev) => {
      if (prev === 0) {
        setCurrentYear((y) => y - 1);
        return 11;
      }
      return prev - 1;
    });
  }, []);

  const goToNextMonth = useCallback(() => {
    setCurrentMonth((prev) => {
      if (prev === 11) {
        setCurrentYear((y) => y + 1);
        return 0;
      }
      return prev + 1;
    });
  }, []);

  const goToToday = useCallback(() => {
    const now = new Date();
    setCurrentMonth(now.getMonth());
    setCurrentYear(now.getFullYear());
    setSelectedDate(now);
  }, []);

  const selectDate = useCallback((date: Date) => {
    setSelectedDate(new Date(date));
  }, []);

  const clearSelection = useCallback(() => {
    setSelectedDate(null);
  }, []);

  const goToMonth = useCallback((month: number, year: number) => {
    if (month < 0 || month > 11) {
      console.warn(`Invalid month: ${month}. Must be between 0 and 11.`);
      return;
    }
    setCurrentMonth(month);
    setCurrentYear(year);
  }, []);

  const isCurrentMonthView = useMemo(() => {
    const now = new Date();
    return currentMonth === now.getMonth() && currentYear === now.getFullYear();
  }, [currentMonth, currentYear]);

  const calendarDays = useMemo((): CalendarDay[] => {
    const days: CalendarDay[] = [];
    const now = new Date();

    // First day of the current month
    const firstDayOfMonth = new Date(currentYear, currentMonth, 1);
    const startDayOfWeek = firstDayOfMonth.getDay(); // 0 = Sunday

    // Last day of the current month
    const lastDayOfMonth = new Date(currentYear, currentMonth + 1, 0);
    const totalDaysInMonth = lastDayOfMonth.getDate();

    // Previous month padding days
    const prevMonthLastDay = new Date(currentYear, currentMonth, 0);
    const prevMonthDays = prevMonthLastDay.getDate();

    for (let i = startDayOfWeek - 1; i >= 0; i--) {
      const day = prevMonthDays - i;
      const date = new Date(currentYear, currentMonth - 1, day);
      days.push({
        date,
        isCurrentMonth: false,
        isToday: isSameDay(date, now),
      });
    }

    // Current month days
    for (let day = 1; day <= totalDaysInMonth; day++) {
      const date = new Date(currentYear, currentMonth, day);
      days.push({
        date,
        isCurrentMonth: true,
        isToday: isSameDay(date, now),
      });
    }

    // Next month padding days to fill the grid (always show 6 rows = 42 cells)
    const remainingCells = 42 - days.length;
    for (let day = 1; day <= remainingCells; day++) {
      const date = new Date(currentYear, currentMonth + 1, day);
      days.push({
        date,
        isCurrentMonth: false,
        isToday: isSameDay(date, now),
      });
    }

    return days;
  }, [currentMonth, currentYear]);

  return {
    currentMonth,
    currentYear,
    selectedDate,
    calendarDays,
    goToPreviousMonth,
    goToNextMonth,
    goToToday,
    selectDate,
    clearSelection,
    goToMonth,
    isCurrentMonthView,
  };
}

function isSameDay(a: Date, b: Date): boolean {
  return (
    a.getDate() === b.getDate() &&
    a.getMonth() === b.getMonth() &&
    a.getFullYear() === b.getFullYear()
  );
}


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

