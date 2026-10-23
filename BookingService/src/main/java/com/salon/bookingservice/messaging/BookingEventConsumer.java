package com.salon.bookingservice.messaging;

import com.salon.bookingservice.dto.PaymentOrder;
import com.salon.bookingservice.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingEventConsumer {

    private final BookingService bookingService;

    @RabbitListener(queues = "booking-queue")
    public void bookingUpdateEventListener(PaymentOrder paymentOrder) throws Exception {

        bookingService.bookingSuccess(paymentOrder);
    }
}
