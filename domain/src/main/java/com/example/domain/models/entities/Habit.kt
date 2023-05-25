package com.example.domain.models.entities

import android.graphics.Color
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.domain.models.converters.DoneDatesConverter
import com.example.domain.models.converters.PriorityConverter
import com.example.domain.utils.DateUtils.nowDate

@Entity
@TypeConverters(PriorityConverter::class, DoneDatesConverter::class)
data class Habit (
    @PrimaryKey var uid: String = "",
    var name: String = "",
    var description: String = "",
    var priority: Priority = Priority.LOW,
    var type: HabitType = HabitType.BAD,
    var count: Int = 0,
    var creationDate: Long = nowDate().time,
    @Embedded val periodicity: TimeInterval = TimeInterval(0, Duration.DAY),
    var color: Int = Color.RED,
    var isSynced: Boolean = false,
    var doneDates: List<Long> = listOf()
)
