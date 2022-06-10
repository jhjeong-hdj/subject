package com.subject.server.service

import com.subject.server.dto.AddPatientRequestDto
import com.subject.server.dto.GetPatientResponseDto
import com.subject.server.dto.SearchCondition
import com.subject.server.dto.UpdatePatientRequestDto

interface PatientService {
    fun addPatient(requestDto: AddPatientRequestDto)
    fun updatePatient(requestDto: UpdatePatientRequestDto)
    fun deletePatient(id: Long)
    fun getPatient(id: Long): GetPatientResponseDto
    fun getPatients(
        page: Long,
        limit: Long,
        condition: SearchCondition?,
        keyword: String?
    ): List<GetPatientResponseDto>
}