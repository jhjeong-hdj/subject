package com.subject.server.repository.dsl

import com.querydsl.jpa.impl.JPAQueryFactory
import com.subject.server.domain.Patient
import com.subject.server.domain.QPatient.patient
import com.subject.server.domain.status.VisitHistoryStatus.EXIST
import com.subject.server.repository.DslPatientRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional(readOnly = true)
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
            .join(patient.visitList).fetchJoin()
            .where(
                condition?.getPatientBooleanExpressionByKeyword(keyword)
            )
            .offset(page)
            .limit(limit)
            .fetch()
    }
}