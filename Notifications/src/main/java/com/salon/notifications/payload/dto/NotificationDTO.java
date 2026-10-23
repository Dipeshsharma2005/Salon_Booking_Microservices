package com.salon.notifications.payload.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class NotificationDTO {

    private Long id;

    private String type;

    private String description;

    private Boolean isRead=false;

    private Long userId;

    private Long bookingId;

    private Long salonId;

    private LocalDateTime createdAt;

    private BookingDTO booking;


    private Set<ServiceDTO> serviceDTOS;


}
