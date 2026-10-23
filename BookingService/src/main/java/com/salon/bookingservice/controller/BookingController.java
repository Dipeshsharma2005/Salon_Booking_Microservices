package com.salon.bookingservice.controller;

import com.salon.bookingservice.domain.BookingStatus;
import com.salon.bookingservice.domain.PaymentMethod;
import com.salon.bookingservice.dto.*;
import com.salon.bookingservice.mapper.BookingMapper;
import com.salon.bookingservice.model.Booking;
import com.salon.bookingservice.service.BookingService;
import com.salon.bookingservice.service.client.PaymentFeignClient;
import com.salon.bookingservice.service.client.SalonFeignClient;
import com.salon.bookingservice.service.client.ServiceOfferingFeignClient;
import com.salon.bookingservice.service.client.UserFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final SalonFeignClient salonFeignClient;
    private final UserFeignClient userFeignClient;
    private final ServiceOfferingFeignClient serviceOfferingFeignClient;
    private final PaymentFeignClient paymentFeignClient;

    @PostMapping
    public ResponseEntity<PaymentLinkResponse> createBooking(@RequestParam Long salonId, @RequestParam PaymentMethod paymentMethod, @RequestBody BookingRequest bookingRequest, @RequestHeader("Authorization") String jwt) throws Exception {

        UserDTO userDTO= userFeignClient.getUserProfile(jwt).getBody();

        SalonDTO salonDTO= salonFeignClient.getSalonById(salonId).getBody();

        Set<ServiceDTO> serviceDTOSet= serviceOfferingFeignClient.getServiceOfferingsByIds(bookingRequest.getServiceIds()).getBody();

        if (serviceDTOSet.isEmpty()){
            throw new Exception("Service not found");
        }

        Booking booking = bookingService.createBooking(bookingRequest,userDTO,salonDTO,serviceDTOSet);

        BookingDTO bookingDTO=BookingMapper.toBookingDTO(booking,serviceDTOSet,salonDTO,userDTO);

        PaymentLinkResponse response=paymentFeignClient.createPaymentLink(bookingDTO,paymentMethod,jwt).getBody();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/customer")
    public ResponseEntity<Set<BookingDTO>> getBookingsByCustomer(@RequestHeader("Authorization") String jwt) throws Exception {

        UserDTO userDTO= userFeignClient.getUserProfile(jwt).getBody();

        if (userDTO==null || userDTO.getId()==null){
            throw new Exception("User not found with jwt...");
        }
        List<Booking> bookings = bookingService.getBookingsByCustomer(userDTO.getId());
        return ResponseEntity.ok(getBookingDTOs(bookings));
    }

    @GetMapping("/salon")
    public ResponseEntity<Set<BookingDTO>> getBookingsBySalon(@RequestHeader("Authorization") String jwt) throws Exception {

        SalonDTO salonDTO= salonFeignClient.getSalonByOwnerId(jwt).getBody();

        if (salonDTO==null || salonDTO.getId()==null){
            throw new Exception("Salon not found with jwt...");
        }
        List<Booking> bookings = bookingService.getBookingsBySalon(salonDTO.getId());
        return ResponseEntity.ok(getBookingDTOs(bookings));
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingDTO> getBookingsById(@PathVariable Long bookingId) throws Exception {

        Booking booking = bookingService.getBookingById(bookingId);
        Set<ServiceDTO> serviceDTOS= serviceOfferingFeignClient.getServiceOfferingsByIds(booking.getServiceIds()).getBody();
        UserDTO userDTO = userFeignClient.getUserById(booking.getCustomerId()).getBody();
        SalonDTO salonDTO= salonFeignClient.getSalonById(booking.getSalonId()).getBody();
        return ResponseEntity.ok(BookingMapper.toBookingDTO(booking,serviceDTOS,salonDTO, userDTO));
    }

    @PutMapping("/{bookingId}/status")
    public ResponseEntity<BookingDTO> updateBookingStatus(@PathVariable Long bookingId, @RequestParam BookingStatus status) throws Exception {

        Booking booking = bookingService.updateBooking(bookingId,status);
        Set<ServiceDTO> serviceDTOS= serviceOfferingFeignClient.getServiceOfferingsByIds(booking.getServiceIds()).getBody();
        UserDTO userDTO = userFeignClient.getUserById(booking.getCustomerId()).getBody();
        SalonDTO salonDTO= salonFeignClient.getSalonById(booking.getSalonId()).getBody();
        return ResponseEntity.ok(BookingMapper.toBookingDTO(booking,serviceDTOS,salonDTO,userDTO));
    }

    @GetMapping("/slots/salon/{salonId}/date")
    public ResponseEntity<List<BookinSlotDTO>> getBookedSlot(@PathVariable Long salonId, @RequestParam(required = false) LocalDate date) throws Exception {

        List<Booking> bookings = bookingService.getBookingByDate(date,salonId);

        List<BookinSlotDTO> slotDTOS = bookings.stream().map(booking -> {
            BookinSlotDTO bookinSlotDTO = new BookinSlotDTO();
            bookinSlotDTO.setStartTime(booking.getStartTime());
            bookinSlotDTO.setEndTime(booking.getEndTime());
            return bookinSlotDTO;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(slotDTOS);
    }

    @GetMapping("/report")
    public ResponseEntity<SalonReport> getSalonReport(@RequestHeader("Authorization") String jwt) throws Exception {

        SalonDTO salonDTO= salonFeignClient.getSalonByOwnerId(jwt).getBody();

        SalonReport salonReport = bookingService.getSalonReport(salonDTO.getId());

        return ResponseEntity.ok(salonReport);
    }


    private Set<BookingDTO> getBookingDTOs(List<Booking> bookings){
        return bookings.stream()
                .map(booking -> {
                    Set<ServiceDTO> serviceDTOS= serviceOfferingFeignClient
                            .getServiceOfferingsByIds(booking.getServiceIds()).getBody();
                    SalonDTO salonDTO;
                    UserDTO userDTO;
                    try {
                        salonDTO = salonFeignClient.getSalonById(booking.getSalonId()).getBody();
                        userDTO = userFeignClient.getUserById(booking.getCustomerId()).getBody();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    return BookingMapper.toBookingDTO(booking,serviceDTOS,salonDTO,userDTO);

                }).collect(Collectors.toSet());
    }
}
