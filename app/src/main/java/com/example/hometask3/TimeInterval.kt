package com.example.hometask3

class TimeInterval(intervalAmount: Int, val interval: Interval) : java.io.Serializable {
    val intervalAmount: Int

    init {
        this.intervalAmount =
            if (intervalAmount >= 0) intervalAmount else throw IllegalArgumentException()
    }

    override fun toString(): String {
        return if (intervalAmount != 1) "$intervalAmount $interval\\s" else "$interval"
    }
}

