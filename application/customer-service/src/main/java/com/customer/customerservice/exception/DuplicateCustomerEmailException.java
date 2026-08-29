package com.customer.customerservice.exception;

public class DuplicateCustomerEmailException extends RuntimeException {

    public DuplicateCustomerEmailException(String email) {
        super("Customer email already exists: " + email);
    }
}