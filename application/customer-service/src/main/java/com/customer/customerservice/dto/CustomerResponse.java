package com.customer.customerservice.dto;

import java.time.OffsetDateTime;

public record CustomerResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}