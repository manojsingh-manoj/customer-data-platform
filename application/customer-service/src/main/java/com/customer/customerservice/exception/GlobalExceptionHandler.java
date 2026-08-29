package com.customer.customerservice.exception;

import com.customer.customerservice.dto.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleCustomerNotFound(CustomerNotFoundException exception) {
        return new ApiError(
                "CUSTOMER_NOT_FOUND",
                exception.getMessage(),
                OffsetDateTime.now()
        );
    }

    @ExceptionHandler(DuplicateCustomerEmailException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleDuplicateCustomerEmail(
            DuplicateCustomerEmailException exception) {

        return new ApiError(
                "DUPLICATE_CUSTOMER_EMAIL",
                exception.getMessage(),
                OffsetDateTime.now()
        );
    }
}