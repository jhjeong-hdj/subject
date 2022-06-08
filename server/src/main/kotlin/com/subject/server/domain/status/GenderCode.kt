package com.subject.server.domain.status

enum class GenderCode(
    val description: String,
    val code: Char
) {
    CODE_M("남", 'M'),
    CODE_F("여", 'F');

    companion object {
        fun findGenderDescriptionByCode(code: Char): String? {
            return GenderCode
                .values()
                .find { it.code == code }
                ?.description
        }
    }
}
