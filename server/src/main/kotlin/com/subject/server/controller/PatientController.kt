package com.subject.server.controller

import com.subject.server.dto.CreatePatientRequestDto
import com.subject.server.dto.FindPatientListResponseDto
import com.subject.server.dto.FindPatientResponseDto
import com.subject.server.dto.UpdatePatientRequestDto
import com.subject.server.repository.PatientRepository
import com.subject.server.repository.dsl.SearchCondition
import com.subject.server.service.PatientService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.OK
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/patients")
class PatientController(
    private val patientService: PatientService,
    private val patientRepository: PatientRepository
) {

    @PostMapping
    fun savePatient(@Valid @RequestBody requestDto: CreatePatientRequestDto): ResponseEntity<Unit> {
        patientService.addPatient(requestDto)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @PatchMapping
    fun updatePatient(@Valid @RequestBody requestDto: UpdatePatientRequestDto): ResponseEntity<Unit> {
        patientService.updatePatient(requestDto)
        return ResponseEntity.status(OK).build()
    }

    @DeleteMapping("/{patientId}/{hospitalId}")
    fun deletePatientVisitInfoFromHospital(
        @PathVariable patientId: Long,
        @PathVariable hospitalId: Long
    ): ResponseEntity<Void> {
        patientService.deletePatientVisitInfoFromHospital(patientId, hospitalId)
        return ResponseEntity.status(OK).build()
    }

    @GetMapping("/{patientId}")
    fun getPatient(@PathVariable patientId: Long): ResponseEntity<FindPatientResponseDto> {
        val responseDto = patientService.getPatient(patientId)
        return ResponseEntity.status(OK).body(responseDto)
    }

    @GetMapping("/{page}/{limit}")
    fun getPatients(
        @PathVariable page: Int,
        @PathVariable limit: Int,
        @RequestParam(value = "condition") condition: SearchCondition?,
        @RequestParam(value = "keyword") keyword: String?
    ): ResponseEntity<Page<FindPatientListResponseDto>> {
        val pageable = PageRequest.of(page, limit)
        val responseDtoList = patientRepository.findByPageAndLimit(
            condition = condition,
            keyword = keyword,
            pageable = pageable
        )
        return ResponseEntity.status(OK).body(responseDtoList)
    }
}