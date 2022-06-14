package com.subject.server.domain.status

enum class GenderCode(
    val description: String,
    val code: Char
) {
    CODE_M("남", 'M'),
    CODE_F("여", 'F');

    companion object {
        private val genderCodes = GenderCode.values()
        fun findGenderDescriptionByCode(code: Char): String? {
            return findGenderByCode(code)?.description
        }

        fun findGenderByCode(code: Char?): GenderCode? {
            return code?.let { initial ->
                genderCodes.find { it.code == initial }
            }
        }
    }
}
