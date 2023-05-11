package com.example.hometask3.data.models.entities

enum class Priority(val value: Int) {
    LOW(0),
    MEDIUM(1),
    HIGH(2);

    companion object {
        fun fromValue(value: Int): Priority {
            return when (value) {
                0 -> LOW
                1 -> MEDIUM
                2 -> HIGH
                else -> throw IllegalArgumentException()
            }
        }
    }
}