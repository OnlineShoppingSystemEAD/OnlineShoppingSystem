package com.paymentmanagement.paymentmanagement.Controller;

import com.paymentmanagement.paymentmanagement.Dto.PaymentMethodRequest;
import com.paymentmanagement.paymentmanagement.Dto.PaymentRequest;
import com.paymentmanagement.paymentmanagement.Dto.PaymentResponse;
import com.paymentmanagement.paymentmanagement.Entity.Payment;
import com.paymentmanagement.paymentmanagement.Entity.PaymentMethod;
import com.paymentmanagement.paymentmanagement.Service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentControllerTest {

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListAllPayments() {
        List<Payment> mockPayments = Arrays.asList(
                new Payment(1, BigDecimal.valueOf(100.0), "PAID", 2),
                new Payment(2, BigDecimal.valueOf(200.0), "PENDING", 2)
        );
    }

    @Test
    void testProcessPayment() {
        PaymentRequest paymentRequest = new PaymentRequest(1, BigDecimal.valueOf(100.0), "CREDIT_CARD");
        PaymentResponse mockResponse = new PaymentResponse(1, "PAID");

        when(paymentService.processPayment(paymentRequest)).thenReturn(mockResponse);

        ResponseEntity<PaymentResponse> response = paymentController.processPayment(paymentRequest);

        assertEquals(mockResponse, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
        verify(paymentService, times(1)).processPayment(paymentRequest);
    }

    @Test
    void testGetPaymentById_Found() {
        Payment mockPayment = new Payment(1, BigDecimal.valueOf(100.0), "PAID", 2);
        when(paymentService.getPaymentById(1)).thenReturn(Optional.of(mockPayment));

        ResponseEntity<Payment> response = paymentController.getPaymentById(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockPayment, response.getBody());
        verify(paymentService, times(1)).getPaymentById(1);
    }

    @Test
    void testGetPaymentById_NotFound() {
        when(paymentService.getPaymentById(1)).thenReturn(Optional.empty());

        ResponseEntity<Payment> response = paymentController.getPaymentById(1);

        assertEquals(404, response.getStatusCodeValue());
        verify(paymentService, times(1)).getPaymentById(1);
    }

    @Test
    void testUpdatePayment() {
        Payment updatedPayment = new Payment(1, BigDecimal.valueOf(150.0), "PAID", 2);
        when(paymentService.updatePayment(1, updatedPayment)).thenReturn(updatedPayment);

        ResponseEntity<Payment> response = paymentController.updatePayment(1, updatedPayment);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(updatedPayment, response.getBody());
        verify(paymentService, times(1)).updatePayment(1, updatedPayment);
    }

    @Test
    void testDeletePayment() {
        doNothing().when(paymentService).deletePayment(1);

        ResponseEntity<Void> response = paymentController.deletePayment(1);

        assertEquals(204, response.getStatusCodeValue());
        verify(paymentService, times(1)).deletePayment(1);
    }

    @Test
    void testConfirmPayment() {
        Payment mockPayment = new Payment(1, BigDecimal.valueOf(100.0), "PAID", 2);
        when(paymentService.confirmPayment(1, BigDecimal.valueOf(100.0))).thenReturn(mockPayment);

        ResponseEntity<?> response = paymentController.confirmPayment(1, 100.0);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockPayment, response.getBody());
        verify(paymentService, times(1)).confirmPayment(1, BigDecimal.valueOf(100.0));
    }

    @Test
    void testSavePaymentMethod() {
        PaymentMethodRequest paymentMethodRequest = new PaymentMethodRequest("CREDIT_CARD");
        PaymentMethod mockPaymentMethod = new PaymentMethod(1, "CREDIT_CARD");

        when(paymentService.savePaymentMethod(paymentMethodRequest)).thenReturn(mockPaymentMethod);

        ResponseEntity<PaymentMethod> response = paymentController.savePaymentMethod(paymentMethodRequest);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockPaymentMethod, response.getBody());
        verify(paymentService, times(1)).savePaymentMethod(paymentMethodRequest);
    }

    @Test
    void testGetAllOrderIdsByUserId() {
        List<Integer> mockOrderIds = Arrays.asList(1, 2, 3);
        when(paymentService.getAllOrderIdsByUserId(1)).thenReturn(mockOrderIds);

        ResponseEntity<List<Integer>> response = paymentController.getAllOrderIdsByUserId(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockOrderIds, response.getBody());
        verify(paymentService, times(1)).getAllOrderIdsByUserId(1);
    }

    @Test
    void testGetPendingAndPaidDeliveryOrderIdsByUserId() {
        List<Integer> mockOrderIds = Arrays.asList(10, 11, 12);
        when(paymentService.getPendingAndPaidDeliveryOrderIdsByUserId(1)).thenReturn(mockOrderIds);

        ResponseEntity<List<Integer>> response = paymentController.getPendingAndPaidDeliveryOrderIdsByUserId(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockOrderIds, response.getBody());
        verify(paymentService, times(1)).getPendingAndPaidDeliveryOrderIdsByUserId(1);
    }
}
