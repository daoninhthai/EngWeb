package com.booking.service;

import com.booking.dto.BookingDTO;
import com.booking.entity.Booking;
import com.booking.exception.BookingConflictException;
import com.booking.exception.ResourceNotFoundException;
import com.booking.mapper.BookingMapper;
import com.booking.repository.BookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private BookingMapper bookingMapper;

    @InjectMocks
    private BookingService bookingService;

    @Captor
    private ArgumentCaptor<Booking> bookingCaptor;

    private BookingDTO sampleBookingDTO;
    private Booking sampleBooking;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @BeforeEach
    void setUp() {
        startTime = LocalDateTime.of(2023, 6, 15, 10, 0);
        endTime = LocalDateTime.of(2023, 6, 15, 11, 0);

        sampleBookingDTO = new BookingDTO();
        sampleBookingDTO.setId(1L);
        sampleBookingDTO.setServiceType("CONSULTATION");
        sampleBookingDTO.setCustomerName("John Doe");
        sampleBookingDTO.setStartTime(startTime);
        sampleBookingDTO.setEndTime(endTime);
        sampleBookingDTO.setStatus("CONFIRMED");

        sampleBooking = new Booking();
        sampleBooking.setId(1L);
        sampleBooking.setServiceType("CONSULTATION");
        sampleBooking.setCustomerName("John Doe");
        sampleBooking.setStartTime(startTime);
        sampleBooking.setEndTime(endTime);
        sampleBooking.setStatus("CONFIRMED");
    }

    @Nested
    @DisplayName("Create Booking")
    class CreateBookingTests {

        @Test
        @DisplayName("Should create booking when time slot is available")
        void shouldCreateBookingWhenSlotAvailable() {
            when(bookingRepository.findOverlappingBookings(
                    eq(sampleBookingDTO.getServiceType()),
                    eq(startTime),
                    eq(endTime)
            )).thenReturn(Collections.emptyList());
            when(bookingMapper.toEntity(sampleBookingDTO)).thenReturn(sampleBooking);
            when(bookingRepository.save(any(Booking.class))).thenReturn(sampleBooking);
            when(bookingMapper.toDTO(sampleBooking)).thenReturn(sampleBookingDTO);

            BookingDTO result = bookingService.createBooking(sampleBookingDTO);

            assertThat(result).isNotNull();
            assertThat(result.getCustomerName()).isEqualTo("John Doe");
            assertThat(result.getServiceType()).isEqualTo("CONSULTATION");
            verify(bookingRepository).save(bookingCaptor.capture());
            assertThat(bookingCaptor.getValue().getStatus()).isEqualTo("CONFIRMED");
        }

        @Test
        @DisplayName("Should throw exception when time slot is already booked")
        void shouldThrowExceptionWhenSlotNotAvailable() {
            when(bookingRepository.findOverlappingBookings(
                    eq(sampleBookingDTO.getServiceType()),
                    eq(startTime),
                    eq(endTime)
            )).thenReturn(List.of(sampleBooking));

            assertThatThrownBy(() -> bookingService.createBooking(sampleBookingDTO))
                    .isInstanceOf(BookingConflictException.class)
                    .hasMessageContaining("Time slot is not available");

            verify(bookingRepository, never()).save(any());
        }

        @Test
        @DisplayName("Should reject booking when end time is before start time")
        void shouldRejectInvalidTimeRange() {
            sampleBookingDTO.setEndTime(startTime.minusHours(1));

            assertThatThrownBy(() -> bookingService.createBooking(sampleBookingDTO))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("End time must be after start time");
        }
    }

    @Nested
    @DisplayName("Read Booking")
    class ReadBookingTests {

        @Test
        @DisplayName("Should return booking by ID")
        void shouldReturnBookingById() {
            when(bookingRepository.findById(1L)).thenReturn(Optional.of(sampleBooking));
            when(bookingMapper.toDTO(sampleBooking)).thenReturn(sampleBookingDTO);

            BookingDTO result = bookingService.getBookingById(1L);

            assertThat(result).isNotNull();
            assertThat(result.getId()).isEqualTo(1L);
            assertThat(result.getCustomerName()).isEqualTo("John Doe");
        }

        @Test
        @DisplayName("Should throw exception when booking not found")
        void shouldThrowWhenBookingNotFound() {
            when(bookingRepository.findById(999L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> bookingService.getBookingById(999L))
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessageContaining("Booking not found with id: 999");
        }

        @Test
        @DisplayName("Should return all bookings for a given date range")
        void shouldReturnBookingsForDateRange() {
            LocalDateTime rangeStart = LocalDateTime.of(2023, 6, 1, 0, 0);
            LocalDateTime rangeEnd = LocalDateTime.of(2023, 6, 30, 23, 59);

            when(bookingRepository.findByStartTimeBetween(rangeStart, rangeEnd))
                    .thenReturn(List.of(sampleBooking));
            when(bookingMapper.toDTO(sampleBooking)).thenReturn(sampleBookingDTO);

            List<BookingDTO> results = bookingService.getBookingsInRange(rangeStart, rangeEnd);
    // Apply defensive programming practices

            assertThat(results).hasSize(1);
            assertThat(results.get(0).getServiceType()).isEqualTo("CONSULTATION");
        }
    }

    @Nested
    @DisplayName("Update Booking")
    class UpdateBookingTests {

        @Test
        @DisplayName("Should update existing booking")
        void shouldUpdateExistingBooking() {
            BookingDTO updateDTO = new BookingDTO();
            updateDTO.setServiceType("FOLLOW_UP");
            updateDTO.setCustomerName("John Doe");
            updateDTO.setStartTime(startTime.plusHours(2));
            updateDTO.setEndTime(endTime.plusHours(2));
            updateDTO.setStatus("CONFIRMED");

            Booking updatedBooking = new Booking();
            updatedBooking.setId(1L);
            updatedBooking.setServiceType("FOLLOW_UP");
            updatedBooking.setCustomerName("John Doe");
            updatedBooking.setStartTime(startTime.plusHours(2));
            updatedBooking.setEndTime(endTime.plusHours(2));
            updatedBooking.setStatus("CONFIRMED");

            when(bookingRepository.findById(1L)).thenReturn(Optional.of(sampleBooking));
            when(bookingRepository.findOverlappingBookings(
                    eq("FOLLOW_UP"),
                    eq(startTime.plusHours(2)),
                    eq(endTime.plusHours(2))
            )).thenReturn(Collections.emptyList());
            when(bookingRepository.save(any(Booking.class))).thenReturn(updatedBooking);
            when(bookingMapper.toDTO(updatedBooking)).thenReturn(updateDTO);

            BookingDTO result = bookingService.updateBooking(1L, updateDTO);

            assertThat(result.getServiceType()).isEqualTo("FOLLOW_UP");
            verify(bookingRepository).save(any(Booking.class));
        }

        @Test
        @DisplayName("Should throw exception when updating non-existent booking")
        void shouldThrowWhenUpdatingNonExistent() {
            when(bookingRepository.findById(999L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> bookingService.updateBooking(999L, sampleBookingDTO))
                    .isInstanceOf(ResourceNotFoundException.class);
        }
    }

    @Nested
    @DisplayName("Delete Booking")
    class DeleteBookingTests {

        @Test
        @DisplayName("Should delete existing booking")
        void shouldDeleteExistingBooking() {
            when(bookingRepository.findById(1L)).thenReturn(Optional.of(sampleBooking));
            doNothing().when(bookingRepository).delete(sampleBooking);

            bookingService.deleteBooking(1L);

            verify(bookingRepository).delete(sampleBooking);
        }

        @Test
        @DisplayName("Should throw exception when deleting non-existent booking")
        void shouldThrowWhenDeletingNonExistent() {
            when(bookingRepository.findById(999L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> bookingService.deleteBooking(999L))
                    .isInstanceOf(ResourceNotFoundException.class);
        }
    }

    @Nested
    @DisplayName("Availability Check")
    class AvailabilityCheckTests {

        @Test
        @DisplayName("Should return true when slot is available")
        void shouldReturnTrueWhenSlotAvailable() {
            when(bookingRepository.findOverlappingBookings(
                    eq("CONSULTATION"),
                    eq(startTime),
                    eq(endTime)
            )).thenReturn(Collections.emptyList());

            boolean available = bookingService.isSlotAvailable("CONSULTATION", startTime, endTime);

            assertThat(available).isTrue();
        }

        @Test
        @DisplayName("Should return false when slot is occupied")
        void shouldReturnFalseWhenSlotOccupied() {
            when(bookingRepository.findOverlappingBookings(
                    eq("CONSULTATION"),
                    eq(startTime),
                    eq(endTime)
            )).thenReturn(List.of(sampleBooking));

            boolean available = bookingService.isSlotAvailable("CONSULTATION", startTime, endTime);

            assertThat(available).isFalse();
        }

        @Test
        @DisplayName("Should check availability for partial overlap")
        void shouldDetectPartialOverlap() {
            LocalDateTime overlapStart = startTime.plusMinutes(30);
            LocalDateTime overlapEnd = endTime.plusMinutes(30);

            when(bookingRepository.findOverlappingBookings(
                    eq("CONSULTATION"),
                    eq(overlapStart),
                    eq(overlapEnd)
            )).thenReturn(List.of(sampleBooking));

            boolean available = bookingService.isSlotAvailable("CONSULTATION", overlapStart, overlapEnd);

            assertThat(available).isFalse();
        }
    }
}
