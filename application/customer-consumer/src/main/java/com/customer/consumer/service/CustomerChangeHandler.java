package com.customer.consumer.service;

import com.customer.consumer.model.CustomerChangeEvent;
import org.springframework.stereotype.Service;

@Service
public class CustomerChangeHandler {

    public String handle(CustomerChangeEvent event) {
        return switch (event.operation()) {
            case "r" -> handleSnapshot(event);
            case "c" -> handleCreate(event);
            case "u" -> handleUpdate(event);
            case "d" -> handleDelete(event);
            default -> throw new IllegalArgumentException(
                    "Unknown CDC operation: " + event.operation()
            );
        };
    }

    private String handleSnapshot(CustomerChangeEvent event) {
        return "Customer SNAPSHOT: " + event.after();
    }

    private String handleCreate(CustomerChangeEvent event) {
        return "Customer CREATED: " + event.after();
    }

    private String handleUpdate(CustomerChangeEvent event) {
        return "Customer UPDATED: before="
                + event.before()
                + ", after="
                + event.after();
    }

    private String handleDelete(CustomerChangeEvent event) {
        return "Customer DELETED: " + event.before();
    }
}