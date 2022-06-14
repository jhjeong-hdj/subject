package com.subject.server.dto

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class UpdatePatientRequestDto(
    @field:NotBlank
    var patientId: Long,
    @field:Size(max = 45)
    var name: String? = null,
    var genderCode: Char? = null,
    @field:Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}\$")
    var phoneNumber: String? = null,
)