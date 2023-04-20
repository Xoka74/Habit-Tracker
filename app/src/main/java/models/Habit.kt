package models

import android.graphics.Color
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import models.converters.PriorityConverter

@Entity
@TypeConverters(PriorityConverter::class)
data class Habit(
    @PrimaryKey val id: Int? = null,
    var name: String = "",
    var description: String = "",
    var priority: Priority = Priority.Low,
    var type: HabitType = HabitType.Good,
    var completionAmount: Int = 0,
    @Embedded var periodicity: TimeInterval = TimeInterval(0, Duration.DAY),
    var color: Int = Color.RED,
)
