package com.example.domain.models.converters

import com.example.domain.models.dto.HabitDto
import com.example.domain.models.entities.Duration
import com.example.domain.models.entities.Habit
import com.example.domain.models.entities.HabitType
import com.example.domain.models.entities.Priority
import com.example.domain.models.entities.TimeInterval

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
            creationDate = habitDto.date * 1000,
            periodicity = TimeInterval(habitDto.frequency, Duration.DAY),
            doneDates = habitDto.doneDates.map { (it * 1000).toLong() }
        )
    }

    fun habitToDto(habit: Habit): HabitDto {
        return HabitDto(
            color = habit.color,
            description = habit.description,
            priority = habit.priority.value,
            title = habit.name,
            type = habit.type.value,
            uid = habit.uid,
            date = habit.creationDate / 1000,
            frequency = habit.periodicity.intervalAmount,
            count = habit.count,
            doneDates = habit.doneDates.map { (it / 1000).toInt() },
        )
    }
}