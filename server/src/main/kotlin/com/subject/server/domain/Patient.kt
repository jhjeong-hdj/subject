package com.subject.server.domain

import com.subject.server.domain.status.GenderCode
import java.time.LocalDateTime
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
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
    var genderCode: GenderCode,
    @Column(length = 10)
    val birthday: String,
    @Column(length = 20)
    var phoneNumber: String
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
}