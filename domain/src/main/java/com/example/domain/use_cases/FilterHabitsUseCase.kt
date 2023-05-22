package com.example.domain.use_cases

import com.example.domain.models.entities.Habit
import com.example.domain.models.filters.HabitFilter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class FilterHabitsUseCase(
    private val habitsFlow: Flow<List<Habit>>,
    private val filterFlow: Flow<HabitFilter>,
) {

    operator fun invoke(): Flow<List<Habit>> {
        return habitsFlow.combine(filterFlow) { habits, filter ->
            habits.filter { filter.apply(it) }
        }
    }
}