package com.subject.server.repository.dsl

import com.querydsl.core.types.dsl.BooleanExpression
import com.subject.server.domain.QPatient.patient

enum class SearchCondition {
    NAME, BIRTHDAY, REGISTRATION;

    // 리팩토링이 필요해보입니다
    fun getPatientBooleanExpressionByKeyword(keyword: String): BooleanExpression? {
        if (this == NAME)
            return patient.name.eq(keyword)
        if (this == BIRTHDAY)
            return patient.birthday.eq(keyword)
        if (this == REGISTRATION)
            return patient.registrationNumber.eq(keyword)

        return null
    }
}