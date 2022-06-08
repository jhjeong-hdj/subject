package com.subject.server.domain.status

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

//todo : 하위 3개 테스트 코드 작성
internal class GenderCodeTest {

    @DisplayName("정상적인 성별 코드에 따라 정상적인 성별 설명 반환 성공")
    @Test
    fun testFindGenderDescriptionByCodeSucess() {
        // given && when
        val male = GenderCode.findGenderDescriptionByCode('M')
        val female = GenderCode.findGenderDescriptionByCode('F')

        // then
        Assertions.assertThat(male).isEqualTo(GenderCode.CODE_M.description)
        Assertions.assertThat(female).isEqualTo(GenderCode.CODE_F.description)
    }

    @DisplayName("정상적인 성별 코드에 따라 정상적인 성별 반환 성공")
    @Test
    fun testFindGenderByCodeSuccess() {
        // given && when
        val male = GenderCode.findGenderDescriptionByCode('M')
        val female = GenderCode.findGenderDescriptionByCode('F')

        // then
        Assertions.assertThat(male).isEqualTo(GenderCode.CODE_M.description)
        Assertions.assertThat(female).isEqualTo(GenderCode.CODE_F.description)
    }

    @DisplayName("비정상적인 성별 코드에 따라 정상적인 성별 설명 반환 실패")
    @Test
    fun testFindGenderDescriptionByCodeFail() {
        // given && when
        val male = GenderCode.findGenderDescriptionByCode('M')
        val female = GenderCode.findGenderDescriptionByCode('F')

        // then
        Assertions.assertThat(male).isEqualTo(GenderCode.CODE_M.description)
        Assertions.assertThat(female).isEqualTo(GenderCode.CODE_F.description)
    }

    @DisplayName("비정상적인 성별 코드에 따라 정상적인 성별 반환 실패")
    @Test
    fun testFindGenderByCodeFail() {
        // given && when
        val male = GenderCode.findGenderDescriptionByCode('M')
        val female = GenderCode.findGenderDescriptionByCode('F')

        // then
        Assertions.assertThat(male).isEqualTo(GenderCode.CODE_M.description)
        Assertions.assertThat(female).isEqualTo(GenderCode.CODE_F.description)
    }
}