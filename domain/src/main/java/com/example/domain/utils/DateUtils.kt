package com.example.domain.utils

import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.Date

object DateUtils {

    fun nowDate() : Date {
        return Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC))
    }
}