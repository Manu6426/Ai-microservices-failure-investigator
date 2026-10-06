package com.manoj.aiincident.incident.service;

import com.manoj.aiincident.incident.model.Incident;
import com.manoj.aiincident.incident.repository.IncidentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class IncidentService {

    private final IncidentRepository incidentRepository;

    public IncidentService(IncidentRepository incidentRepository) {
        this.incidentRepository = incidentRepository;
    }

    public Incident createIncident(Incident incident) {
        incident.setStatus("OPEN");
        incident.setCreatedAt(LocalDateTime.now());

        return incidentRepository.save(incident);
    }

    public List<Incident> getAllIncidents() {



        return incidentRepository.findAll();
    }
}