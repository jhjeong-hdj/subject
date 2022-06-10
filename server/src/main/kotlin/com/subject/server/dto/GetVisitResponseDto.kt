package com.subject.server.dto

import com.subject.server.domain.Hospital
import com.subject.server.domain.Visit
import com.subject.server.exception.extract
import java.time.LocalDateTime
import javax.persistence.FetchType.LAZY
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

data class GetVisitResponseDto(
    val id: Long,
    val hospitalId: Long,
    val receptionDate: LocalDateTime
){
    companion object{
        fun of(visit : Visit) : GetVisitResponseDto{
            return GetVisitResponseDto(
                id = visit.id.extract(),
                hospitalId = visit.hospital.id.extract(),
                receptionDate = visit.receptionDate
            )
        }
    }
}