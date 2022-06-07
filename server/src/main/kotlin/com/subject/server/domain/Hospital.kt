package com.subject.server.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id

@Entity
class Hospital {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "hospital_id")
    var id: Long? = null
    private set

    @Column(length = 45)
    var name: String? = null
    private set

    @Column(length = 20, unique = true)
    var institutionNumber: String? = null
    private set

    @Column(length = 10)
    var directorName: String? = null
    private set
}