package fragments

import adapters.AdapterUtils
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.icu.util.TimeUnit
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RadioButton
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.hometask3.R
import com.example.hometask3.databinding.FragmentHabitDetailsBinding
import models.Habit
import models.HabitType
import models.Priority
import models.TimeInterval
import view_models.HabitViewModel

class HabitDetailsFragment : Fragment() {
    private lateinit var binding: FragmentHabitDetailsBinding
    private lateinit var prioritySpinnerAdapter: ArrayAdapter<Priority>
    private lateinit var intervalSpinnerAdapter: ArrayAdapter<TimeUnit>
    private lateinit var habitViewModel: HabitViewModel
    private var checkedType = HabitType.Good

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHabitDetailsBinding.inflate(layoutInflater)

        habitViewModel = ViewModelProvider(requireActivity())[HabitViewModel::class.java]

        with(binding) {
            completeCreationButton.setOnClickListener { onClickComplete() }
            typeButtonsRadiogroup.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.good_habit_button -> checkedType = HabitType.Good
                    R.id.bad_habit_button -> checkedType = HabitType.Bad
                }
            }
            prioritySpinnerAdapter = AdapterUtils.setupAdapterForSpinner(
                prioritySpinner, Priority.values(), requireContext()
            )
            intervalSpinnerAdapter = AdapterUtils.setupAdapterForSpinner(
                intervalSpinner, TimeUnit.values(), requireContext()
            )
            pickedColor.setBackgroundColor(Color.RED)
            setupColors(colorsContainer,
                resources.getStringArray(R.array.habit_colors).map { str -> Color.parseColor(str) })
        }

        habitViewModel.habit.value?.let {
            enableEditModeFor(it)
            addDeleteButtonForHabit()
        }

        return binding.root
    }

    private fun setupColors(colorContainer: LinearLayout, colors: List<Int>) {
        val density = resources.displayMetrics.density.toInt()
        for (color in colors) {
            val imageView = FrameLayout(requireContext()).apply {
                layoutParams = FrameLayout.LayoutParams(density * 100, density * 100)
                setBackgroundColor(color)
                setOnClickListener {
                    binding.pickedColor.setBackgroundColor((it.background as ColorDrawable).color)
                }
            }
            colorContainer.addView(imageView)
        }
    }


    private fun addDeleteButtonForHabit() {
        binding.deleteButton.isVisible = true
        binding.deleteButton.setOnClickListener {
            habitViewModel.deleteHabit()
            findNavController().navigate(R.id.action_habitDetailsFragment_to_mainFragment)
        }
    }

    private fun enableEditModeFor(habit: Habit) {
        with(binding) {
            habitNameInput.setText(habit.name)
            habitDescriptionInput.setText(habit.description)
            prioritySpinner.setSelection(prioritySpinnerAdapter.getPosition(habit.priority))
            timesAmountInput.setText(habit.periodicity.intervalAmount.toString())
            intervalSpinner.setSelection(intervalSpinnerAdapter.getPosition(habit.periodicity.interval))
            habitCompletionAmountInput.setText(habit.completionAmount.toString())
            pickedColor.setBackgroundColor(habit.color)
            typeButtonsRadiogroup.children.first {
                ((it as RadioButton).text.toString() == habit.type.name)
            }.apply {
                (this as RadioButton).isChecked = true
            }
        }
    }

    private fun onClickComplete() {
        if (!dataIsCorrect()) return
        habitViewModel.saveOrEdit(createNewHabit())
        findNavController().navigate(R.id.action_habitDetailsFragment_to_mainFragment)
    }

    private fun dataIsCorrect(): Boolean {
        with(binding) {
            if (habitNameInput.text.toString().trim().isEmpty()) {
                habitNameInput.error = "Name is required!"
                return false
            }

            if (habitCompletionAmountInput.text.toString().trim().isEmpty()) {
                habitCompletionAmountInput.error = "Completion amount is required!"
                return false
            }

            if (timesAmountInput.text.toString().trim().isEmpty()) {
                timesAmountInput.error = "Interval is required!"
                return false
            }
        }

        return true
    }

    private fun createNewHabit(): Habit {
        with(binding) {
            return Habit(
                name = habitNameInput.text.toString(),
                description = habitDescriptionInput.text.toString(),
                completionAmount = habitCompletionAmountInput.text.toString().trim().toInt(),
                priority = prioritySpinner.selectedItem as Priority,
                type = checkedType,
                periodicity = TimeInterval(
                    timesAmountInput.text.toString().trim().toInt(),
                    intervalSpinner.selectedItem as TimeUnit
                ),
                color = (pickedColor.background as ColorDrawable).color,
            )
        }
    }
}