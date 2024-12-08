package com.paymentmanagement.paymentmanagement.Service;

import com.paymentmanagement.paymentmanagement.Dto.PaymentRequest;
import com.paymentmanagement.paymentmanagement.Dto.PaymentResponse;
import com.paymentmanagement.paymentmanagement.Entity.Payment;
import com.paymentmanagement.paymentmanagement.Repository.PaymentRepo;
import jakarta.transaction.Transactional;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@CommonsLog
@Service
public class PaymentService {

    @Autowired
    private PaymentRepo paymentRepository;

    // Method to handle a new payment request
    public PaymentResponse processPayment(PaymentRequest paymentRequest) {

        // Create a new Payment entity
        Payment payment = new Payment();
        payment.setOrderId(paymentRequest.getOrderId());
        payment.setAmount(BigDecimal.valueOf(paymentRequest.getAmount()));
        payment.setCurrency("USD");
        payment.setStatus(Payment.Status.PENDING);
        payment.setCreatedAt(LocalDate.now().atStartOfDay());

        // Save to the database
        paymentRepository.save(payment);

        // Return a response
        return new PaymentResponse(payment.getOrderId(), payment.getId(), payment.getAmount().doubleValue());
    }

    // Method to retrieve a payment by ID
    public Optional<Payment> getPaymentById(int id) {
        return paymentRepository.findById(id);
    }

    // Update an existing payment
    public Payment updatePayment(int id, Payment updatedPayment) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setAmount(updatedPayment.getAmount());
        payment.setCurrency(updatedPayment.getCurrency());
        payment.setStatus(updatedPayment.getStatus());
        return paymentRepository.save(payment);
    }

    // Delete a payment
    public void deletePayment(int id) {
        paymentRepository.deleteById(id);
    }

    // Confirm a payment by orderId and amount
    @Transactional
    public Payment confirmPayment(int orderId, BigDecimal amount) {
        Payment payment = (Payment) paymentRepository.findByOrderIdAndAmount(orderId, amount)
                .orElseThrow(() -> new RuntimeException("Payment not found with matching orderId and amount"));

        if (!payment.getStatus().equals(Payment.Status.PENDING)) {
            throw new RuntimeException("Payment cannot be confirmed as it is not in PENDING status");
        }

        payment.setStatus(Payment.Status.CONFIRMED);
        return paymentRepository.save(payment);
    }

    // Retrieve all payments
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

}
