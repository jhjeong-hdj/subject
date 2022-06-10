package com.subject.server.repository.dsl

import com.querydsl.jpa.impl.JPAQueryFactory
import com.subject.server.domain.Patient
import com.subject.server.domain.QPatient.patient
import com.subject.server.dto.SearchCondition
import com.subject.server.repository.DslPatientRepository

class DslPatientRepositoryImpl(private val query: JPAQueryFactory) : DslPatientRepository {
    override fun findByPageAndLimit(
        page: Long,
        limit: Long,
        condition: SearchCondition?,
        keyword: String?
    ): List<Patient> {
        return query
            .select(patient)
            .from(patient)
            .where(condition?.getPatientBooleanExpressionByKeyword(keyword!!))
            .offset(page)
            .limit(limit)
            .fetch()
    }
}