package com.danielcode.customer;

import com.danielcode.AbstractTestContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerRepositoryTest extends AbstractTestContainer {

    @Autowired
    private CustomerRepository underTest;

    @BeforeEach
    void setup(){

    }

    @Test
    void existsByEmail() {
        // GIVEN
        Customer customer = new Customer(
                FAKER.name().firstName(),
                FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID(),
                FAKER.number().numberBetween(16,99)
        );
        underTest.save(customer);

        // WHEN
        boolean expected = underTest.existsByEmail(customer.getEmail());

        // THEN
        assertThat(expected).isTrue();
    }

    @Test
    void doesNotExistByEmail(){
        // GIVEN
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();

        // WHEN
        boolean expected = underTest.existsByEmail(email);

        // THEN
        assertThat(expected).isFalse();
    }
}