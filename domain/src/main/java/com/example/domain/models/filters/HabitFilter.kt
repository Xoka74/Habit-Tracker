package com.example.domain.models.filters

import com.example.domain.models.entities.Habit
import com.example.domain.models.entities.HabitType

data class HabitFilter(
    var query: String? = null,
    var type: HabitType? = null,
) {
    fun apply(habit: Habit): Boolean {
        return (type == null || type == habit.type) &&
                (query.isNullOrEmpty() || habit.name.lowercase().contains(query!!.lowercase().trim()))
    }
}