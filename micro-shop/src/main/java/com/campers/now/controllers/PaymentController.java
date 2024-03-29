package com.campers.now.controllers;

import com.campers.now.models.Command;
import com.campers.now.models.Payment;
import com.campers.now.models.ProductCommand;
import com.campers.now.repositories.CommandRepository;
import com.campers.now.repositories.PaymentRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin

@RequestMapping("/payments")
public class PaymentController {

    @Value("${stripe.apiKey}")
    private String stripeApiKey;

    private final CommandRepository commandRepository;
    private final PaymentRepository paymentRepository;

    public PaymentController(CommandRepository commandRepository, PaymentRepository paymentRepository) {
        this.commandRepository = commandRepository;
        this.paymentRepository = paymentRepository;
    }

    @PostMapping
    public ResponseEntity<String> makePayment(@RequestBody ProductCommand productCommand,
                                              @RequestParam String paymentMethod) {
        try {
            Stripe.apiKey = stripeApiKey;


            PaymentIntentCreateParams createParams = new PaymentIntentCreateParams.Builder()
                    .setCurrency("usd")
                    .setAmount((long) (productCommand.getPriceTotal() * 100)) // Stripe requires the amount in cents
                    .build();

            PaymentIntent paymentIntent = PaymentIntent.create(createParams);

            Payment payment = new Payment();
            payment.setPaymentMethod(paymentMethod);
            payment.setPaymentStatus("PAID");
            payment.setPaymentResponse(paymentIntent.getClientSecret());


            Command command = productCommand.getCommand();
            payment.setCommandId(command.getId());
            paymentRepository.save(payment);


            return ResponseEntity.ok(paymentIntent.getClientSecret());
        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Payment processing failed: " + e.getMessage());
        }
    }
}
