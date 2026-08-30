
package com.customer.consumer.consumer;

import com.customer.consumer.model.CustomerChangeEvent;
import com.customer.consumer.service.CustomerChangeHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomerChangeConsumer {

    private final ObjectMapper objectMapper;
    private final CustomerChangeHandler changeHandler;

    public CustomerChangeConsumer(
            ObjectMapper objectMapper,
            CustomerChangeHandler changeHandler
    ) {
        this.objectMapper = objectMapper;
        this.changeHandler = changeHandler;
    }

    @KafkaListener(
            topics = "customerdb.public.customer",
            groupId = "customer-consumer-group"
    )
    public void consume(ConsumerRecord<String, String> record) {

        String key = record.key();
        String value = record.value();

        if (value == null) {
            log.info("Received tombstone for customer key: {}", key);
            return;
        }

        try {
            JsonNode root = objectMapper.readTree(value);

            JsonNode payload = root.has("payload")
                    ? root.get("payload")
                    : root;

            CustomerChangeEvent event =
                    objectMapper.treeToValue(
                            payload,
                            CustomerChangeEvent.class
                    );

            String result = changeHandler.handle(event);

            log.info("{}", result);

        } catch (Exception exception) {
            log.error(
                    "Failed to process CDC event at offset {}",
                    record.offset(),
                    exception
            );
        }
    }
}
