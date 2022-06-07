package com.subject.server.domain

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType.LAZY
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToMany
import javax.persistence.ManyToOne

@Entity
class Visit {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "visit_id")
    var id: Long? = null
    private set

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "hospital_id")
    var hospital: Hospital? = null
    private set

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "patient_id")
    var patient: Patient? = null
    private set

    var receptionDate: LocalDateTime? = null
    private set
}