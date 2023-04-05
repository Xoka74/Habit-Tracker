package fragments

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.icu.util.TimeUnit
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.hometask3.R
import com.example.hometask3.databinding.FragmentHabitDetailsBinding
import com.example.hometask3.serializable
import models.Habit
import models.HabitType
import models.Priority
import models.TimeInterval
import view_models.HabitsListViewModel
import view_models.HabitViewModel

class HabitDetailsFragment : Fragment() {
    private lateinit var binding: FragmentHabitDetailsBinding
    private lateinit var prioritySpinnerAdapter: ArrayAdapter<Priority>
    private lateinit var intervalSpinnerAdapter: ArrayAdapter<TimeUnit>
    private lateinit var habitViewModel: HabitViewModel
    private lateinit var navController: NavController
    private lateinit var listViewModel: HabitsListViewModel
    private var checkedType = HabitType.Good

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHabitDetailsBinding.inflate(layoutInflater)

        navController = findNavController()
        listViewModel = ViewModelProvider(requireActivity())[HabitsListViewModel::class.java]
        habitViewModel =
            ViewModelProvider(requireActivity())[HabitViewModel::class.java]

        with(binding) {
            completeCreationButton.setOnClickListener { onClickComplete() }
            typeButtonsRadiogroup.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.good_habit_button -> checkedType = HabitType.Good
                    R.id.bad_habit_button -> checkedType = HabitType.Bad
                }
            }
            prioritySpinnerAdapter = setupAdapterForSpinner(prioritySpinner, Priority.values())
            intervalSpinnerAdapter = setupAdapterForSpinner(intervalSpinner, TimeUnit.values())
            pickedColor.setBackgroundColor(Color.RED)
            setupColors(
                colorsContainer,
                resources.getStringArray(R.array.habit_colors).map { str -> Color.parseColor(str) })
        }

        if (habitViewModel.habit.value != null){
            enableEditModeFor(habitViewModel.habit.value!!)
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

    private fun <T> setupAdapterForSpinner(spinner: Spinner, array: Array<T>): ArrayAdapter<T> {
        val prioritySpinnerAdapter: ArrayAdapter<T> = ArrayAdapter(
            requireContext(), android.R.layout.simple_spinner_item, array
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        spinner.adapter = prioritySpinnerAdapter
        return prioritySpinnerAdapter
    }

    private fun addDeleteButtonForHabit() {
        binding.deleteButton.isVisible = true
        binding.deleteButton.setOnClickListener {
            arguments?.let {
                onClickDeleteExistingHabit()
            }
        }
    }

    private fun onClickDeleteExistingHabit() {
        habitViewModel.deleteHabit()
        navController.navigate(R.id.action_habitDetailsFragment_to_mainFragment)
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

            for (view in typeButtonsRadiogroup.children) {
                val btn = view as RadioButton
                if (btn.text.toString() == habit.type.name) {
                    btn.isChecked = true
                    break
                }
            }
        }
    }

    private fun onClickComplete() {
        if (!dataIsCorrect()) return
        habitViewModel.saveHabit(createNewHabit())
        navController.navigate(R.id.action_habitDetailsFragment_to_mainFragment)
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