package com.manoj.aiincident.incident.repository;

import com.manoj.aiincident.incident.model.Incident;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncidentRepository extends JpaRepository<Incident, Long> {
}