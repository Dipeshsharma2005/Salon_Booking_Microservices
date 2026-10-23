package com.salon.paymentservice.messaging;

import com.salon.paymentservice.payload.dto.NotificationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationEventProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sentNotification(Long bookingId,Long userId, Long salonId){

        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setBookingId(bookingId);
        notificationDTO.setUserId(userId);
        notificationDTO.setSalonId(salonId);
        notificationDTO.setType("Booking");
        notificationDTO.setDescription("Your Booking got confirmed!");

        rabbitTemplate.convertAndSend("notification-queue", notificationDTO);
    }
}
