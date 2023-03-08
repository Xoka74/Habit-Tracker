package com.example.hometask3

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hometask3.databinding.ItemHabitBinding

class HabitAdapter(
    private val habits: List<Habit>,
    private val onItemClick: ((Habit, Int) -> Unit)? = null
) : RecyclerView.Adapter<HabitAdapter.HabitViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHabitBinding.inflate(inflater, parent, false)
        return HabitViewHolder(binding)
    }

    override fun getItemCount(): Int = habits.size

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        holder.bind(habits[position], position)
    }


    inner class HabitViewHolder(
        private val binding: ItemHabitBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(habit: Habit, position: Int) {
            binding.habitTextView.text = habit.name
            binding.habitTextView.setOnClickListener { onItemClick?.invoke(habit, position) }
        }
    }
}