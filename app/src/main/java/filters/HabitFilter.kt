package filters

import models.Habit
import models.HabitType
import models.Priority

class HabitFilter(
    var name: String?,
    var type: HabitType?,
    val priority: Priority?,
) {
    fun apply(habit: Habit): Boolean {
        return (type == null || type == habit.type) && (priority == null || priority == habit.priority) &&
                (name.isNullOrEmpty() || habit.name.lowercase().contains(name!!.lowercase().trim()))
    }
}