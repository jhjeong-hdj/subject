package com.subject.server.service

import com.subject.server.domain.Patient
import com.subject.server.domain.Visit
import com.subject.server.domain.status.GenderCode
import com.subject.server.domain.status.GenderCode.Companion
import com.subject.server.dto.CreatePatientRequestDto
import com.subject.server.dto.FindPatientResponseDto
import com.subject.server.dto.UpdatePatientRequestDto
import com.subject.server.repository.HospitalRepository
import com.subject.server.repository.PatientRepository
import com.subject.server.util.extract
import com.subject.server.util.toLocalDateTime
import com.subject.server.util.toString
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
@Transactional
class PatientServiceImpl(
    private val patientRepository: PatientRepository,
    private val hospitalRepository: HospitalRepository
) : PatientService {
    private var num = 0
    private var date = ""
    override fun addPatient(requestDto: CreatePatientRequestDto) {
        val findHospital = hospitalRepository.findByIdOrNull(requestDto.hospitalId).extract()
        val patient = Patient(
            name = requestDto.name,
            registrationNumber = receptionDateGenerator(),
            phoneNumber = requestDto.phoneNumber,
            birthday = requestDto.birthday,
            genderCode = GenderCode.findGenderByCode(requestDto.genderCode)
        )
        patient.addVisit(
            Visit(
                hospitalUid = findHospital.id,
                receptionDate = requestDto.receptionDate.toLocalDateTime()
            )
        )

        patientRepository.save(patient)
    }

    override fun updatePatient(requestDto: UpdatePatientRequestDto) {
        val findPatient = patientRepository.findByIdOrNull(requestDto.patientId).extract()
        findPatient.changePatientInfo(
            name = requestDto.name,
            genderCode = requestDto.genderCode?.let { Companion.findGenderByCode(it) },
            phoneNumber = requestDto.phoneNumber
        )
    }

    override fun deletePatientVisitInfoFromHospital(patientId: Long, hospitalId: Long) {
        val findPatient = patientRepository.findWithVisitById(patientId).extract()
        findPatient.visitList
            .filter { it.hospitalUid == hospitalId }
            .map { it.deleteVisitInfo() }
    }

    override fun getPatient(patientId: Long): FindPatientResponseDto {
        val findPatient = patientRepository.findWithVisitById(patientId).extract()
        return FindPatientResponseDto.of(findPatient)
    }

    private fun receptionDateGenerator(): String {
        val date = LocalDateTime.now().toString("yyyyMMdd")
        if (date != this.date) {
            this.date = date
            num = 0
        }
        num += 1
        val nextNumString = num.toString()
        var result: String = date
        for (i in nextNumString.length..4) {
            result += '0'
        }
        return result + nextNumString
    }
}