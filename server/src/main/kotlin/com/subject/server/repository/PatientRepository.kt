package com.subject.server.repository;

import com.subject.server.domain.Patient
import org.springframework.data.jpa.repository.JpaRepository

interface PatientRepository : JpaRepository<Patient, Long> {
}