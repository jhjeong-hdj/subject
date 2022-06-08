package com.subject.server.domain.status

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class GenderCodeTest {

    @DisplayName("성별 코드에 따라 정상적인 성별 설명 출력 테스트")
    @Test
    fun testFindGenderDescriptionByCode() {
        // given && when
        val male = GenderCode.findGenderDescriptionByCode('M')
        val female = GenderCode.findGenderDescriptionByCode('F')

        // then
        Assertions.assertThat(male).isEqualTo(GenderCode.CODE_M.description)
        Assertions.assertThat(female).isEqualTo(GenderCode.CODE_F.description)
    }
}