package com.example.domain.models.entities



data class TimeInterval(val intervalAmount: Int, var interval: Duration)  {
    override fun toString(): String {
        val intervalLower = interval.toString().lowercase()
        return if (intervalAmount != 1) "$intervalAmount ${intervalLower}s" else intervalLower
    }
}

