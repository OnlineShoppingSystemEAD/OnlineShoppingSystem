package com.paymentmanagement.paymentmanagement.Service;


import com.paymentmanagement.paymentmanagement.Dto.PaymentRequest;
import com.paymentmanagement.paymentmanagement.Dto.PaymentResponse;
import com.paymentmanagement.paymentmanagement.Entity.Payment;
import com.paymentmanagement.paymentmanagement.Repository.PaymentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepo paymentRepository;


    // Method to handle a new payment request
    public PaymentResponse processPayment(PaymentRequest paymentRequest) {
        // Generate a unique paymentId
        String paymentId = UUID.randomUUID().toString();

        // Create a new Payment entity
        Payment payment = new Payment();
        payment.setOrderId(paymentRequest.getOrderId());
        payment.setAmount(BigDecimal.valueOf(paymentRequest.getTotalAmount()));
        payment.setCurrency("USD");
        payment.setStatus(Payment.Status.PENDING);
        payment.setPaymentId(paymentId);
        payment.setCreatedAt(LocalDate.now().atStartOfDay());

        // Save to the database
        paymentRepository.save(payment);

        // Return a response
        return new PaymentResponse(
                payment.getOrderId(),
                payment.getPaymentId(),
                payment.getAmount().doubleValue()
        );
    }
    // Method to retrieve a payment by ID
    public Optional<Payment> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }

    // Update an existing payment
    public Payment updatePayment(Long id, Payment updatedPayment) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setAmount(updatedPayment.getAmount());
        payment.setCurrency(updatedPayment.getCurrency());
        payment.setStatus(updatedPayment.getStatus());
        return paymentRepository.save(payment);
    }

    // Delete a payment
    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }

    // Confirm a payment
    public Payment confirmPayment(Long paymentId, String orderId, BigDecimal amount) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        if (payment.getOrderId().equals(orderId) && payment.getAmount().compareTo(amount) == 0) {
            payment.setStatus(Payment.Status.CONFIRMED);
            return paymentRepository.save(payment);
        } else {
            throw new RuntimeException("Payment confirmation failed due to mismatched data");
        }
    }

    // Retrieve all payments
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}
