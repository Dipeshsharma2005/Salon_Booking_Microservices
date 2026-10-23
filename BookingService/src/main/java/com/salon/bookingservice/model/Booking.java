package com.salon.bookingservice.model;

import com.salon.bookingservice.domain.BookingStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long salonId;

    private Long customerId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @ElementCollection
    private Set<Long> serviceIds;

    @Enumerated(EnumType.STRING)
    private BookingStatus status=BookingStatus.PENDING;

    private int totalPrice;
}
