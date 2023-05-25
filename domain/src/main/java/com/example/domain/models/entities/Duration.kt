package com.example.domain.models.entities


enum class Duration(val value : Long) {
    MINUTE(java.time.Duration.ofMinutes(1).toSeconds()),
    HOUR(java.time.Duration.ofHours(1).toSeconds()),
    DAY(java.time.Duration.ofDays(1).toSeconds()),
    WEEK(java.time.Duration.ofDays(7).toSeconds()),
    MONTH(java.time.Duration.ofDays(30).toSeconds()),
    YEAR(java.time.Duration.ofDays(365).toSeconds()),
    DECADE(java.time.Duration.ofDays(365).toSeconds() * 10);


    fun toDuration(value : Long) : Duration? {
        return Duration.values().firstOrNull { value == it.value }
    }
}