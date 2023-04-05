package adapters

import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.hometask3.R
import com.example.hometask3.databinding.ItemHabitBinding
import diff_utils.HabitsDiffUtilCallback
import models.Habit

class HabitAdapter(
    private var habits: List<Habit>,
    private val onItemClick: ((Habit) -> Unit),
) : RecyclerView.Adapter<HabitAdapter.HabitViewHolder>() {

    private var habitsListFull: List<Habit> = mutableListOf()
    init{
        habitsListFull = habits.toList()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHabitBinding.inflate(inflater, parent, false)
        return HabitViewHolder(binding)
    }

    override fun getItemCount(): Int = habits.size

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        holder.bind(habits[position])
    }

    fun updateList(newHabits: List<Habit>) {
        val diffUtil = HabitsDiffUtilCallback(habits, newHabits)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        habits = newHabits
        habitsListFull = newHabits.toList()
        diffResults.dispatchUpdatesTo(this)
    }

    inner class HabitViewHolder(
        private val binding: ItemHabitBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(habit: Habit) {
            binding.habit = habit
            with(binding) {
                colorDot.setImageDrawable(getDrawable(habit.color))
                habitContainer.setOnClickListener { onItemClick(habit) }
            }
        }

        private fun getDrawable(color: Int): GradientDrawable {
            return (ResourcesCompat.getDrawable(
                binding.colorDot.resources, R.drawable.circle, null
            ) as GradientDrawable).apply { setColor(color) }
        }
    }
}
