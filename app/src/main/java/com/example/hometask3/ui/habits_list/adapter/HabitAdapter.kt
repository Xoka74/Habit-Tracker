package com.example.hometask3.ui.habits_list.adapter

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.hometask3.R
import com.example.hometask3.databinding.ItemHabitBinding
import com.example.hometask3.ui.habits_list.adapter.diff_utils.HabitsDiffUtilCallback
import com.example.hometask3.data.models.Habit

class HabitAdapter(
    private var habits: List<Habit> = listOf(),
    private val onItemClick: ((Habit) -> Unit),
) : RecyclerView.Adapter<HabitAdapter.HabitViewHolder>() {

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
