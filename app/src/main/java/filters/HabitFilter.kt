package filters

import models.Habit
import models.HabitType

class HabitFilter(
    var name: String? = null,
    var type: HabitType? = null,
) {
    fun apply(habit: Habit): Boolean {
        return (type == null || type == habit.type) &&
                (name.isNullOrEmpty() || habit.name.lowercase().contains(name!!.lowercase().trim()))
    }
}