package com.paymentmanagement.paymentmanagement.Repository;

import com.paymentmanagement.paymentmanagement.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;

public interface PaymentRepo extends JpaRepository<Payment, Integer> {
    Optional<Payment> findById(int id);

    void deleteById(int id);

    Optional<Object> findByOrderIdAndAmount(int orderId, BigDecimal amount);
}
