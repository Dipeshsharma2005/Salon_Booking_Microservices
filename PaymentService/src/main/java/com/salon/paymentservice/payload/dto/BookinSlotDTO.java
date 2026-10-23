package com.salon.paymentservice.payload.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookinSlotDTO {

    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
