package models

import android.icu.util.TimeUnit

class TimeInterval(intervalAmount: Int, val interval: TimeUnit) : java.io.Serializable {
    val intervalAmount: Int

    init {
        this.intervalAmount =
            if (intervalAmount >= 0) intervalAmount else throw IllegalArgumentException()
    }

    override fun toString(): String {
        return if (intervalAmount != 1) "$intervalAmount ${interval.subtype}s" else interval.subtype
    }
}

