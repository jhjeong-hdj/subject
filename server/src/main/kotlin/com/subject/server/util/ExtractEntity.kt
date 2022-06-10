package com.subject.server.exception

import com.subject.server.domain.Hospital
import com.subject.server.domain.Patient

fun Hospital?.extract(message: String? = null): Hospital {
    return this ?: throw CustomExceptionType.NOT_FOUND_HOSPITAL.toException(message)
}

fun Patient?.extract(message: String? = null): Patient {
    return this ?: throw CustomExceptionType.NOT_FOUND_PATIENT.toException(message)
}

fun Long?.extract(): Long {
    return this ?: throw CustomExceptionType.JPA_ERROR.toException()
}