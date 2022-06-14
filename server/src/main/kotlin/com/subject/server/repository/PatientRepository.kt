package com.subject.server.repository;

import com.subject.server.domain.Patient
import com.subject.server.dto.FindPatientListResponseDto
import com.subject.server.repository.dsl.SearchCondition
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface PatientRepository : JpaRepository<Patient, Long>, DslPatientRepository

interface DslPatientRepository {

    fun findWithVisitById(patientId: Long): Patient?
    fun findByPageAndLimit(
        pageable: Pageable,
        condition: SearchCondition?,
        keyword: String?
    ): Page<FindPatientListResponseDto>
}