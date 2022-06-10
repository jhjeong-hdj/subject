package com.subject.server.repository;

import com.subject.server.domain.Patient
import com.subject.server.dto.SearchCondition
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PatientRepository : JpaRepository<Patient, Long>, DslPatientRepository

interface DslPatientRepository {
    fun findByPageAndLimit(
        page: Long,
        limit: Long,
        condition: SearchCondition?
    ): List<Patient>
}