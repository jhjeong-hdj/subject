package com.subject.server.domain

import com.subject.server.domain.status.GenderCode
import com.subject.server.domain.status.GenderCode.CODE_M
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun mockPatient(
    name: String = "환자1",
    registrationNumber: String = "1",
    genderCode: GenderCode = CODE_M,
    birthday: String = "2022-06-07",
    phoneNumber: String = "010-1111-1111",
    visit: Visit? = null
): Patient {
    val patient = Patient(
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
    hospitalName: String = "네이버",
    receptionDateString: String = "2022-06-01 13:00"
): Visit {
    val hospital = Hospital(
        name = hospitalName,
        institutionNumber = "102",
        directorName = "김원장")
    val receptionDate = LocalDateTime.parse(
        receptionDateString,
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    )

    return Visit(
        hospital = hospital,
        receptionDate = receptionDate
    )
}