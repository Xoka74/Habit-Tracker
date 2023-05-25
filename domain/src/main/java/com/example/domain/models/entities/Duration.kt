package com.example.domain.models.entities


enum class Duration(val value : Long) {
    MINUTE(java.time.Duration.ofMinutes(1).toMillis()),
    HOUR(java.time.Duration.ofHours(1).toMillis()),
    DAY(java.time.Duration.ofDays(1).toMillis()),
    WEEK(java.time.Duration.ofDays(7).toMillis()),
    MONTH(java.time.Duration.ofDays(30).toMillis()),
    YEAR(java.time.Duration.ofDays(365).toMillis()),
    DECADE(java.time.Duration.ofDays(365).toMillis() * 10);


    fun toDuration(value : Long) : Duration? {
        return Duration.values().firstOrNull { value == it.value }
    }
}