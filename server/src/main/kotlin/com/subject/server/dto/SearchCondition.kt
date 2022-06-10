package com.subject.server.dto

import com.querydsl.core.types.dsl.BooleanExpression
import com.subject.server.domain.QPatient.patient

enum class SearchCondition {
    NAME, BIRTHDAY, REGISTRATION;

    fun getPatientBooleanExpressionByKeyword(keyword: String): BooleanExpression? {
        if (this == SearchCondition.NAME)
            return patient.name.eq(keyword)
        if (this == SearchCondition.BIRTHDAY)
            return patient.birthday.eq(keyword)
        if (this == SearchCondition.REGISTRATION)
            return patient.registrationNumber.eq(keyword)

        return null
    }
}