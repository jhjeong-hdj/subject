package com.subject.server.domain

import com.subject.server.domain.status.GenderCode
import java.time.LocalDateTime
import java.util.StringJoiner
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
class Patient {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "patient_id")
    var id: Long? = null
    private set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id")
    var hospital: Hospital? = null
    private set

    @Column(length = 45)
    var name: String? = null
    private set

    @Column(length = 13, unique = true)
    var registrationNumber: String? = null
    private set

    @Column(length = 10)
    var genderCode: GenderCode? = null
    private set

    @Column(length = 10)
    var birthday: String? = null
    private set

    @Column(length = 20)
    var phoneNumber: String? = null
    private set

    @Column
    var latestVisitDate: LocalDateTime? = null
    private set
}