package com.example.hometask3.data.filters

import com.example.hometask3.data.models.entities.Habit
import com.example.hometask3.data.models.entities.HabitType

data class HabitFilter(
    var query: String? = null,
    var type: HabitType? = null,
) {
    fun apply(habit: Habit): Boolean {
        return (type == null || type == habit.type) &&
                (query.isNullOrEmpty() || habit.name.lowercase().contains(query!!.lowercase().trim()))
    }
}