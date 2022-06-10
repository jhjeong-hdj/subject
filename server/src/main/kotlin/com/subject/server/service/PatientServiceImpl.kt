package com.subject.server.service

import com.subject.server.domain.Patient
import com.subject.server.domain.Visit
import com.subject.server.domain.status.GenderCode
import com.subject.server.domain.status.GenderCode.Companion
import com.subject.server.dto.AddPatientRequestDto
import com.subject.server.dto.GetPatientResponseDto
import com.subject.server.dto.UpdatePatientRequestDto
import com.subject.server.exception.extract
import com.subject.server.repository.HospitalRepository
import com.subject.server.repository.PatientRepository
import com.subject.server.util.toLocalDateTime
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class PatientServiceImpl(
    private val patientRepository: PatientRepository,
    private val hospitalRepository: HospitalRepository
) : PatientService {
    //todo : 접수번호 비관적 락 걸고 실패 시에 재시도 하게끔 aop 구성
    override fun addPatient(requestDto: AddPatientRequestDto) {
        val findHospital = hospitalRepository.findByIdOrNull(requestDto.hospitalId).extract()
        val patient = Patient(
            name = requestDto.name,
            registrationNumber = "10",
            phoneNumber = requestDto.phoneNumber,
            birthday = requestDto.birthday,
            genderCode = GenderCode.findGenderByCode(requestDto.genderCode)
        )
        patient.addVisit(
            Visit(
                hospital = findHospital,
                receptionDate = toLocalDateTime(requestDto.receptionDate)
            )
        )
        patientRepository.save(patient)
    }

    override fun updatePatient(requestDto: UpdatePatientRequestDto) {
        val findPatient = patientRepository.findByIdOrNull(requestDto.patientId).extract()
        findPatient.changePatientInfo(
            name = requestDto.name,
            genderCode = requestDto.genderCode ?. let { Companion.findGenderByCode(it) },
            phoneNumber = requestDto.phoneNumber
        )
    }

    override fun deletePatient(id: Long) {

    }

    override fun getPatient(id: Long): GetPatientResponseDto {
        val findPatient = patientRepository.findByIdOrNull(id).extract()
        return GetPatientResponseDto.of(findPatient)
    }

    override fun getPatients(page: Int, offset: Int): List<GetPatientResponseDto> {
        TODO("Not yet implemented")
    }
}