package com.customer.consumer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CustomerData(
        Long id,
        String first_name,
        String last_name,
        String email,
        String created_at,
        String updated_at
) {
}