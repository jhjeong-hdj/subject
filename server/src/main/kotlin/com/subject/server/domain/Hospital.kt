package com.subject.server.domain

import com.querydsl.core.annotations.QueryEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id

@Entity
@QueryEntity
class Hospital(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "hospital_id")
    val id: Long? = null,
    @Column(length = 45)
    var name: String,
    @Column(length = 20, unique = true)
    var institutionNumber: String,
    @Column(length = 10)
    var directorName: String
)