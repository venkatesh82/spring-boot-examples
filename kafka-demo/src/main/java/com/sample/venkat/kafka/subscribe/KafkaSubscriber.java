package com.sample.venkat.kafka.subscribe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaSubscriber {
    private final Logger logger = LoggerFactory.getLogger(KafkaSubscriber.class);

    @KafkaListener(topics = "venkat-topic", groupId = "myGroup")
    public void consume(String message) {
        logger.info("Consumed Message -> {}", message);
    }
}
