package com.example.hometask3.ui.habits_list.adapter.diff_utils

import androidx.recyclerview.widget.DiffUtil
import com.example.hometask3.data.models.entities.Habit

class HabitsDiffUtilCallback(
    private val oldList: List<Habit>,
    private val newList: List<Habit>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].name == newList[newItemPosition].name
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}