package com.subject.server.repository;

import com.subject.server.domain.Patient
import com.subject.server.repository.dsl.SearchCondition
import org.springframework.data.jpa.repository.JpaRepository

interface PatientRepository : JpaRepository<Patient, Long>, DslPatientRepository

interface DslPatientRepository {

    fun findWithVisitById(patientId : Long) : Patient?
    fun findByPageAndLimit(
        page: Long,
        limit: Long,
        condition: SearchCondition?,
        keyword: String?
    ): List<Patient>
}