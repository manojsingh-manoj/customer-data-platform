package com.customer.customerservice.repository;

import com.customer.customerservice.domain.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void shouldSaveAndFindCustomer() {
        Customer customer = new Customer(
                "Alice",
                "Smith",
                "alice@example.com",
                OffsetDateTime.now(),
                OffsetDateTime.now()
        );

        Customer saved = customerRepository.save(customer);

        assertThat(saved.getId()).isNotNull();

        Customer found = customerRepository.findById(saved.getId())
                .orElseThrow();

        assertThat(found.getEmail()).isEqualTo("alice@example.com");
    }
}