package com.paymentmanagement.paymentmanagement.Service;

import com.paymentmanagement.paymentmanagement.Entity.Payment;
import com.paymentmanagement.paymentmanagement.Repository.PaymentRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class PaymentServiceTest {


    @Mock
    private PaymentRepo paymentRepo;

    @InjectMocks
    private PaymentService paymentService;

    private Payment payment;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        payment = new Payment();
        payment.setPaymentId(1L);
        payment.setOrderId("o12345");
        payment.setAmount(BigDecimal.valueOf(150.00));
        payment.setCurrency("USD");
        payment.setStatus(Payment.Status.PENDING);
        payment.setCreatedAt(LocalDateTime.now());
        payment.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void testCreatePayment() {
        when(paymentRepo.save(any(Payment.class))).thenReturn(payment);

        Payment createdPayment = paymentService.createPayment(payment);

        assertNotNull(createdPayment);
        assertEquals(payment.getOrderId(), createdPayment.getOrderId());
        assertEquals(payment.getAmount(), createdPayment.getAmount());
        verify(paymentRepo, times(1)).save(payment);
    }

    @Test
    void testGetPaymentById() {
        when(paymentRepo.findById(1L)).thenReturn(Optional.of(payment));

        Optional<Payment> fetchedPayment = paymentService.getPaymentById(1L);

        assertTrue(fetchedPayment.isPresent());
        assertEquals(payment.getOrderId(), fetchedPayment.get().getOrderId());
    }

    @Test
    void testUpdatePayment() {
        Payment updatedPayment = new Payment();
        updatedPayment.setAmount(BigDecimal.valueOf(200.00));
        updatedPayment.setCurrency("USD");
        updatedPayment.setStatus(Payment.Status.CONFIRMED);

        when(paymentRepo.findById(1L)).thenReturn(Optional.of(payment));
        when(paymentRepo.save(any(Payment.class))).thenReturn(updatedPayment);

        Payment result = paymentService.updatePayment(1L, updatedPayment);

        assertNotNull(result);
        assertEquals(updatedPayment.getAmount(), result.getAmount());
        assertEquals(updatedPayment.getStatus(), result.getStatus());
    }

    @Test
    void testDeletePayment() {
        when(paymentRepo.findById(1L)).thenReturn(Optional.of(payment));

        paymentService.deletePayment(1L);

        verify(paymentRepo, times(1)).deleteById(1L);
    }

    @Test
    void testConfirmPayment() {
        when(paymentRepo.findById(1L)).thenReturn(Optional.of(payment));

        Payment confirmedPayment = paymentService.confirmPayment(1L, "o12345", BigDecimal.valueOf(150.00));

        assertNotNull(confirmedPayment);
        assertEquals(Payment.Status.CONFIRMED, confirmedPayment.getStatus());
    }


    @Test
    void testConfirmPaymentWithMismatchedAmount() {
        when(paymentRepo.findById(1L)).thenReturn(Optional.of(payment));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            paymentService.confirmPayment(1L, "o12345", BigDecimal.valueOf(200.00));
        });

        assertEquals("Payment confirmation failed due to mismatched data", exception.getMessage());
    }

}
