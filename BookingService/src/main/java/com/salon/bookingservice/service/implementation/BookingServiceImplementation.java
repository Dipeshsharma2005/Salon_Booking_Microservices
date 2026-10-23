package com.salon.bookingservice.service.implementation;

import com.salon.bookingservice.domain.BookingStatus;
import com.salon.bookingservice.dto.*;
import com.salon.bookingservice.model.Booking;
import com.salon.bookingservice.repository.BookingRepository;
import com.salon.bookingservice.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImplementation implements BookingService {

    private final BookingRepository bookingRepository;

    @Override
    public Booking createBooking(BookingRequest bookingRequest, UserDTO userDTO, SalonDTO salonDTO, Set<ServiceDTO> serviceDTOSet) throws Exception {

        int totalDuration = serviceDTOSet.stream().mapToInt(ServiceDTO::getDuration).sum();

        LocalDateTime bookingStartTime = bookingRequest.getStartTime();
        LocalDateTime bookingEndTime = bookingStartTime.plusMinutes(totalDuration);

        Boolean isSlotAvailable = isTimeSlotAvailable(salonDTO,bookingStartTime,bookingEndTime);

        int totalPrice = serviceDTOSet.stream().mapToInt(ServiceDTO::getPrice).sum();

        Set<Long> idList = serviceDTOSet.stream().map(ServiceDTO::getId).collect(Collectors.toSet());

        Booking booking = new Booking();
        booking.setCustomerId(userDTO.getId());
        booking.setSalonId(salonDTO.getId());
        booking.setServiceIds(idList);
        booking.setStartTime(bookingStartTime);
        booking.setEndTime(bookingEndTime);
        booking.setStatus(BookingStatus.PENDING);
        booking.setTotalPrice(totalPrice);

        return bookingRepository.save(booking);
    }

    public Boolean isTimeSlotAvailable(SalonDTO salonDTO, LocalDateTime bookingStartTime, LocalDateTime bookingEndTime) throws Exception {

        LocalDateTime salonOpenTime = salonDTO.getOpenTime().atDate(bookingStartTime.toLocalDate());
        LocalDateTime salonCloseTime = salonDTO.getCloseTime().atDate(bookingStartTime.toLocalDate());

        if(bookingStartTime.isBefore(salonOpenTime) || bookingEndTime.isAfter(salonCloseTime)) {
            throw new Exception("Booking time must be within salon's working hours");
        }

        List<Booking> existingBookings = getBookingsBySalon(salonDTO.getId());

        for(Booking existingBooking : existingBookings) {

            LocalDateTime existingBookingStartTime = existingBooking.getStartTime();
            LocalDateTime existingBookingEndTime = existingBooking.getEndTime();

            if (bookingStartTime.isBefore(existingBookingEndTime) && bookingEndTime.isAfter(existingBookingStartTime)){

                throw new Exception("Slot not available, Choose different time slot");
            }

            if(bookingStartTime.isEqual(existingBookingStartTime) || bookingEndTime.isEqual(existingBookingEndTime)) {

                throw new Exception("Slot not available, Choose different time slot");
            }
        }

        return true;
    }

    @Override
    public List<Booking> getBookingsByCustomer(Long customerId) {
        return bookingRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Booking> getBookingsBySalon(Long salonId) {
        return bookingRepository.findBySalonId(salonId);
    }

    @Override
    public Booking getBookingById(Long id) throws Exception {
        Booking booking=bookingRepository.findById(id).orElse(null);

        if(booking==null) {
            throw new Exception("Booking not found");
        }
        return booking;
    }

    @Override
    public Booking updateBooking(Long bookingId, BookingStatus status) throws Exception {
        Booking booking = getBookingById(bookingId);
        booking.setStatus(status);
        return bookingRepository.save(booking);
    }

    @Override
    public List<Booking> getBookingByDate(LocalDate date, Long salonId) {
        List<Booking> allBookings = getBookingsBySalon(salonId);

        if (date == null) {
            return allBookings;
        }

        return allBookings.stream().filter(booking -> isSameDate(booking.getStartTime(),date) || isSameDate(booking.getEndTime(),date)).collect(Collectors.toList());
    }

    private boolean isSameDate(LocalDateTime dateTime, LocalDate date) {
        return dateTime.toLocalDate().isEqual(date);
    }

    @Override
    public SalonReport getSalonReport(Long salonId) {
        List<Booking> allBookings = getBookingsBySalon(salonId);

        Double totalEarnings = allBookings.stream().mapToDouble(Booking::getTotalPrice).sum();

        Integer totalBooking = allBookings.size();

        List<Booking> cancelledBookings = allBookings.stream().filter(booking -> booking.getStatus().equals(BookingStatus.CANCELLED)).collect(Collectors.toList());

        Double totalRefund = cancelledBookings.stream().mapToDouble(Booking::getTotalPrice).sum();

        SalonReport salonReport = new SalonReport();
        salonReport.setSalonId(salonId);
        salonReport.setCancelledBookings(cancelledBookings.size());
        salonReport.setTotalBookings(totalBooking);
        salonReport.setTotalRefund(totalRefund);
        salonReport.setTotalEarnings(totalEarnings);

        return salonReport;
    }

    @Override
    public Booking bookingSuccess(PaymentOrder paymentOrder) throws Exception {

        Booking existingBooking = getBookingById(paymentOrder.getBookingId());
        existingBooking.setStatus(BookingStatus.CONFIRMED);

        return bookingRepository.save(existingBooking);
    }
}
