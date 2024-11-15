package com.paymentmanagement.paymentmanagement.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymentmanagement.paymentmanagement.Entity.Payment;
import com.paymentmanagement.paymentmanagement.Service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;


import java.math.BigDecimal;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;

    private Payment payment;

    @BeforeEach
    void setUp() {
        payment = new Payment();
        payment.setOrderId("o12345");
        payment.setAmount(BigDecimal.valueOf(150.00));
        payment.setCurrency("USD");
        payment.setStatus(Payment.Status.PENDING);
    }

    @Test
    void testCreatePayment() throws Exception {
        when(paymentService.createPayment(any(Payment.class))).thenReturn(payment);

        mockMvc.perform(post("/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(payment)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.orderId").value(payment.getOrderId()))
                .andExpect(jsonPath("$.amount").value(payment.getAmount()))
                .andExpect(jsonPath("$.status").value(payment.getStatus().toString()));

        verify(paymentService, times(1)).createPayment(any(Payment.class));
    }

    @Test
    void testGetPaymentById() throws Exception {
        when(paymentService.getPaymentById(1L)).thenReturn(java.util.Optional.of(payment));

        mockMvc.perform(get("/payments/{paymentId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(payment.getOrderId()))
                .andExpect(jsonPath("$.amount").value(payment.getAmount()))
                .andExpect(jsonPath("$.status").value(payment.getStatus().toString()));

        verify(paymentService, times(1)).getPaymentById(1L);
    }

    @Test
    void testGetPaymentNotFound() throws Exception {
        when(paymentService.getPaymentById(999L)).thenReturn(java.util.Optional.empty());

        mockMvc.perform(get("/payments/{paymentId}", 999L))
                .andExpect(status().isNotFound());

        verify(paymentService, times(1)).getPaymentById(999L);
    }

    @Test
    void testUpdatePayment() throws Exception {
        Payment updatedPayment = new Payment();
        updatedPayment.setAmount(BigDecimal.valueOf(200.00));
        updatedPayment.setCurrency("USD");
        updatedPayment.setStatus(Payment.Status.CONFIRMED);

        when(paymentService.updatePayment(1L, updatedPayment)).thenReturn(updatedPayment);

        mockMvc.perform(put("/payments/{paymentId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedPayment)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(updatedPayment.getAmount()))
                .andExpect(jsonPath("$.status").value(updatedPayment.getStatus().toString()));

        verify(paymentService, times(1)).updatePayment(1L, updatedPayment);
    }

    @Test
    void testDeletePayment() throws Exception {
        doNothing().when(paymentService).deletePayment(1L);

        mockMvc.perform(delete("/payments/{paymentId}", 1L))
                .andExpect(status().isNoContent());

        verify(paymentService, times(1)).deletePayment(1L);
    }

    @Test
    void testConfirmPayment() throws Exception {
        when(paymentService.confirmPayment(1L, "o12345", BigDecimal.valueOf(150.00))).thenReturn(payment);

        mockMvc.perform(post("/payments/{paymentId}/confirm", 1L)
                        .param("orderId", "o12345")
                        .param("amount", "150.00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CONFIRMED"));

        verify(paymentService, times(1)).confirmPayment(1L, "o12345", BigDecimal.valueOf(150.00));
    }

    @Test
    void testConfirmPaymentFailure() throws Exception {
        when(paymentService.confirmPayment(1L, "o12345", BigDecimal.valueOf(150.00)))
                .thenThrow(new RuntimeException("Payment confirmation failed"));

        mockMvc.perform(post("/payments/{paymentId}/confirm", 1L)
                        .param("orderId", "o12345")
                        .param("amount", "150.00"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Payment confirmation failed"));

        verify(paymentService, times(1)).confirmPayment(1L, "o12345", BigDecimal.valueOf(150.00));
    }

}
