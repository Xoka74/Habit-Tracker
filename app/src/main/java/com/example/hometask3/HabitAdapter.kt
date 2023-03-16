package com.example.hometask3

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.util.Log
import android.view.*
import android.view.View.OnCreateContextMenuListener
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
            binding.colorDot.setImageDrawable(newDrawable)
            binding.habitItemContainer.setOnClickListener { onItemClick?.invoke(habit, position) }
        }
    }
}