package com.example.hometask3

import android.graphics.drawable.GradientDrawable
import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.example.hometask3.databinding.ItemHabitBinding

class HabitAdapter (
    private val habits: List<Habit>,
    private val onItemClick: ((Habit, Int) -> Unit)? = null,
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
    ) : RecyclerView.ViewHolder(binding.root){
        fun bind(habit: Habit, position: Int) {
            binding.habitTextView.text = habit.name
            val newDrawable = (binding.colorDot.drawable as GradientDrawable).apply {
                this.setColor(habit.color)
            }
            binding.priorityTextView.text = habit.priority.name
            binding.colorDot.setImageDrawable(newDrawable)
            binding.descriptionTextView.text = habit.description
            binding.typeTextView.text = habit.type.name
            binding.completionsAmountTextView.text = habit.completionAmount.toString()
            binding.timeIntervalTextView.text = habit.periodicity.toString()
            binding.habitContainer.setOnClickListener { onItemClick?.invoke(habit, position) }
        }
    }
}