package models.converters

import androidx.room.TypeConverter
import models.Priority

class PriorityConverter {
    @TypeConverter
    fun fromPriority(priority: Priority): Int = priority.value

    @TypeConverter
    fun toPriority(value: Int): Priority = enumValues<Priority>()[value]
}