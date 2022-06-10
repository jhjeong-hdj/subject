package com.subject.server.repository;

import com.subject.server.domain.Patient
import com.subject.server.repository.dsl.SearchCondition
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import javax.persistence.LockModeType.PESSIMISTIC_WRITE

interface PatientRepository : JpaRepository<Patient, Long>, DslPatientRepository

interface DslPatientRepository {

    fun findByPageAndLimit(
        page: Long,
        limit: Long,
        condition: SearchCondition?,
        keyword: String?
    ): List<Patient>
}