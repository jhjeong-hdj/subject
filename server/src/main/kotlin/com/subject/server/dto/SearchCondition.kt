package com.subject.server.dto

import java.util.StringJoiner

data class SearchCondition(
    val patientName : String? = null,
    val birthday : String? = null,
    val registrationNumber : String? = null
)