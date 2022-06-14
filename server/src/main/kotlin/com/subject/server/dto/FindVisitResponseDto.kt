package com.subject.server.dto

import com.subject.server.domain.Visit
import com.subject.server.util.extract
import java.time.LocalDateTime

data class FindVisitResponseDto(
    val id: Long,
    val hospitalId: Long,
    val receptionDate: LocalDateTime
) {
    companion object {
        fun of(visit: Visit): FindVisitResponseDto {
            return FindVisitResponseDto(
                id = visit.id.extract(),
                hospitalId = visit.hospitalUid.extract(),
                receptionDate = visit.receptionDate
            )
        }
    }
}