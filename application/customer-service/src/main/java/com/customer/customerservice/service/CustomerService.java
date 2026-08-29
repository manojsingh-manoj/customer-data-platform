package com.customer.customerservice.service;

import com.customer.customerservice.domain.Customer;
import com.customer.customerservice.dto.CreateCustomerRequest;
import com.customer.customerservice.dto.CustomerResponse;
import com.customer.customerservice.dto.UpdateCustomerRequest;
import com.customer.customerservice.exception.CustomerNotFoundException;
import com.customer.customerservice.exception.DuplicateCustomerEmailException;
import com.customer.customerservice.repository.CustomerRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerResponse create(CreateCustomerRequest request) {
        OffsetDateTime now = OffsetDateTime.now();

        Customer customer = new Customer(
                request.firstName(),
                request.lastName(),
                request.email(),
                now,
                now
        );

        try {
            Customer savedCustomer = customerRepository.save(customer);
            return toResponse(savedCustomer);
        } catch (DataIntegrityViolationException exception) {
            throw new DuplicateCustomerEmailException(request.email());
        }
    }

    public CustomerResponse getById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));

        return toResponse(customer);
    }

    public CustomerResponse update(Long id, UpdateCustomerRequest request) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));

        customer.setFirstName(request.firstName());
        customer.setLastName(request.lastName());
        customer.setEmail(request.email());
        customer.setUpdatedAt(OffsetDateTime.now());

        Customer updatedCustomer = customerRepository.save(customer);

        return toResponse(updatedCustomer);
    }

    public void delete(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));

        customerRepository.delete(customer);
    }

    private CustomerResponse toResponse(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getCreatedAt(),
                customer.getUpdatedAt()
        );
    }
}