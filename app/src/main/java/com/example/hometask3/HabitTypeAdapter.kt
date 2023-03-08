package com.example.hometask3

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hometask3.databinding.HabitTypeRadiobuttonItemBinding

class HabitTypeAdapter(
    private val habitTypes : List<HabitType>
) : RecyclerView.Adapter<HabitTypeAdapter.HabitTypeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitTypeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = HabitTypeRadiobuttonItemBinding.inflate(inflater, parent, false)
        return HabitTypeViewHolder(binding)
    }

    override fun getItemCount(): Int = habitTypes.size

    override fun onBindViewHolder(holder: HabitTypeViewHolder, position: Int) {
        holder.bind(habitTypes[position])
    }

    inner class HabitTypeViewHolder (
        private val binding: HabitTypeRadiobuttonItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(habitType: HabitType) {
            binding.habitTypeButton.text = habitType.name
        }
    }
}