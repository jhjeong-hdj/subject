package com.subject.server.repository.dsl

import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import com.subject.server.domain.Patient
import com.subject.server.domain.QPatient
import com.subject.server.domain.QPatient.*
import com.subject.server.dto.GetPatientResponseDto
import com.subject.server.dto.SearchCondition
import com.subject.server.repository.DslPatientRepository
import org.springframework.util.StringUtils
import org.springframework.util.StringUtils.*

class DslPatientRepositoryImpl(private val query: JPAQueryFactory) : DslPatientRepository {
    override fun findByPageAndLimit(
        page: Long,
        limit: Long,
        condition: SearchCondition
    ): List<Patient> {
        return query
            .select(patient)
            .from(patient)
            .where(
                patientNameEq(condition.patientName),
                registrationNumberEq(condition.registrationNumber),
                birthdayEq(condition.birthday)
            )
            .offset(page)
            .limit(limit)
            .fetch()
    }

    private fun patientNameEq(patientName: String?) : BooleanExpression? {
        return patientName?.let { patient.name.eq(it) }
    }

    private fun registrationNumberEq(registrationNumber: String?) : BooleanExpression? {
        return registrationNumber?.let { patient.name.eq(it) }
    }

    private fun birthdayEq(birthday: String?) : BooleanExpression? {
        return birthday?.let { patient.name.eq(it) }
    }
}