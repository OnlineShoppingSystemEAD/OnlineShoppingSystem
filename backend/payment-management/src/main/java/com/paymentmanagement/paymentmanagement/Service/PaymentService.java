package com.paymentmanagement.paymentmanagement.Service;


import com.paymentmanagement.paymentmanagement.Entity.Payment;
import com.paymentmanagement.paymentmanagement.Repository.PaymentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepo paymentRepository;

    public Payment createPayment(Payment payment) {
        payment.setStatus(Payment.Status.PENDING);
        return paymentRepository.save(payment);
    }

    public Optional<Payment> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }

    public Payment updatePayment(Long id, Payment updatedPayment) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setAmount(updatedPayment.getAmount());
        payment.setCurrency(updatedPayment.getCurrency());
        payment.setStatus(updatedPayment.getStatus());
        return paymentRepository.save(payment);
    }

    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }

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

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}
