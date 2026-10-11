package com.manoj.aiincident.payment.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class IncidentEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public IncidentEventProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendIncidentEvent(String message) {
        kafkaTemplate.send("incident-events", message);
    }
}