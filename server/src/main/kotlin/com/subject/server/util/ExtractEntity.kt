package com.subject.server.util

import com.subject.server.domain.Hospital
import com.subject.server.domain.Patient
import com.subject.server.exception.CustomExceptionType.JPA_ERROR
import com.subject.server.exception.CustomExceptionType.NOT_FOUND_HOSPITAL
import com.subject.server.exception.CustomExceptionType.NOT_FOUND_PATIENT

fun Hospital?.extract(message: String? = null): Hospital {
    return this ?: throw NOT_FOUND_HOSPITAL.toException(message)
}

fun Patient?.extract(message: String? = null): Patient {
    return this ?: throw NOT_FOUND_PATIENT.toException(message)
}

fun Long?.extract(): Long {
    return this ?: throw JPA_ERROR.toException()
}