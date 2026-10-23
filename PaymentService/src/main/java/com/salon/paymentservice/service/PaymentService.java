package com.salon.paymentservice.service;

import com.razorpay.PaymentLink;
import com.razorpay.RazorpayException;
import com.salon.paymentservice.domain.PaymentMethod;
import com.salon.paymentservice.model.PaymentOrder;
import com.salon.paymentservice.payload.dto.BookingDTO;
import com.salon.paymentservice.payload.dto.UserDTO;
import com.salon.paymentservice.payload.response.PaymentLinkResponse;
import com.stripe.exception.StripeException;

public interface PaymentService {

    PaymentLinkResponse createPaymentOrder(UserDTO userDTO, BookingDTO  bookingDTO, PaymentMethod paymentMethod) throws RazorpayException, StripeException;

    PaymentOrder getPaymentOrderById(Long id) throws Exception;

    PaymentOrder getPaymentOrderByPaymentLinkId(String paymentId);

    PaymentLink createRazorpayPaymentLink(UserDTO userDTO, Long amount,Long orderId) throws RazorpayException;//orderId is PaymentOrderId

    String createStripePaymentLink(UserDTO userDTO, Long amount,Long orderId) throws StripeException;

    Boolean proceedPayment(PaymentOrder paymentOrder,String paymentId,String paymentLinkId) throws Exception;
}
