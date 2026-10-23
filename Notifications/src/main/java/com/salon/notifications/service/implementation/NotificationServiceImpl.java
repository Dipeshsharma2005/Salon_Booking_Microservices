package com.salon.notifications.service.implementation;

import com.salon.notifications.mapper.NotificationMapper;
import com.salon.notifications.model.Notification;
import com.salon.notifications.payload.dto.BookingDTO;
import com.salon.notifications.payload.dto.NotificationDTO;
import com.salon.notifications.payload.dto.ServiceDTO;
import com.salon.notifications.repository.NotificationRepository;
import com.salon.notifications.service.NotificationService;
import com.salon.notifications.service.client.BookingFeignClient;
import com.salon.notifications.service.client.ServiceOfferingFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final BookingFeignClient bookingFeignClient;
    private final ServiceOfferingFeignClient serviceOfferingFeignClient;
    private final RealTimeCommunicationService realTimeCommunicationService;

    @Override
    public NotificationDTO createNotification(Notification notification) throws Exception {

        Notification savedNotification = notificationRepository.save(notification);

        BookingDTO bookingDTO = bookingFeignClient.getBookingsById(savedNotification.getBookingId()).getBody();

        Set<ServiceDTO> serviceDTO = serviceOfferingFeignClient.getServiceOfferingsByIds(bookingDTO.getServiceIds()).getBody();

        NotificationDTO notificationDTO = NotificationMapper.toNotificationDTO(savedNotification,bookingDTO,serviceDTO);

        // FROM HERE WE WILL SEND NOTIFICATION TO CLIENT AFTER ADDING WEBSOCKET

        realTimeCommunicationService.sendNotification(notificationDTO);

        return notificationDTO;
    }

    @Override
    public List<Notification> getAllNotificationsByUserId(Long userId) {
        return notificationRepository.findByUserId(userId);
    }

    @Override
    public List<Notification> getAllNotificationsBySalonId(Long salonId) {
        return notificationRepository.findBySalonId(salonId);
    }

    @Override
    public Notification markNotificationAsRead(Long notificationId) throws Exception {
        return notificationRepository.findById(notificationId).map(
                notification -> {
                    notification.setIsRead(true);
                    return notificationRepository.save(notification);
        }).orElseThrow(()->new Exception("Notification Not Found"));
    }
}
