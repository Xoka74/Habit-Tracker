package data

import android.graphics.Color
import android.icu.util.MeasureUnit
import models.Habit
import models.HabitType
import models.Priority
import models.TimeInterval


object HabitsProvider {
    private val bad_habits = (0..15).map {
        Habit(
            name = "Name $it",
            description = "desc",
            priority = Priority.High,
            type = HabitType.Bad,
            completionAmount = 10,
            periodicity = TimeInterval(10, MeasureUnit.HOUR),
            color = Color.RED,
        )
    }

    private val good_habits = (16..30).map {
        Habit(
            name = "Name $it",
            description = "desc",
            priority = Priority.High,
            type = HabitType.Good,
            completionAmount = 10,
            periodicity = TimeInterval(10, MeasureUnit.HOUR),
            color = Color.RED,
        )
    }
    private val habits = bad_habits.union(good_habits).toMutableList()

    fun insert(habit: Habit) {
        habits.add(habit)
    }

    fun changeHabit(oldHabit: Habit, newHabit: Habit) {
        habits[habits.indexOf(oldHabit)] = newHabit
    }

    fun deleteHabit(habit: Habit) {
        habits.removeAt(habits.indexOf(habit))
    }

    fun getHabitsByType(habitType: HabitType): List<Habit> {
        return habits.filter { habit: Habit -> habit.type == habitType }
    }
}