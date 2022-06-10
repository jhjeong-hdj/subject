package com.subject.server.domain

import com.subject.server.domain.status.GenderCode
import com.subject.server.domain.status.GenderCode.CODE_M
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun mockPatient(
    patientId: Long? = 1L,
    name: String = "환자1",
    registrationNumber: String = "1",
    genderCode: GenderCode = CODE_M,
    birthday: String = "2022.06.07",
    phoneNumber: String = "010-1111-1111",
    visit: Visit? = null
): Patient {
    val patient = Patient(
        id = patientId,
        name = name,
        registrationNumber = registrationNumber,
        genderCode = genderCode,
        birthday = birthday,
        phoneNumber = phoneNumber
    )
    if (visit != null)
        patient.addVisit(visit)

    return patient
}

fun mockVisit(
    hospitalId: Long ?= 1L,
    hospitalName: String = "테스트병원1",
    institutionNumber: String = "1001",
    directorName: String = "김원장",
    visitId: Long ?= 1L,
    receptionDateString: String = "2022-06-01 13:00"
): Visit {
    val hospital = mockHospital(hospitalId,
        hospitalName,
        institutionNumber,
        directorName)
    val receptionDate = LocalDateTime.parse(
        receptionDateString,
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    )

    return Visit(
        id = visitId,
        hospital = hospital,
        receptionDate = receptionDate
    )
}

fun mockHospital(
    hospitalId: Long ?= 1L,
    hospitalName: String = "테스트병원1",
    institutionNumber: String = "1001",
    directorName: String = "김원장"
): Hospital {
    return Hospital(
        id = hospitalId,
        name = hospitalName,
        institutionNumber = institutionNumber,
        directorName = directorName
    )
}