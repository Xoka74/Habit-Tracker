package com.example.hometask3.presentation.ui.habits_list.adapter.diff_utils

import androidx.recyclerview.widget.DiffUtil
import com.example.domain.models.entities.Habit

class HabitsDiffUtilCallback(
    private val oldList: List<com.example.domain.models.entities.Habit>,
    private val newList: List<com.example.domain.models.entities.Habit>
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