package com.subject.server.dto

import com.subject.server.domain.Patient
import com.subject.server.exception.extract

data class GetPatientResponseDto(
    val id: Long,
    val visitList: List<GetVisitResponseDto>,
    val name: String,
    val registrationNumber: String,
    val genderCodeDescription: String?,
    val birthday: String?,
    val phoneNumber: String?
) {
    companion object {
        fun of(patient: Patient): GetPatientResponseDto {
            return GetPatientResponseDto(
                id = patient.id.extract(),
                visitList = patient.visitList.map { GetVisitResponseDto.of(it) }.toList(),
                name = patient.name,
                registrationNumber = patient.registrationNumber,
                genderCodeDescription = patient.genderCode?.description,
                birthday = patient.birthday,
                phoneNumber = patient.phoneNumber
            )
        }
    }
}