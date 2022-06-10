package com.subject.server.dto

data class SearchCondition(
    val patientName: String? = null,
    val birthday: String? = null,
    val registrationNumber: String? = null
)