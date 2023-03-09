package com.example.hometask3

import android.graphics.Color
import android.util.Log
import android.view.*
import android.view.View.OnCreateContextMenuListener
import androidx.recyclerview.widget.RecyclerView
import com.example.hometask3.databinding.ItemHabitBinding

class HabitAdapter(
    private val habits: List<Habit>,
    private val onItemClick: ((Habit, Int) -> Unit)? = null,
    //private val onItemLongClick: ((Int) -> Boolean) = { _ -> false },
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
    ) : RecyclerView.ViewHolder(binding.root), OnCreateContextMenuListener {

        fun bind(habit: Habit, position: Int) {
            binding.habitTextView.text = habit.name
            binding.habitTextView.setTextColor(habit.color)
            binding.habitTextView.setOnClickListener { onItemClick?.invoke(habit, position) }
            //binding.habitType.text = habit.type.name
            //binding.habitTextView.setOnLongClickListener { onItemLongClick.invoke(position) }
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            view: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            menu?.add(0, view?.id ?: -1, 0, "delete")
            menu?.add(0, view?.id ?: -1, 0, "share")
        }
    }
}