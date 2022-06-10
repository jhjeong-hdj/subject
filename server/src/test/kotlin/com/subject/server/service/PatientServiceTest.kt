package com.subject.server.service

import com.subject.server.domain.Patient
import com.subject.server.domain.mockHospital
import com.subject.server.domain.mockPatient
import com.subject.server.domain.mockVisit
import com.subject.server.domain.status.GenderCode
import com.subject.server.dto.AddPatientRequestDto
import com.subject.server.dto.UpdatePatientRequestDto
import com.subject.server.exception.CustomException
import com.subject.server.repository.HospitalRepository
import com.subject.server.repository.PatientRepository
import io.mockk.CapturingSlot
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.data.repository.findByIdOrNull

internal class PatientServiceTest {
    // kotlin style 테스트 도구라고 해서 사용해봤습니다
    private val patientRepository: PatientRepository = mockk()
    private val hospitalRepository: HospitalRepository = mockk()

    private val patientService = PatientServiceImpl(patientRepository, hospitalRepository)

    @DisplayName("환자 추가 실패 병원이 조회되지 않음")
    @Test
    fun addPatientFailCantAccessHospital() {
        // Given
        val baseHospitalId = 1L
        every { hospitalRepository.findByIdOrNull(baseHospitalId) } returns null

        // When & then
        assertThrows<CustomException> {
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
        }
    }

    @DisplayName("환자 추가 성공")
    @Test
    fun addPatientSuccess() {
        // Given
        val baseHospitalId = 1L
        every { hospitalRepository.findByIdOrNull(baseHospitalId) } returns mockHospital()
        every { patientRepository.save(any()) } returns mockPatient()

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
        val slot: CapturingSlot<Patient> = slot()
        verify(exactly = 1) { hospitalRepository.findByIdOrNull(baseHospitalId) }
        verify(exactly = 1) { patientRepository.save(capture(slot)) }
        val savedPatient = slot.captured

        assertThat(savedPatient.name).isEqualTo("김환자")
        assertThat(savedPatient.genderCode).isEqualTo(GenderCode.CODE_M)
        assertThat(savedPatient.phoneNumber).isEqualTo("010-1234-5678")
        assertThat(savedPatient.birthday).isEqualTo("2000.01.01")
        assertThat(savedPatient.visitList[0].receptionDate).isEqualTo("2022-06-01T13:00")
        assertThat(savedPatient.visitList[0].hospital.id).isEqualTo(baseHospitalId)
    }

    @DisplayName("환자 정보 수정 실패")
    @Test
    fun updatePatientFail() {
        // Given
        val basePatientId = 1L
        every { patientRepository.findByIdOrNull(basePatientId) } returns null

        // When & then
        assertThrows<CustomException> {
            patientService.updatePatient(
                UpdatePatientRequestDto(
                    basePatientId,
                    name = "수정된테스트이름",
                    null,
                    null
                )
            )
        }
    }

    // todo : 수정된 객체를 추적해서 변동 내역 확인하는 테스트 코드 작성법 알아내기
    @DisplayName("환자 이름 수정 성공")
    @Test
    fun updatePatientNameSuccess() {
        // Given
        val basePatientId = 1L
        every { patientRepository.findByIdOrNull(basePatientId) } returns mockPatient()

        // When
        patientService.updatePatient(
            UpdatePatientRequestDto(
                basePatientId,
                name = "수정된테스트이름",
                null,
                null
            )
        )

        // Then
        verify(exactly = 1) { patientRepository.findByIdOrNull(basePatientId) }
    }

    @DisplayName("하나의 환자 정보 조회 실패 : 환자가 없음")
    @Test
    fun getPatientFailPatientNotExist() {
        // Given
        val basePatientId = 1L
        every { patientRepository.findByIdOrNull(basePatientId) } returns null

        // When & then
        assertThrows<CustomException> {
            patientService.getPatient(basePatientId)
        }
    }

    //일어날 수가 없는 상황이라고 생각합니다
    @DisplayName("하나의 환자 정보 조회 실패 : 병원이 없음")
    @Test
    fun getPatientFailHospitalNotExist() {
        // Given
        val basePatientId = 1L
        val mockPatient = mockPatient()
        mockPatient.addVisit(mockVisit(hospitalId = null))
        every { patientRepository.findByIdOrNull(basePatientId) } returns mockPatient

        // When & then
        assertThrows<CustomException> {
            patientService.getPatient(basePatientId)
        }
    }

    @DisplayName("하나의 환자 정보 조회 성공")
    @Test
    fun getPatientSuccess() {
        // Given
        val basePatientId = 1L
        val mockPatient = mockPatient()
        mockPatient.addVisit(mockVisit())
        every { patientRepository.findByIdOrNull(basePatientId) } returns mockPatient

        // When
        val getPatientResponseDto = patientService.getPatient(basePatientId)

        // Then
        assertThat(getPatientResponseDto.name).isNotEmpty
        assertThat(getPatientResponseDto.birthday).isNotEmpty
        assertThat(getPatientResponseDto.phoneNumber).isNotEmpty
        assertThat(getPatientResponseDto.genderCodeDescription).isNotEmpty
        assertThat(getPatientResponseDto.registrationNumber).isNotEmpty
        assertThat(getPatientResponseDto.visitList.size).isEqualTo(1)
        assertThat(getPatientResponseDto.visitList[0].receptionDate).isNotNull
    }
}