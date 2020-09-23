package com.sample.venkat.kafka.publish;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaPublisher {

    private static final Logger logger = LoggerFactory.getLogger(KafkaPublisher.class);

    private static final String TOPIC = "venkat-topic";

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaPublisher(final KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishMessage(String message) {
        logger.info("Publishing message -> {}", message);
        this.kafkaTemplate.send(TOPIC, message);
    }


}
