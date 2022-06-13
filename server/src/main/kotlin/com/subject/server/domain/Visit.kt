package com.subject.server.domain

import com.querydsl.core.annotations.QueryEntity
import com.subject.server.domain.status.VisitHistoryStatus
import com.subject.server.domain.status.VisitHistoryStatus.DELETE
import com.subject.server.domain.status.VisitHistoryStatus.EXIST
import com.subject.server.exception.CustomExceptionType
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType.STRING
import javax.persistence.Enumerated
import javax.persistence.FetchType.LAZY
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
@QueryEntity
class Visit(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "visit_id")
    val id: Long? = null,
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "hospital_id")
    var hospital: Hospital,
    val receptionDate: LocalDateTime,
    @Enumerated(STRING)
    var status: VisitHistoryStatus = EXIST
){
    fun deleteVisitInfo(){
        this.status = DELETE
    }
    fun isExistOrThrow() {
        if (status == DELETE) throw CustomExceptionType.NOT_FOUND_PATIENT.toException()
    }
}