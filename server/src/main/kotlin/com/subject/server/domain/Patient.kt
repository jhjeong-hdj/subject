package com.subject.server.domain

import com.querydsl.core.annotations.QueryEntity
import com.subject.server.domain.status.GenderCode
import com.subject.server.domain.status.PatientStatus
import com.subject.server.domain.status.PatientStatus.DELETE
import com.subject.server.domain.status.PatientStatus.EXIST
import com.subject.server.exception.CustomExceptionType
import java.time.LocalDateTime
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType.STRING
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
@QueryEntity
class Patient(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "patient_id")
    val id: Long? = null,
    @OneToMany(cascade = [CascadeType.PERSIST])
    val visitList: MutableList<Visit> = emptyList<Visit>().toMutableList(),
    @Column(length = 45)
    var name: String,
    @Column(length = 13, unique = true)
    val registrationNumber: String,
    @Column(length = 10)
    @Enumerated(STRING)
    var genderCode: GenderCode?,
    @Column(length = 10)
    val birthday: String?,
    @Column(length = 20)
    var phoneNumber: String?,
    @Enumerated(STRING)
    var status: PatientStatus = EXIST
) {
    fun getLastVisitedDate(): LocalDateTime? {
        if (visitList.isEmpty())
            return null

        return visitList
            .sortedBy { it.receptionDate }
            .reversed()[0]
            .receptionDate
    }

    fun addVisit(visit: Visit) {
        visitList.add(visit)
    }

    fun changePatientInfo(
        name: String?,
        genderCode: GenderCode?,
        phoneNumber: String?
    ) {
        this.name = name ?: this.name
        this.genderCode = genderCode ?: this.genderCode
        this.phoneNumber = phoneNumber ?: this.phoneNumber
    }

    fun isExistOrThrow() {
        if (status == DELETE) throw CustomExceptionType.NOT_FOUND_PATIENT.toException()
    }
}