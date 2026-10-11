
package com.manoj.aiincident.incident.kafka;

import com.manoj.aiincident.incident.model.Incident;
import com.manoj.aiincident.incident.service.IncidentService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Component
public class IncidentEventConsumer {

    private final IncidentService incidentService;
    private final ObjectMapper objectMapper;

    public IncidentEventConsumer(
            IncidentService incidentService,
            ObjectMapper objectMapper) {

        this.incidentService = incidentService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "incident-events")
    public void consume(String message) {

        System.out.println("Kafka event received: " + message);

        try {
            JsonNode event = objectMapper.readTree(message);

            Incident incident = new Incident();

            incident.setSourceService(
                    event.path("sourceService").asText());

            incident.setTargetService(
                    event.path("targetService").asText());

            incident.setErrorType(
                    event.path("errorType").asText());

            incident.setErrorMessage(
                    event.path("errorMessage").asText());

            Incident savedIncident =
                    incidentService.createIncident(incident);

            System.out.println(
                    "Incident saved with ID: " + savedIncident.getId());

        } catch (Exception e) {
            throw new IllegalStateException(
                    "Failed to process Kafka incident event", e);
        }
    }
}
