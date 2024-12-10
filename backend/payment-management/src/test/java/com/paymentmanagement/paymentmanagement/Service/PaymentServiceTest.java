package com.paymentmanagement.paymentmanagement.Service;

import com.paymentmanagement.paymentmanagement.Dto.PaymentMethodRequest;
import com.paymentmanagement.paymentmanagement.Dto.PaymentRequest;
import com.paymentmanagement.paymentmanagement.Dto.PaymentResponse;
import com.paymentmanagement.paymentmanagement.Entity.Payment;
import com.paymentmanagement.paymentmanagement.Entity.PaymentMethod;
import com.paymentmanagement.paymentmanagement.Repository.PaymentMethodRepo;
import com.paymentmanagement.paymentmanagement.Repository.PaymentRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentServiceTest {

    @Mock
    private PaymentRepo paymentRepository;

    @Mock
    private PaymentMethodRepo paymentMethodRepo;

    @InjectMocks
    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProcessPayment() {
        PaymentRequest paymentRequest = new PaymentRequest(1, 100.0, "CREDIT_CARD");
        Payment mockPayment = new Payment(1, BigDecimal.valueOf(100.0), "PENDING", 2);
        mockPayment.setId(1);
        mockPayment.setOrderId(paymentRequest.getOrderId());
        when(paymentRepository.save(any(Payment.class))).thenReturn(mockPayment);

        PaymentResponse response = paymentService.processPayment(paymentRequest);

        assertNotNull(response);
        assertEquals(1, response.getOrderId());
        assertEquals(100.0, response.getAmount());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testGetPaymentById_Found() {
        Payment mockPayment = new Payment(1, BigDecimal.valueOf(100.0), "PAID", 2);
        when(paymentRepository.findById(1)).thenReturn(Optional.of(mockPayment));

        Optional<Payment> payment = paymentService.getPaymentById(1);

        assertTrue(payment.isPresent());
        assertEquals(mockPayment, payment.get());
        verify(paymentRepository, times(1)).findById(1);
    }

    @Test
    void testGetPaymentById_NotFound() {
        when(paymentRepository.findById(1)).thenReturn(Optional.empty());

        Optional<Payment> payment = paymentService.getPaymentById(1);

        assertFalse(payment.isPresent());
        verify(paymentRepository, times(1)).findById(1);
    }

    @Test
    void testUpdatePayment() {
        Payment existingPayment = new Payment(1, BigDecimal.valueOf(100.0), "PENDING", 2);
        Payment updatedPayment = new Payment(1, BigDecimal.valueOf(200.0), "PAID", 2);
        when(paymentRepository.findById(1)).thenReturn(Optional.of(existingPayment));
        when(paymentRepository.save(existingPayment)).thenReturn(updatedPayment);

        Payment result = paymentService.updatePayment(1, updatedPayment);

        assertEquals(updatedPayment.getAmount(), result.getAmount());
        assertEquals(updatedPayment.getStatus(), result.getStatus());
        verify(paymentRepository, times(1)).findById(1);
        verify(paymentRepository, times(1)).save(existingPayment);
    }

    @Test
    void testDeletePayment() {
        doNothing().when(paymentRepository).deleteById(1);

        paymentService.deletePayment(1);

        verify(paymentRepository, times(1)).deleteById(1);
    }

    @Test
    void testConfirmPayment() {
        Payment mockPayment = new Payment(1, BigDecimal.valueOf(100.0), "PENDING", 2);
        when(paymentRepository.findByOrderIdAndAmount(1, BigDecimal.valueOf(100.0))).thenReturn(Optional.of(mockPayment));
        when(paymentRepository.save(mockPayment)).thenReturn(mockPayment);

        Payment result = paymentService.confirmPayment(1, BigDecimal.valueOf(100.0));

        assertEquals(Payment.Status.CONFIRMED, result.getStatus());
        verify(paymentRepository, times(1)).findByOrderIdAndAmount(1, BigDecimal.valueOf(100.0));
        verify(paymentRepository, times(1)).save(mockPayment);
    }

    @Test
    void testGetAllPayments() {
        List<Payment> mockPayments = Arrays.asList(
                new Payment(1, BigDecimal.valueOf(100.0), "PAID", 2),
                new Payment(2, BigDecimal.valueOf(200.0), "PENDING", 2)
        );
        when(paymentRepository.findAll()).thenReturn(mockPayments);

        List<Payment> payments = paymentService.getAllPayments();

        assertEquals(2, payments.size());
        verify(paymentRepository, times(1)).findAll();
    }

    @Test
    void testSavePaymentMethod() {
        PaymentMethodRequest request = new PaymentMethodRequest(1, "John Doe", "1234567812345678", "12/26", 123, "Personal Card");
        PaymentMethod mockPaymentMethod = new PaymentMethod(1, "John Doe", "1234567812345678", "12/26", 123, "Personal Card");

        when(paymentMethodRepo.save(any(PaymentMethod.class))).thenReturn(mockPaymentMethod);

        PaymentMethod result = paymentService.savePaymentMethod(request);

        assertNotNull(result);
        assertEquals("John Doe", result.getCardHolderName());
        verify(paymentMethodRepo, times(1)).save(any(PaymentMethod.class));
    }

    @Test
    void testGetAllOrderIdsByUserId() {
        List<Payment> mockPayments = Arrays.asList(
                new Payment(1, BigDecimal.valueOf(100.0), "PAID", 1),
                new Payment(2, BigDecimal.valueOf(200.0), "PENDING", 2)
        );
        when(paymentRepository.findByUserId(1)).thenReturn(mockPayments);

        List<Integer> orderIds = paymentService.getAllOrderIdsByUserId(1);

        assertEquals(Arrays.asList(1, 2), orderIds);
        verify(paymentRepository, times(1)).findByUserId(1);
    }

    @Test
    void testGetPendingAndPaidDeliveryOrderIdsByUserId() {
        List<Payment> mockPayments = Arrays.asList(
                new Payment(1, BigDecimal.valueOf(100.0), "PENDING", 1),
                new Payment(2, BigDecimal.valueOf(200.0), "PAID", 2)
        );
        when(paymentRepository.findByUserIdAndStatusIn(1, Arrays.asList(Payment.Status.PENDING, Payment.Status.PAID))).thenReturn(mockPayments);

        List<Integer> orderIds = paymentService.getPendingAndPaidDeliveryOrderIdsByUserId(1);

        assertEquals(Arrays.asList(1, 2), orderIds);
        verify(paymentRepository, times(1)).findByUserIdAndStatusIn(1, Arrays.asList(Payment.Status.PENDING, Payment.Status.PAID));
    }
}
