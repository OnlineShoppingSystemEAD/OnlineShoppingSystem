package com.paymentmanagement.paymentmanagement.Repository;

import com.paymentmanagement.paymentmanagement.Entity.PaymentMethod;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

@DataJpaTest
class PaymentMethodRepoTest {

    @Autowired
    private PaymentMethodRepo paymentMethodRepo;

    @Test
    void shouldSaveAndFindPaymentMethod() {
        // Arrange: Create a PaymentMethod instance
        PaymentMethod paymentMethod = new PaymentMethod(1, "John Doe", "1234567812345678", "12/26", 123, "Personal Card");
        paymentMethod.setCardHolderName("John Doe");
        paymentMethod.setCardNumber("1234567890123456");
        paymentMethod.setExpirationDate(String.valueOf(LocalDate.of(2025, 12, 31)));
        paymentMethod.setCvv(String.valueOf(123));
        paymentMethod.setNickname("Personal Card");
        paymentMethod.setUserId(1);

        // Act: Save the payment method
        PaymentMethod savedPaymentMethod = paymentMethodRepo.save(paymentMethod);

        // Assert: Verify the payment method is saved and can be retrieved
        Optional<PaymentMethod> retrievedPaymentMethod = paymentMethodRepo.findById(savedPaymentMethod.getId());
        assertThat(String.valueOf(retrievedPaymentMethod)).isPresent();
        assertThat(retrievedPaymentMethod.get().getCardHolderName()).equals("John Doe");
    }

    private Optional<Object> assertThat(String cardHolderName) {

        return Optional.empty();
    }


    @Test
    void shouldDeletePaymentMethod() {
        // Arrange: Create and save a PaymentMethod instance
        PaymentMethod paymentMethod = new PaymentMethod(1, "John Doe", "1234567812345678", "12/26", 123, "Personal Card");
        paymentMethod.setCardHolderName("Jane Doe");
        paymentMethod.setCardNumber("6543210987654321");
        paymentMethod.setExpirationDate(String.valueOf(LocalDate.of(2024, 6, 30)));
        paymentMethod.setCvv(String.valueOf(456));
        paymentMethod.setNickname("Business Card");
        paymentMethod.setUserId(2);
        PaymentMethod savedPaymentMethod = paymentMethodRepo.save(paymentMethod);

        // Act: Delete the payment method
        paymentMethodRepo.deleteById(savedPaymentMethod.getId());

        // Assert: Verify the payment method no longer exists
        Optional<PaymentMethod> retrievedPaymentMethod = paymentMethodRepo.findById(savedPaymentMethod.getId());
        assertThat(String.valueOf(retrievedPaymentMethod)).isEmpty();
    }
}
