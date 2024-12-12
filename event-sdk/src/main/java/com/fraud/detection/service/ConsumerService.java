package com.fraud.detection.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConsumerService {

    @KafkaListener(topics = {
            "${spring.kafka.topic.name}" }, containerFactory = "kafkaListenerStringFactory", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeMessage(String message) {
        log.info("**** -> Consumed message -> {}", message);
    }

}
