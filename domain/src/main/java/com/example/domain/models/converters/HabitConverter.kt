package com.example.domain.models.converters

import com.example.domain.models.dto.HabitDto
import com.example.domain.models.entities.Duration
import com.example.domain.models.entities.Habit
import com.example.domain.models.entities.HabitType
import com.example.domain.models.entities.Priority
import com.example.domain.models.entities.TimeInterval
import java.util.Date

class HabitConverter {

    fun dtoToHabit(habitDto: HabitDto): Habit {
        return Habit(
            uid = habitDto.uid,
            name = habitDto.title,
            description = habitDto.description,
            priority = Priority.fromValue(habitDto.priority),
            type = HabitType.fromValue(habitDto.type),
            count = habitDto.count,
            color = habitDto.color,
            date = Date().apply {
                time = habitDto.date * 1000
            },
            periodicity = TimeInterval(habitDto.frequency, Duration.DAY)
        )
    }

    fun habitToDto(habit: Habit): HabitDto {
        return HabitDto(
            doneDates = listOf(),
            color = habit.color,
            count = habit.count,
            date = habit.date.time / 1000,
            description = habit.description,
            frequency = habit.periodicity.intervalAmount,
            priority = habit.priority.value,
            title = habit.name,
            type = habit.type.value,
            uid = habit.uid
        )
    }
}