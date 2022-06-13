package com.subject.server.service

import com.subject.server.dto.AddPatientRequestDto
import com.subject.server.dto.GetPatientResponseDto
import com.subject.server.dto.UpdatePatientRequestDto
import com.subject.server.repository.dsl.SearchCondition
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface PatientService {
    fun addPatient(requestDto: AddPatientRequestDto)
    fun updatePatient(requestDto: UpdatePatientRequestDto)
    fun deletePatientVisitInfoFromHospital(patientId: Long, hospitalId: Long)
    fun getPatient(patientId: Long): GetPatientResponseDto
    fun getPatients(
        pageable: Pageable,
        condition: SearchCondition?,
        keyword: String?
    ): Page<GetPatientResponseDto>
}