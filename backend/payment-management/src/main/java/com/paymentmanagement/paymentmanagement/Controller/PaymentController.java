package com.paymentmanagement.paymentmanagement.Controller;

import com.paymentmanagement.paymentmanagement.Dto.PaymentRequest;
import com.paymentmanagement.paymentmanagement.Dto.PaymentResponse;
import com.paymentmanagement.paymentmanagement.Entity.Payment;
import com.paymentmanagement.paymentmanagement.Service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    // Endpoint to list all payments
    @GetMapping
    public List<Payment> listAllPayments() {
        return paymentService.getAllPayments();
    }

    // Endpoint to process a new payment
    @PostMapping
    public ResponseEntity<PaymentResponse> processPayment(@RequestBody PaymentRequest paymentRequest) {
        // Call service to process payment
        PaymentResponse response = paymentService.processPayment(paymentRequest);

        // Return the processed payment data
        return ResponseEntity.ok(response);
    }

    // Endpoint to retrieve a payment by its ID
    @GetMapping("/{paymentId}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable int paymentId) {
        Optional<Payment> payment = paymentService.getPaymentById(paymentId);
        return payment.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Endpoint to update payment details
    @PutMapping("/{paymentId}")
    public ResponseEntity<Payment> updatePayment(@PathVariable int paymentId, @RequestBody Payment updatedPayment) {
        try {
            Payment payment = paymentService.updatePayment(paymentId, updatedPayment);
            return ResponseEntity.ok(payment);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Endpoint to delete a payment
    @DeleteMapping("/{paymentId}")
    public ResponseEntity<Void> deletePayment(@PathVariable int paymentId) {
        paymentService.deletePayment(paymentId);
        return ResponseEntity.noContent().build();
    }

    // Endpoint to confirm a payment by orderId and amount
    @PostMapping("/confirm")
    public ResponseEntity<?> confirmPayment(
            @RequestParam int orderId,
            @RequestParam double amount) {
        try {
            Payment confirmedPayment = paymentService.confirmPayment(orderId, BigDecimal.valueOf(amount));
            return ResponseEntity.ok(confirmedPayment);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
