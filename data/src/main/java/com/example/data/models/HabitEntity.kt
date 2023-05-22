package com.example.data.models

import android.graphics.Color
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.domain.models.converters.DateConverter
import com.example.domain.models.converters.PriorityConverter
import com.example.domain.models.entities.Duration
import com.example.domain.models.entities.HabitType
import com.example.domain.models.entities.Priority
import com.example.domain.models.entities.TimeInterval
import com.example.domain.utils.DateUtils
import java.util.Date

@Entity
@TypeConverters(PriorityConverter::class, DateConverter::class)
data class HabitEntity(
    @PrimaryKey
    var uid: String = "",
    var name: String = "",
    var description: String = "",
    var priority: Priority = Priority.LOW,
    var type: HabitType = HabitType.BAD,
    var count: Int = 0,
    var date: Date = DateUtils.nowDate(),
    @Embedded
    val periodicity: TimeInterval = TimeInterval(0, Duration.DAY),
    var color: Int = Color.RED,
    var isSynced: Boolean = false,
)