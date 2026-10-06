package com.manoj.aiincident.payment.client;

import com.manoj.aiincident.payment.dto.IncidentRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class IncidentClient {

    private final RestClient restClient;

    public IncidentClient() {
        this.restClient = RestClient.builder()
                .baseUrl("http://localhost:8082")
                .build();
    }

    public void createIncident(
            String sourceService,
            String targetService,
            String errorType,
            String errorMessage) {

        IncidentRequest request = new IncidentRequest(
                sourceService,
                targetService,
                errorType,
                errorMessage
        );

        restClient.post()
                .uri("/api/incidents")
                .body(request)
                .retrieve()
                .toBodilessEntity();
    }
}