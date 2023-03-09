package com.example.hometask3

class TimeInterval(timesPerInterval: Int, val interval: Interval) : java.io.Serializable{
    val timesPerInterval: Int

    init {
        this.timesPerInterval =
            if (timesPerInterval >= 0) timesPerInterval else throw IllegalArgumentException()
    }

    override fun toString(): String {
        return if (timesPerInterval != 1) "$timesPerInterval times per $interval" else "Every $interval"
    }
}


enum class Interval : java.io.Serializable {
    Second,
    Minute,
    Hour,
    Day,
    Week,
    Month,
    Year,
    Decade,
}