package data

import android.graphics.Color
import android.icu.util.MeasureUnit
import models.Habit
import models.HabitType
import models.Priority
import models.TimeInterval


object HabitsRepository {
    val habits = (0..30).map {
        Habit(
            name = "Name $it",
            description = "desc",
            priority = Priority.High,
            type = if (it % 2 == 0) HabitType.Good else HabitType.Bad,
            completionAmount = 10,
            periodicity = TimeInterval(10, MeasureUnit.HOUR),
            color = Color.RED,
        )
    }.toMutableList()

    fun addHabit(habit: Habit) {
        habits.add(habit)
    }

    fun changeHabit(oldHabit: Habit, newHabit: Habit) {
        habits[habits.indexOf(oldHabit)] = newHabit
    }

    fun deleteHabit(habit: Habit) {
        habits.removeAt(habits.indexOf(habit))
    }
}