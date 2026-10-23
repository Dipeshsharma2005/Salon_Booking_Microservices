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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    private final BookingFeignClient bookingFeignClient;
    private final ServiceOfferingFeignClient serviceOfferingFeignClient;

    @PostMapping
    public ResponseEntity<NotificationDTO> createNotification(@RequestBody Notification notification) throws Exception {

        return ResponseEntity.ok(notificationService.createNotification(notification));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationDTO>> getNotificationsByUserId(@PathVariable Long userId)  {

        List<Notification> notifications= notificationService.getAllNotificationsByUserId(userId);

        List<NotificationDTO>notificationDTOS=notifications.stream().map(notification -> {
            BookingDTO bookingDTO= null;
            Set<ServiceDTO> serviceDTOS= null;
            try {
                bookingDTO = bookingFeignClient.getBookingsById(notification.getBookingId()).getBody();
                serviceDTOS= serviceOfferingFeignClient.getServiceOfferingsByIds(bookingDTO.getServiceIds()).getBody();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return NotificationMapper.toNotificationDTO(notification,bookingDTO,serviceDTOS);
        }).collect(Collectors.toList());

        return ResponseEntity.ok(notificationDTOS);
    }

    @PutMapping("/{notificationId}/read")
    public ResponseEntity<NotificationDTO> markNotificationAsRead(@PathVariable Long notificationId) throws Exception {

        Notification notification = notificationService.markNotificationAsRead(notificationId);

        BookingDTO bookingDTO = bookingFeignClient.getBookingsById(notification.getBookingId()).getBody();

        Set<ServiceDTO> serviceDTOS= serviceOfferingFeignClient.getServiceOfferingsByIds(bookingDTO.getServiceIds()).getBody();

        return ResponseEntity.ok(NotificationMapper.toNotificationDTO(notification,bookingDTO,serviceDTOS));
    }

    @PostMapping("/dummy")
    public ResponseEntity<?> dummyController(@RequestBody Notification notification) throws Exception {

        return ResponseEntity.ok(notificationService.createNotification(notification));
    }
}
