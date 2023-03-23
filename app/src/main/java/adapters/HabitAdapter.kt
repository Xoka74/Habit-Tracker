package adapters

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.hometask3.R
import com.example.hometask3.databinding.ItemHabitBinding
import models.Habit

class HabitAdapter(
    private val habits: List<Habit>,
    private val onItemClick: ((Habit) -> Unit)? = null,
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


    inner class HabitViewHolder(
        private val binding: ItemHabitBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(habit: Habit) {
            with(binding) {
                habitTextView.text = habit.name
                val drawable =
                    (ResourcesCompat.getDrawable(
                        binding.colorDot.resources,
                        R.drawable.circle,
                        null
                    ) as GradientDrawable).apply {
                        setColor(habit.color)
                    }

                colorDot.setImageDrawable(drawable)
                priorityTextView.text = habit.priority.name
                descriptionTextView.text = habit.description
                typeTextView.text = habit.type.name
                completionsAmountTextView.text = habit.completionAmount.toString()
                timeIntervalTextView.text = habit.periodicity.toString()
                habitContainer.setOnClickListener {
                    onItemClick?.invoke(habit)
                }
            }
        }
    }
}
