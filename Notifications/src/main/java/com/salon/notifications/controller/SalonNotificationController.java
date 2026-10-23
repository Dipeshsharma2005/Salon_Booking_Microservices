package com.salon.notifications.controller;

import com.salon.notifications.mapper.NotificationMapper;
import com.salon.notifications.model.Notification;
import com.salon.notifications.payload.dto.BookingDTO;
import com.salon.notifications.payload.dto.NotificationDTO;
import com.salon.notifications.payload.dto.ServiceDTO;
import com.salon.notifications.service.NotificationService;
import com.salon.notifications.service.client.BookingFeignClient;
import com.salon.notifications.service.client.ServiceOfferingFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications/salon-owner")
public class SalonNotificationController {

    private final NotificationService notificationService;
    private final BookingFeignClient bookingFeignClient;
    private final ServiceOfferingFeignClient serviceOfferingFeignClient;

    @GetMapping("/salon/{salonId}")
    public ResponseEntity<List<NotificationDTO>> getNotificationsBySalonId(@PathVariable Long salonId)  {

        List<Notification> notifications= notificationService.getAllNotificationsBySalonId(salonId);

        List<NotificationDTO>notificationDTOS=notifications.stream().map(notification -> {
            BookingDTO bookingDTO= null;
            Set<ServiceDTO> serviceDTOS= null;
            try {
                bookingDTO = bookingFeignClient.getBookingsById(notification.getBookingId()).getBody();
                serviceDTOS = serviceOfferingFeignClient.getServiceOfferingsByIds(bookingDTO.getServiceIds()).getBody();

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return NotificationMapper.toNotificationDTO(notification,bookingDTO,serviceDTOS);
        }).collect(Collectors.toList());

        return ResponseEntity.ok(notificationDTOS);
    }
}
