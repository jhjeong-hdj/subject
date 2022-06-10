package com.subject.server.dto

import com.subject.server.util.StringDateTimeFormat
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class AddPatientRequestDto(
    var hospitalId: Long,
    @field:StringDateTimeFormat(pattern = "yyyy-MM-dd HH:mm", message = "패턴이 올바르지 않습니다.")
    var receptionDate: String,
    @field:Size(max = 45)
    var name: String,
    var genderCode: Char?,
    @field:Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}\$")
    var phoneNumber: String?,
    @field:Pattern(regexp = "^\\d{4}.\\d{2}.\\d{2}\$")
    var birthday: String?
)