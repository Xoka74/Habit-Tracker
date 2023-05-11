package com.example.hometask3.data.models.entities

class TimeInterval(intervalAmount: Int, var interval: Duration) : java.io.Serializable {
    var intervalAmount: Int

    init {
        this.intervalAmount =
            if (intervalAmount >= 0) intervalAmount else throw IllegalArgumentException()
    }

    override fun toString(): String {
        val intervalLower = interval.toString().lowercase()
        return if (intervalAmount != 1) "$intervalAmount ${intervalLower}s" else intervalLower
    }
}

