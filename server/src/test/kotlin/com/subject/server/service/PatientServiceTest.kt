package com.subject.server.service

import com.subject.server.domain.mockHospital
import com.subject.server.domain.mockPatient
import com.subject.server.dto.AddPatientRequestDto
import com.subject.server.repository.HospitalRepository
import com.subject.server.repository.PatientRepository
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.Optional

internal class PatientServiceTest {
    private val patientRepository: PatientRepository = mock()
    private val hospitalRepository: HospitalRepository = mock()

    private val patientService = PatientServiceImpl(patientRepository, hospitalRepository)

    @DisplayName("환자 추가 성공")
    @Test
    fun addPatientSuccess() {
        // Given
        val baseHospitalId = 1L
        whenever(hospitalRepository.findById(any())).thenReturn(Optional.of(mockHospital()))

        // When
        patientService.addPatient(
            AddPatientRequestDto(
                hospitalId = baseHospitalId,
                receptionDate = "2022-06-01 13:00",
                genderCode = 'M',
                name = "김환자",
                phoneNumber = "010-1234-5678",
                birthday = "2000.01.01"
            )
        )

        // Then
        verify(patientRepository, times(1)).save(any())
        verify(hospitalRepository, times(1)).findById(any())
    }
}