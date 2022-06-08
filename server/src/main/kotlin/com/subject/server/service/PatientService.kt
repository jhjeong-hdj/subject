package com.subject.server.service

import com.subject.server.dto.AddPatientRequestDto
import com.subject.server.dto.GetPatientResponseDto
import com.subject.server.dto.UpdatePatientRequestDto

interface PatientService {
    fun addPatient(requestDto: AddPatientRequestDto)
    fun updatePatient(requestDto: UpdatePatientRequestDto)
    fun deletePatient(id: Long)
    fun getPatient(id: Long): GetPatientResponseDto
    fun getPatients(page: Int, offset: Int): List<GetPatientResponseDto>
}