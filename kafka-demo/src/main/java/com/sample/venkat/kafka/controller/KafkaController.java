package com.sample.venkat.kafka.controller;

import com.sample.venkat.kafka.publish.KafkaPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

    private final KafkaPublisher publisher;

    public KafkaController(final KafkaPublisher publisher) {
        this.publisher = publisher;
    }

    @GetMapping("/publish")
    public ResponseEntity<String> publishMessage(@RequestParam(value = "message") String msg) {
        publisher.publishMessage(msg);
        return new ResponseEntity<>("Published Successfully", HttpStatus.OK);
    }
}
