package com.subject.server.util

// 접수번호 생성기 구현 실패!
/*
@Component
class RegistrationNumberGenerator(
    private val patientRepository: PatientRepository
) {
    var nullableRegNum : String ?= null

    fun getNext() : String{
        val curRegNum = nullableRegNum?: initialize()
        return curRegNum.toInt().plus(1).toString()
    }

    private fun initialize() : String{
        return patientRepository.findLastVisitedPatient().registrationNumber
    }
}
*/