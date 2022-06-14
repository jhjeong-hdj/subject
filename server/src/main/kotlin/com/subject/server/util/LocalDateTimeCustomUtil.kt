package com.subject.server.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun String.toLocalDateTime(pattern: String? = "yyyy-MM-dd HH:mm"): LocalDateTime {
    return LocalDateTime.parse(
        this,
        DateTimeFormatter.ofPattern(pattern)
    )
}

fun LocalDateTime.toString(pattern: String? = "yyyyMMdd"): String {
    return this.format(DateTimeFormatter.ofPattern(pattern))
}