package com.salon.notifications.payload.dto;

import com.salon.notifications.domain.BookingStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class BookingDTO {

        private Long id;

        private Long salonId;

        private Long customerId;

        private LocalDateTime startTime;

        private LocalDateTime endTime;

        private Set<Long> serviceIds;

        private BookingStatus status=BookingStatus.PENDING;

        private int totalPrice;


        private Set<ServiceDTO> serviceDTOS;

        private SalonDTO salonDTO;

        private UserDTO userDTO;

}
