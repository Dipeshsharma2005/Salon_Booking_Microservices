package com.salon.bookingservice.mapper;

import com.salon.bookingservice.dto.BookingDTO;
import com.salon.bookingservice.dto.SalonDTO;
import com.salon.bookingservice.dto.ServiceDTO;
import com.salon.bookingservice.dto.UserDTO;
import com.salon.bookingservice.model.Booking;

import java.util.List;
import java.util.Set;

public class BookingMapper {

    public static BookingDTO toBookingDTO(Booking booking, Set<ServiceDTO> services, SalonDTO salon, UserDTO user) {

        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setId(booking.getId());
        bookingDTO.setCustomerId(booking.getCustomerId());
        bookingDTO.setStatus(booking.getStatus());
        bookingDTO.setStartTime(booking.getStartTime());
        bookingDTO.setEndTime(booking.getEndTime());
        bookingDTO.setSalonId(booking.getSalonId());
        bookingDTO.setServiceIds(booking.getServiceIds());
        bookingDTO.setTotalPrice(booking.getTotalPrice());
        bookingDTO.setServiceDTOS(services);
        bookingDTO.setUserDTO(user);
        bookingDTO.setSalonDTO(salon);

        return bookingDTO;
    }
}
