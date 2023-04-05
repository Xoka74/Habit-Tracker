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
    private var habits: MutableList<Habit>,
    private val onItemClick: ((Habit) -> Unit),
) : RecyclerView.Adapter<HabitAdapter.HabitViewHolder>(), Filterable {

    private val habitsListFull: MutableList<Habit> = mutableListOf()
    init{
        habitsListFull.addAll(habits)
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

    fun updateList(newHabits: MutableList<Habit>) {
        val diffUtil = HabitsDiffUtilCallback(habits, newHabits)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        habits = newHabits
        habitsListFull.clear()
        habitsListFull.addAll(newHabits)
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

    override fun getFilter(): Filter = filter

    private val filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredHabits = mutableListOf<Habit>()
            if (constraint.isNullOrEmpty()) {
                filteredHabits.addAll(habitsListFull)
            } else {
                val filterPattern = constraint.toString().lowercase().trim()
                filteredHabits.addAll(habitsListFull.filter { it.name.lowercase().contains(filterPattern) })
            }

            return FilterResults().apply {
                values = filteredHabits
            }
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            val filterResults = results?.values as MutableList<Habit>
            val diffUtil = HabitsDiffUtilCallback(habits, filterResults)
            val diffResults = DiffUtil.calculateDiff(diffUtil)
            habits = filterResults
            diffResults.dispatchUpdatesTo(this@HabitAdapter)
        }
    }
}
