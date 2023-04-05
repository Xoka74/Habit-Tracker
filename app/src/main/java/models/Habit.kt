package models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class Habit(
    val name: String,
    val description: String,
    val priority: Priority,
    val type: HabitType,
    val completionAmount: Int,
    val periodicity: TimeInterval,
    val color: Int,
) : Parcelable