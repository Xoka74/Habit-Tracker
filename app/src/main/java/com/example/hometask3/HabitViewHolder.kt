package com.example.hometask3

import androidx.recyclerview.widget.RecyclerView
import com.example.hometask3.databinding.ItemHabitBinding

class HabitViewHolder(
    private val binding: ItemHabitBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(habit: Habit) {
        binding.habitButton.text = habit.name
    }
}