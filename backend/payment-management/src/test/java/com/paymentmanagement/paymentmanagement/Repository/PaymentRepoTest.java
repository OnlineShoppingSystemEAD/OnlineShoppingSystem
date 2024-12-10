package com.paymentmanagement.paymentmanagement.Repository;

import com.paymentmanagement.paymentmanagement.Entity.Payment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PaymentRepoTest {

    @Autowired
    private PaymentRepo paymentRepo;

    @Test
    void shouldSaveAndRetrievePaymentById() {
        // Arrange
        Payment payment = new Payment();
        payment.setOrderId(1001);
        payment.setAmount(500.0);
        payment.setCurrency("USD");
        payment.setStatus(Payment.Status.PENDING);
        payment.setCreatedAt(LocalDateTime.now());

        // Act
        Payment savedPayment = paymentRepo.save(payment);
        Optional<Payment> retrievedPayment = paymentRepo.findById(savedPayment.getId());

        // Assert
        assertThat(retrievedPayment).isPresent();
        assertThat(retrievedPayment.get().getOrderId()).isEqualTo(1001);
        assertThat(retrievedPayment.get().getAmount()).isEqualTo(BigDecimal.valueOf(500.00));
    }

    @Test
    void shouldDeletePaymentById() {
        // Arrange
        Payment payment = new Payment();
        payment.setOrderId(1002);
        payment.setAmount(250.0);
        payment.setCurrency("USD");
        payment.setStatus(Payment.Status.PENDING);
        payment.setCreatedAt(LocalDateTime.now());

        Payment savedPayment = paymentRepo.save(payment);
        int paymentId = savedPayment.getId();

        // Act
        paymentRepo.deleteById(paymentId);
        Optional<Payment> retrievedPayment = paymentRepo.findById(paymentId);

        // Assert
        assertThat(retrievedPayment).isEmpty();
    }

    @Test
    void shouldFindPaymentByOrderIdAndAmount() {
        // Arrange
        Payment payment = new Payment();
        payment.setOrderId(1003);
        payment.setAmount(300.0);
        payment.setCurrency("USD");
        payment.setStatus(Payment.Status.PENDING);
        payment.setCreatedAt(LocalDateTime.now());

        paymentRepo.save(payment);

        // Act
        Optional<Payment> retrievedPayment = paymentRepo.findByOrderIdAndAmount(1003, 300.0);

        // Assert
        assertThat(retrievedPayment).isPresent();
        assertThat(retrievedPayment.get().getCurrency()).isEqualTo("USD");
    }

    @Test
    void shouldFindPaymentsByUserId() {
        // Arrange
        Payment payment1 = new Payment();
        payment1.setUserId(1);
        payment1.setOrderId(1004);
        payment1.setAmount(400.0);
        payment1.setCurrency("USD");
        payment1.setStatus(Payment.Status.PAID);
        payment1.setCreatedAt(LocalDateTime.now());

        Payment payment2 = new Payment();
        payment2.setUserId(1);
        payment2.setOrderId(1005);
        payment2.setAmount(150.0);
        payment2.setCurrency("USD");
        payment2.setStatus(Payment.Status.PENDING);
        payment2.setCreatedAt(LocalDateTime.now());

        paymentRepo.save(payment1);
        paymentRepo.save(payment2);

        // Act
        List<Payment> payments = paymentRepo.findByUserId(1);

        // Assert
        assertThat(payments).hasSize(2);
        assertThat(payments.getFirst().getUserId()).isEqualTo(1);
    }

    @Test
    void shouldFindPaymentsByUserIdAndStatus() {
        // Arrange
        Payment payment1 = new Payment();
        payment1.setUserId(2);
        payment1.setOrderId(1006);
        payment1.setAmount(600.0);
        payment1.setCurrency("USD");
        payment1.setStatus(Payment.Status.PAID);
        payment1.setCreatedAt(LocalDateTime.now());

        Payment payment2 = new Payment();
        payment2.setUserId(2);
        payment2.setOrderId(1007);
        payment2.setAmount(350.0);
        payment2.setCurrency("USD");
        payment2.setStatus(Payment.Status.PENDING);
        payment2.setCreatedAt(LocalDateTime.now());

        Payment payment3 = new Payment();
        payment3.setUserId(2);
        payment3.setOrderId(1008);
        payment3.setAmount(200.0);
        payment3.setCurrency("USD");
        payment3.setStatus(Payment.Status.FAILED);
        payment3.setCreatedAt(LocalDateTime.now());

        paymentRepo.save(payment1);
        paymentRepo.save(payment2);
        paymentRepo.save(payment3);

        // Act
        List<Payment> payments = paymentRepo.findByUserIdAndStatusIn(2, Arrays.asList(Payment.Status.PAID, Payment.Status.PENDING));

        // Assert
        assertThat(payments).hasSize(2);
        assertThat(payments.stream().allMatch(p -> p.getStatus() != Payment.Status.FAILED)).isTrue();
    }
}
