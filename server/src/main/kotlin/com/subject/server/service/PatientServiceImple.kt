package com.subject.server.service

import com.subject.server.domain.Patient
import com.subject.server.domain.Visit
import com.subject.server.domain.status.GenderCode
import com.subject.server.dto.AddPatientRequestDto
import com.subject.server.dto.GetPatientResponseDto
import com.subject.server.dto.UpdatePatientRequestDto
import com.subject.server.exception.extract
import com.subject.server.repository.HospitalRepository
import com.subject.server.repository.PatientRepository
import com.subject.server.util.toLocalDateTime
import org.springframework.stereotype.Service

@Service
class PatientServiceImpl(
    private val patientRepository: PatientRepository,
    private val hospitalRepository: HospitalRepository
) : PatientService {
    //todo : 접수번호 비관적 락 걸고 실패시에 재시도 하게끔 aop 구성
    override fun addPatient(requestDto: AddPatientRequestDto) {
        val findHospital = hospitalRepository.findById(requestDto.hospitalId).extract()
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
        TODO("Not yet implemented")
    }

    override fun deletePatient(id: Long) {
        TODO("Not yet implemented")
    }

    override fun getPatient(id: Long): GetPatientResponseDto {
        TODO("Not yet implemented")
    }

    override fun getPatients(page: Int, offset: Int): List<GetPatientResponseDto> {
        TODO("Not yet implemented")
    }
}