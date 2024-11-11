package com.holdme.holdmeapi_restfull.service;

import com.holdme.holdmeapi_restfull.dto.PaymentCaptureResponse;
import com.holdme.holdmeapi_restfull.dto.PaymentOrderResponse;
import jakarta.mail.MessagingException;

public interface CheckoutService {
    PaymentOrderResponse createPayment(Integer inscriptionId, String returnUrl, String cancelUrl) throws MessagingException;

    PaymentCaptureResponse capturePayment(String orderId) throws MessagingException;
}
