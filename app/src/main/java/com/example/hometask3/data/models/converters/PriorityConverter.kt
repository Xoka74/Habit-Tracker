package com.example.hometask3.data.models.converters

import androidx.room.TypeConverter
import com.example.hometask3.data.models.entities.Priority

class PriorityConverter {
    @TypeConverter
    fun fromPriority(priority: Priority): Int = priority.value

    @TypeConverter
    fun toPriority(value: Int): Priority = enumValues<Priority>()[value]
}