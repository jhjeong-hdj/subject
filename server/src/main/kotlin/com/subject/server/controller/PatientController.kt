package com.subject.server.controller

import com.subject.server.dto.AddPatientRequestDto
import com.subject.server.dto.GetPatientResponseDto
import com.subject.server.repository.dsl.SearchCondition
import com.subject.server.dto.UpdatePatientRequestDto
import com.subject.server.service.PatientService
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
class PatientController(private val patientService: PatientService) {

    @PostMapping
    fun savePatient(@Valid @RequestBody requestDto: AddPatientRequestDto): ResponseEntity<Void> {
        patientService.addPatient(requestDto)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @PatchMapping
    fun updatePatient(@Valid @RequestBody requestDto: UpdatePatientRequestDto): ResponseEntity<Void> {
        patientService.updatePatient(requestDto)
        return ResponseEntity.status(OK).build()
    }

    @DeleteMapping("/{patientId}")
    fun deletePatient(@PathVariable patientId: Long): ResponseEntity<Void> {
        patientService.deletePatient(patientId)
        return ResponseEntity.status(OK).build()
    }

    @GetMapping("/{patientId}")
    fun getPatient(@PathVariable patientId: Long): ResponseEntity<GetPatientResponseDto> {
        val responseDto = patientService.getPatient(patientId)
        return ResponseEntity.status(OK).body(responseDto)
    }

    @GetMapping("/{page}/{limit}")
    fun getPatients(
        @PathVariable page: Long,
        @PathVariable limit: Long,
        @RequestParam(value = "condition") condition: SearchCondition?,
        @RequestParam(value = "keyword") keyword: String?
    ): ResponseEntity<List<GetPatientResponseDto>> {
        val responseDtoList = patientService.getPatients(page, limit, condition, keyword)
        return ResponseEntity.status(OK).body(responseDtoList)
    }
}