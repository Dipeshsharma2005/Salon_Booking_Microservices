package com.salon.notifications.mapper;

import com.salon.notifications.model.Notification;
import com.salon.notifications.payload.dto.BookingDTO;
import com.salon.notifications.payload.dto.NotificationDTO;
import com.salon.notifications.payload.dto.ServiceDTO;

import java.util.Set;

public class NotificationMapper {

    public static NotificationDTO toNotificationDTO(Notification notification, BookingDTO bookingDTO, Set<ServiceDTO> serviceDTO) {

        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setId(notification.getId());
        notificationDTO.setType(notification.getType());
        notificationDTO.setDescription(notification.getDescription());
        notificationDTO.setBookingId(bookingDTO.getId());
        notificationDTO.setUserId(notification.getUserId());
        notificationDTO.setIsRead(notification.getIsRead());
        notificationDTO.setSalonId(notification.getSalonId());
        notificationDTO.setCreatedAt(notification.getCreatedAt());
        notificationDTO.setBooking(bookingDTO);
        notificationDTO.setServiceDTOS(serviceDTO);

        return notificationDTO;
    }
}
