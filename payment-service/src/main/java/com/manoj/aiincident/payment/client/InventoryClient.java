package com.manoj.aiincident.payment.client;

import com.manoj.aiincident.payment.kafka.IncidentEventProducer;
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
    private final IncidentEventProducer incidentEventProducer;

    public InventoryClient(IncidentEventProducer incidentEventProducer) {

        this.incidentEventProducer = incidentEventProducer;

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

            String incidentEvent = """
                    {
                      "sourceService": "payment-service",
                      "targetService": "inventory-service",
                      "errorType": "DEPENDENCY_FAILURE",
                      "errorMessage": "Inventory service unavailable"
                    }
                    """;

            incidentEventProducer.sendIncidentEvent(incidentEvent);

            throw new RuntimeException(
                    "Inventory service unavailable",
                    e
            );
        }
    }
}