package com.customer.consumer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CustomerChangeEvent(
        @JsonProperty("op")
        String operation,

        CustomerData before,

        CustomerData after
) {
}