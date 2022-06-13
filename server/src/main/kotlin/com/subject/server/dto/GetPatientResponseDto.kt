package com.subject.server.dto

import com.subject.server.domain.Patient
import com.subject.server.domain.status.VisitHistoryStatus.EXIST
import com.subject.server.util.extract

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
                visitList = patient.visitList
                    .filter { it.status == EXIST }
                    .map { GetVisitResponseDto.of(it) }
                    .toList(),
                name = patient.name,
                registrationNumber = patient.registrationNumber,
                genderCodeDescription = patient.genderCode?.description,
                birthday = patient.birthday,
                phoneNumber = patient.phoneNumber
            )
        }
    }
}