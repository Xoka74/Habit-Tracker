package com.example.domain.models.entities

enum class HabitType(val value: Int) {
    BAD(0),
    GOOD(1);

    companion object {
        fun fromValue(value: Int): HabitType {
            return when (value) {
                0 -> BAD
                1 -> GOOD
                else -> throw IllegalArgumentException()
            }
        }
    }
}