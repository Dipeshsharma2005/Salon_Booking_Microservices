package com.salon.bookingservice.dto;


import lombok.Data;

@Data
public class PaymentOrder {

    private Long id;

    private Long amount;

    private String paymentLinkId;

    private Long userId;

    private Long bookingId;

    private Long salonId;

}
