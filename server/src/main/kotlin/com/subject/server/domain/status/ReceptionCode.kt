package com.subject.server.domain.status

enum class ReceptionCode(
    val description: String,
    val code: Int
) {
    CODE_VISIT("방문중", 1),
    CODE_END("종료", 2),
    CODE_CANCEL("취소", 3);
}