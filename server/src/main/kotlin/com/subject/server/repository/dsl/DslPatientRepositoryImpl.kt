package com.subject.server.repository.dsl

import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import com.subject.server.domain.Patient
import com.subject.server.domain.QPatient.patient
import com.subject.server.domain.QVisit.visit
import com.subject.server.dto.FindPatientListResponseDto
import com.subject.server.repository.DslPatientRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.support.PageableExecutionUtils
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional(readOnly = true)
class DslPatientRepositoryImpl(private val query: JPAQueryFactory) : DslPatientRepository {
    override fun findWithVisitById(patientId: Long): Patient? {
        return query
            .select(patient)
            .from(patient)
            .leftJoin(patient.visitList, visit)
            .where(patient.id.eq(patientId))
            .fetchOne()
    }

    override fun findByPageAndLimit(
        pageable: Pageable,
        condition: SearchCondition?,
        keyword: String?
    ): Page<FindPatientListResponseDto> {
        val results = query
            .select(
                Projections.constructor(
                    FindPatientListResponseDto::class.java,
                    patient.id,
                    patient.name,
                    patient.registrationNumber,
                    patient.genderCode,
                    patient.birthday,
                    patient.phoneNumber
                )
            )
            .from(patient)
            .where(
                condition?.getPatientBooleanExpressionByKeyword(keyword)
            )
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val totalCount = query
            .select(patient)
            .from(patient)
            .leftJoin(patient.visitList, visit)
            .where(
                condition?.getPatientBooleanExpressionByKeyword(keyword)
            )
            .fetch().count()
        return PageableExecutionUtils.getPage(results, pageable) { totalCount.toLong() }
    }
}

enum class SearchCondition(
    private val expression: (String?) -> BooleanExpression?
) {
    NAME({ keyword -> patient.name.contains(keyword) }),
    BIRTHDAY({ keyword -> patient.birthday.contains(keyword) }),
    REGISTRATION({ keyword -> patient.registrationNumber.contains(keyword) });

    fun getPatientBooleanExpressionByKeyword(keyword: String?): BooleanExpression? {
        return this.expression(keyword)
    }
}