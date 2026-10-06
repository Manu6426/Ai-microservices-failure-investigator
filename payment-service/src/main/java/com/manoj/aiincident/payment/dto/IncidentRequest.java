package com.manoj.aiincident.payment.dto;

public class IncidentRequest {

    private String sourceService;
    private String targetService;
    private String errorType;
    private String errorMessage;

    public IncidentRequest() {
    }

    public IncidentRequest(
            String sourceService,
            String targetService,
            String errorType,
            String errorMessage) {

        this.sourceService = sourceService;
        this.targetService = targetService;
        this.errorType = errorType;
        this.errorMessage = errorMessage;
    }

    public String getSourceService() {
        return sourceService;
    }

    public void setSourceService(String sourceService) {
        this.sourceService = sourceService;
    }

    public String getTargetService() {
        return targetService;
    }

    public void setTargetService(String targetService) {
        this.targetService = targetService;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}