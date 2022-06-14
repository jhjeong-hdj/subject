package com.subject.server.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class PatientTest {

    @DisplayName("병원에 방문한 적 없을 때 가장 최근 방문 정보 조회")
    @Test
    fun getLastVisitedDateFromEmpty() {
        // given
        val patient = mockPatient()

        // when
        val lastVisitedDate = patient.getLastVisitedDate()

        // then
        assertThat(lastVisitedDate).isNull()
    }

    @DisplayName("가장 최근 방문 정보 조회")
    @Test
    fun getLastVisitedDate() {
        // given
        val patient = mockPatient()
        val visit1 = mockVisit(receptionDateString = "2022-06-01 11:00")
        val visit2 = mockVisit(receptionDateString = "2022-06-02 10:59")
        val visit3 = mockVisit(receptionDateString = "2022-06-02 12:00")
        patient.addVisit(visit1)
        patient.addVisit(visit2)
        patient.addVisit(visit3)

        // when
        val lastVisitedDate = patient.getLastVisitedDate()

        // then
        assertThat(lastVisitedDate.toString()).isEqualTo("2022-06-02T12:00")
    }
}