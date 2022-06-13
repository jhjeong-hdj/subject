package com.subject.server.controller

import com.subject.server.domain.mockVisit
import com.subject.server.dto.AddPatientRequestDto
import com.subject.server.dto.GetPatientResponseDto
import com.subject.server.dto.GetVisitResponseDto
import com.subject.server.dto.UpdatePatientRequestDto
import com.subject.server.repository.dsl.SearchCondition.NAME
import com.subject.server.service.PatientService
import io.mockk.CapturingSlot
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.data.domain.PageRequest
import org.springframework.data.support.PageableExecutionUtils
import org.springframework.http.HttpStatus
import java.util.function.LongSupplier

internal class PatientControllerTest {
    private val patientService: PatientService = mockk()
    private val controller = PatientController(patientService)

    @BeforeEach
    fun init() {
        clearMocks(patientService)
    }

    @Test
    fun parameterTest() {
        assertThat(controller).isNotNull
    }

    @DisplayName("환자 생성 성공 테스트")
    @Test
    fun createPatientApiTestSuccess() {
        // Given
        val requestDto = AddPatientRequestDto(
            hospitalId = 1,
            receptionDate = "2022-06-10 13:00",
            name = "김철수",
            genderCode = 'M',
            phoneNumber = "010-1234-5678",
            birthday = "2000.01.02"
        )
        every { patientService.addPatient(requestDto) } returns Unit

        // When
        val responseEntity = controller.savePatient(requestDto)

        // Then
        assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.CREATED)

        val slot: CapturingSlot<AddPatientRequestDto> = slot()
        verify(exactly = 1) { patientService.addPatient(capture(slot)) }
        val capturedRequestDto = slot.captured
        assertThat(capturedRequestDto.name).isEqualTo("김철수")
        assertThat(capturedRequestDto.receptionDate).isEqualTo("2022-06-10 13:00")
        assertThat(capturedRequestDto.hospitalId).isEqualTo(1L)
        assertThat(capturedRequestDto.genderCode).isEqualTo('M')
        assertThat(capturedRequestDto.birthday).isEqualTo("2000.01.02")
        assertThat(capturedRequestDto.phoneNumber).isEqualTo("010-1234-5678")
    }

    @DisplayName("환자 정보 수정 성공 테스트")
    @Test
    fun updatePatientApiTestSuccess() {
        // Given
        val requestDto = UpdatePatientRequestDto(
            patientId = 1,
            name = "김철수",
            genderCode = 'M',
            phoneNumber = "010-1234-5678"
        )
        every { patientService.updatePatient(requestDto) } returns Unit

        // When
        val responseEntity = controller.updatePatient(requestDto)

        // Then
        assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)

        val slot: CapturingSlot<UpdatePatientRequestDto> = slot()
        verify(exactly = 1) { patientService.updatePatient(capture(slot)) }
        val capturedRequestDto = slot.captured
        assertThat(capturedRequestDto.name).isEqualTo("김철수")
        assertThat(capturedRequestDto.genderCode).isEqualTo('M')
        assertThat(capturedRequestDto.patientId).isEqualTo(1L)
        assertThat(capturedRequestDto.phoneNumber).isEqualTo("010-1234-5678")
    }

    @DisplayName("환자 정보 조회 성공 테스트")
    @Test
    fun getPatientApiTestSuccess() {
        // Given
        val basePatientId = 1L
        val visitResponseDto = GetVisitResponseDto.of(mockVisit())
        every { patientService.getPatient(basePatientId) } returns GetPatientResponseDto(
            id = 1,
            visitList = mutableListOf(visitResponseDto),
            name = "김철수",
            registrationNumber = "202206100001",
            genderCodeDescription = null,
            birthday = null,
            phoneNumber = null
        )

        // When
        val responseEntity = controller.getPatient(basePatientId)

        // Then
        assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
        verify(exactly = 1) { patientService.getPatient(basePatientId) }
    }

    @DisplayName("1번 병원에 방문한 환자 정보 삭제 성공 테스트")
    @Test
    fun deletePatientApiTestSuccess() {
        // Given
        val basePatientId = 1L
        val baseHospitalId = 1L
        every {
            patientService.deletePatientVisitInfoFromHospital(
                basePatientId,
                baseHospitalId
            )
        } returns Unit

        // When
        val responseEntity =
            controller.deletePatientVisitInfoFromHospital(basePatientId, baseHospitalId)

        // Then
        assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
        verify(exactly = 1) {
            patientService.deletePatientVisitInfoFromHospital(
                basePatientId,
                baseHospitalId
            )
        }
    }

    @DisplayName("환자 목록 이름으로 조회 성공 테스트")
    @Test
    fun getPatientsApiByNameTestSuccess() {
        // Given
        val basePatientId = 1L
        val basePageable = PageRequest.of(0, 10)
        val visitResponseDto = GetVisitResponseDto.of(mockVisit())
        every {
            patientService.getPatients(
                basePageable,
                NAME,
                "김철수"
            )
        } returns PageableExecutionUtils.getPage(
            mutableListOf(
                GetPatientResponseDto(
                    id = 1,
                    visitList = mutableListOf(visitResponseDto),
                    name = "김철수",
                    registrationNumber = "202206100001",
                    genderCodeDescription = null,
                    birthday = null,
                    phoneNumber = null
                )
            ),
            basePageable,
            LongSupplier { 100 })

        // When
        val responseEntity = controller.getPatients(0, 10, NAME, "김철수")

        // Then
        assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
        verify(exactly = 1) { patientService.getPatients(basePageable, NAME, "김철수") }
    }
}