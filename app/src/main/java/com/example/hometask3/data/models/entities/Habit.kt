package com.example.hometask3.data.models.entities

import android.graphics.Color
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.hometask3.data.models.converters.DateConverter
import com.example.hometask3.data.models.converters.PriorityConverter
import com.example.hometask3.utils.DateUtils.nowDate
import java.io.Serializable
import java.util.Date
import java.util.UUID

@Entity
@TypeConverters(PriorityConverter::class, DateConverter::class)
data class Habit (
    @PrimaryKey var uid: String = "",
    var name: String = "",
    var description: String = "",
    var priority: Priority = Priority.LOW,
    var type: HabitType = HabitType.BAD,
    var count: Int = 0,
    var date: Date = nowDate(),
    @Embedded val periodicity: TimeInterval = TimeInterval(0, Duration.DAY),
    var color: Int = Color.RED,
    var isSynced: Boolean = false,
)
