package com.subject.server.dto

import com.subject.server.domain.status.GenderCode

data class FindPatientListResponseDto(
    val id: Long,
    val name: String,
    val registrationNumber: String,
    val genderCodeDescription: String?,
    val birthday: String?,
    val phoneNumber: String?
) {
    constructor(id : Long,
                name : String,
                registrationNumber: String,
                genderCode: GenderCode?,
                birthday: String?,
                phoneNumber: String?) :
        this(id, name, registrationNumber, genderCode?.description, birthday, phoneNumber)
}