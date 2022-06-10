package com.subject.server.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class RegistrationNumberTranslator {
    companion object {
        fun localDateTimeToString(localDateTime: LocalDateTime): String {
            return localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd"))
        }
    }
}