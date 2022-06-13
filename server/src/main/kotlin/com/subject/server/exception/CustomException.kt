package com.subject.server.exception

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

data class CustomException(
    val httpStatus: HttpStatus,
    override val message: String
) : RuntimeException()

enum class CustomExceptionType(
    private val httpStatus: HttpStatus,
    private val defaultMessage: String
) {
    NOT_FOUND_HOSPITAL(HttpStatus.NOT_FOUND, "없는 병원 입니다."),
    NOT_FOUND_PATIENT(HttpStatus.NOT_FOUND, "없는 환자 입니다."),
    NOT_FOUND_GENDER(HttpStatus.NOT_FOUND, "없는 성별 코드 입니다."),

    JPA_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "JPA Service Error");

    fun toException(message: String? = null): CustomException {
        return CustomException(httpStatus, message ?: defaultMessage)
    }
}

data class ErrorResponse(
    @field:JsonProperty("result_code")
    var resultCode: String? = null,
    @field:JsonProperty("http_status")
    var httpStatus: String? = null,
    var message: String? = null,
    var path: String? = null,
    var timestamp: LocalDateTime? = null,
    var errors: MutableList<Error>? = mutableListOf()
)

data class Error(
    var field: String? = null,
    var message: String? = null,
    var value: Any? = null
)
