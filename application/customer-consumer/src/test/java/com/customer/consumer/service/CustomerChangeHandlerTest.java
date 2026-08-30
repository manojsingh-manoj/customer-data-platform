package com.customer.consumer.service;

import com.customer.consumer.model.CustomerChangeEvent;
import com.customer.consumer.model.CustomerData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerChangeHandlerTest {

    private final CustomerChangeHandler handler =
            new CustomerChangeHandler();

    @Test
    void shouldHandleSnapshot() {

        CustomerData customer = new CustomerData(
                1L,
                "Alice",
                "Smith",
                "alice@example.com",
                "2026-08-30T10:00:00Z",
                "2026-08-30T10:00:00Z"
        );

        CustomerChangeEvent event =
                new CustomerChangeEvent("r", null, customer);

        String result = handler.handle(event);

        assertTrue(result.contains("Customer SNAPSHOT"));
        assertTrue(result.contains("alice@example.com"));
    }

    @Test
    void shouldHandleCreate() {

        CustomerData customer = new CustomerData(
                2L,
                "Bob",
                "Jones",
                "bob@example.com",
                "2026-08-30T10:00:00Z",
                "2026-08-30T10:00:00Z"
        );

        CustomerChangeEvent event =
                new CustomerChangeEvent("c", null, customer);

        String result = handler.handle(event);

        assertTrue(result.contains("Customer CREATED"));
        assertTrue(result.contains("bob@example.com"));
    }

    @Test
    void shouldHandleUpdate() {

        CustomerData before = new CustomerData(
                3L,
                "Carol",
                "Old",
                "old@example.com",
                "2026-08-30T10:00:00Z",
                "2026-08-30T10:00:00Z"
        );

        CustomerData after = new CustomerData(
                3L,
                "Carol",
                "New",
                "new@example.com",
                "2026-08-30T10:00:00Z",
                "2026-08-30T11:00:00Z"
        );

        CustomerChangeEvent event =
                new CustomerChangeEvent("u", before, after);

        String result = handler.handle(event);

        assertTrue(result.contains("Customer UPDATED"));
        assertTrue(result.contains("old@example.com"));
        assertTrue(result.contains("new@example.com"));
    }

    @Test
    void shouldHandleDelete() {

        CustomerData before = new CustomerData(
                4L,
                "David",
                "Jones",
                "david@example.com",
                "2026-08-30T10:00:00Z",
                "2026-08-30T10:00:00Z"
        );

        CustomerChangeEvent event =
                new CustomerChangeEvent("d", before, null);

        String result = handler.handle(event);

        assertTrue(result.contains("Customer DELETED"));
        assertTrue(result.contains("david@example.com"));
    }

    @Test
    void shouldRejectUnknownOperation() {

        CustomerChangeEvent event =
                new CustomerChangeEvent("x", null, null);

        assertThrows(
                IllegalArgumentException.class,
                () -> handler.handle(event)
        );
    }
}