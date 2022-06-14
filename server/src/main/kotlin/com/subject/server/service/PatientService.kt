package com.subject.server.service

import com.subject.server.dto.CreatePatientRequestDto
import com.subject.server.dto.FindPatientResponseDto
import com.subject.server.dto.UpdatePatientRequestDto

interface PatientService {
    fun addPatient(requestDto: CreatePatientRequestDto)
    fun updatePatient(requestDto: UpdatePatientRequestDto)
    fun deletePatientVisitInfoFromHospital(patientId: Long, hospitalId: Long)
    fun getPatient(patientId: Long): FindPatientResponseDto
}