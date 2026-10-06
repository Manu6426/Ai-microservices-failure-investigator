package com.manoj.aiincident.payment.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Component
public class InventoryClient {

    private static final Logger log =
            LoggerFactory.getLogger(InventoryClient.class);

    private final RestClient restClient;
    private final IncidentClient incidentClient;

    public InventoryClient(IncidentClient incidentClient) {

        this.incidentClient = incidentClient;

        this.restClient = RestClient.builder()
                .baseUrl("http://localhost:8081")
                .build();
    }

    public void reserveStock(String productCode, Integer quantity) {

        try {

            restClient.put()
                    .uri(uriBuilder -> uriBuilder
                            .path("/api/inventory/{productCode}/reserve")
                            .queryParam("quantity", quantity)
                            .build(productCode))
                    .retrieve()
                    .toBodilessEntity();

        } catch (RestClientException e) {

            log.error(
                    "DEPENDENCY_FAILURE service=inventory-service operation=reserveStock productCode={} quantity={} error={}",
                    productCode,
                    quantity,
                    e.getMessage()
            );

            try {
                incidentClient.createIncident(
                        "payment-service",
                        "inventory-service",
                        "DEPENDENCY_FAILURE",
                        "Inventory service unavailable"
                );
            } catch (Exception incidentException) {

                log.error(
                        "Failed to record incident: {}",
                        incidentException.getMessage()
                );
            }

            throw new RuntimeException(
                    "Inventory service unavailable",
                    e
            );
        }
    }
}