package com.salon.paymentservice.controller;

import com.razorpay.RazorpayException;
import com.salon.paymentservice.domain.PaymentMethod;
import com.salon.paymentservice.model.PaymentOrder;
import com.salon.paymentservice.payload.dto.BookingDTO;
import com.salon.paymentservice.payload.dto.UserDTO;
import com.salon.paymentservice.payload.response.PaymentLinkResponse;
import com.salon.paymentservice.service.PaymentService;
import com.salon.paymentservice.service.client.UserFeignClient;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final UserFeignClient userFeignClient;

    @PostMapping("/create")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(@RequestBody BookingDTO booking, @RequestParam PaymentMethod paymentMethod,@RequestHeader("Authorization") String jwt) throws Exception {

        UserDTO userDTO = userFeignClient.getUserProfile(jwt).getBody();

        PaymentLinkResponse paymentLinkResponse = paymentService.createPaymentOrder(userDTO,booking,paymentMethod);
        return ResponseEntity.ok(paymentLinkResponse);
    }

    @GetMapping("/{paymentOrderId}")
    public ResponseEntity<PaymentOrder> getPaymentOrderById(@PathVariable Long paymentOrderId) throws Exception {

        PaymentOrder paymentOrder = paymentService.getPaymentOrderById(paymentOrderId);
        return ResponseEntity.ok(paymentOrder);
    }

    @PatchMapping("/proceed")
    public ResponseEntity<Boolean> proceedPayment(@RequestParam String paymentId,@RequestParam String paymentLinkId) throws Exception {

        PaymentOrder paymentOrder= paymentService.getPaymentOrderByPaymentLinkId(paymentLinkId);

        Boolean payment = paymentService.proceedPayment(paymentOrder,paymentId,paymentLinkId);
        return ResponseEntity.ok(payment);
    }
}
