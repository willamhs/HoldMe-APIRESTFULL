package com.holdme.holdmeapi_restfull.service.impl;

import com.holdme.holdmeapi_restfull.dto.InscriptionDetailsDTO;
import com.holdme.holdmeapi_restfull.dto.PaymentCaptureResponse;
import com.holdme.holdmeapi_restfull.dto.PaymentOrderResponse;
import com.holdme.holdmeapi_restfull.integration.notification.email.dto.Mail;
import com.holdme.holdmeapi_restfull.integration.notification.email.service.EmailService;
import com.holdme.holdmeapi_restfull.integration.payment.paypal.dto.OrderCaptureResponse;
import com.holdme.holdmeapi_restfull.integration.payment.paypal.dto.OrderResponse;
import com.holdme.holdmeapi_restfull.integration.payment.paypal.service.PaypalService;
import com.holdme.holdmeapi_restfull.service.CheckoutService;
import com.holdme.holdmeapi_restfull.service.InscriptionService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@RequiredArgsConstructor
@Service
public class CheckoutServiceImpl implements CheckoutService {

    private final PaypalService payPalService;
    private final InscriptionService inscriptionService;
    private final EmailService emailService;

    @Value("${spring.mail.username}")
    private String mailFrom;

    @Override
    public PaymentOrderResponse createPayment(Integer inscriptionId, String returnUrl, String cancelUrl) throws MessagingException{
        OrderResponse orderResponse =payPalService.createOrder(inscriptionId, returnUrl, cancelUrl);

        String paypalUrl = orderResponse
                .getLinks()
                .stream()
                .filter(link -> link.getRel().equals("approve"))
                .findFirst()
                .orElseThrow(RuntimeException::new)
                .getHref();

        return new PaymentOrderResponse(paypalUrl);

    }

    @Override
    public PaymentCaptureResponse capturePayment(String orderId) throws MessagingException{
        OrderCaptureResponse orderCaptureResponse = payPalService.captureOrder(orderId);
        boolean completed = orderCaptureResponse.getStatus().equals("COMPLETED");

        PaymentCaptureResponse paypalCaptureResponse = new PaymentCaptureResponse();
        paypalCaptureResponse.setCompleted(completed);

        if (completed) {
            String purchaseIdStr = orderCaptureResponse.getInscriptionUnits().get(0).getReferenceId();
            InscriptionDetailsDTO inscriptionDetailsDTO = inscriptionService.confirmInscription(Integer.parseInt(purchaseIdStr));
            paypalCaptureResponse.setInscriptionId(inscriptionDetailsDTO.getId());
            sendInscriptionConfirmationEmail(inscriptionDetailsDTO);
        }
        return paypalCaptureResponse;
    }

    private void sendInscriptionConfirmationEmail(InscriptionDetailsDTO inscriptionDetailsDTO) throws MessagingException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        Map<String, Object> model = new HashMap<>();
        model.put("user", userEmail);
        model.put("total", inscriptionDetailsDTO.getTotal());
        model.put("eventUrl", "http://localhost:4200/inscription/" + inscriptionDetailsDTO.getId());

        Mail mail = emailService.createMail(
                userEmail,
                "Confirmacion de Inscripcion",
                model,
                mailFrom
        );
        emailService.sendMail(mail, "email/inscription-confirmation-template");
    }
}
