package com.subject.server.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun toLocalDateTime(sequence: String): LocalDateTime {
    val pattern = "yyyy-MM-dd HH:mm"
    return LocalDateTime.parse(
        sequence,
        DateTimeFormatter.ofPattern(pattern)
    )
}

fun toLocalDateTime(sequence: String, pattern: String): LocalDateTime {
    return LocalDateTime.parse(
        sequence,
        DateTimeFormatter.ofPattern(pattern)
    )
}