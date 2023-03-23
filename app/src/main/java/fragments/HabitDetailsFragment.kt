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
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.hometask3.R
import com.example.hometask3.databinding.FragmentHabitDetailsBinding
import com.example.hometask3.parcelable
import data.HabitsProvider
import models.Habit
import models.HabitType
import models.Priority
import models.TimeInterval

class HabitDetailsFragment : Fragment() {
    private lateinit var binding: FragmentHabitDetailsBinding
    private lateinit var prioritySpinnerAdapter: ArrayAdapter<Priority>
    private lateinit var intervalSpinnerAdapter: ArrayAdapter<TimeUnit>
    private lateinit var colorContainerLayout: LinearLayout
    private lateinit var pickedColorImageView: ImageView
    private lateinit var navController: NavController
    private var isEditMode = false
    private lateinit var colors: List<Int>

    companion object {
        const val habitTag = "habit"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHabitDetailsBinding.inflate(layoutInflater)
        colors = resources.getStringArray(R.array.habit_colors).map { str -> Color.parseColor(str) }
        navController = findNavController()
        binding.completeCreationButton.setOnClickListener { onClickComplete() }

        prioritySpinnerAdapter = setupAdapterForSpinner(binding.prioritySpinner, Priority.values())
        intervalSpinnerAdapter = setupAdapterForSpinner(binding.intervalSpinner, TimeUnit.values())

        colorContainerLayout = binding.colorsContainer
        pickedColorImageView = binding.pickedColor
        pickedColorImageView.setBackgroundColor(Color.RED)

        setupColors(colorContainerLayout)

        arguments?.parcelable<Habit>(habitTag)?.let {
            enableEditModeFor(it)
            addDeleteButtonForHabit()
        }
        return binding.root
    }

    private fun setupColors(colorContainer: LinearLayout) {
        for (color in colors) {
            val density = resources.displayMetrics.density.toInt()
            val imageView = FrameLayout(requireContext()).apply {
                layoutParams = FrameLayout.LayoutParams(density * 100, density * 100)
                setBackgroundColor(color)

                setOnClickListener {
                    pickedColorImageView.setBackgroundColor((it.background as ColorDrawable).color)
                }
            }
            colorContainer.addView(imageView)
        }
    }

    private fun <T> setupAdapterForSpinner(spinner: Spinner, array: Array<T>): ArrayAdapter<T> {
        val prioritySpinnerAdapter: ArrayAdapter<T> = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            array
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        spinner.adapter = prioritySpinnerAdapter
        return prioritySpinnerAdapter
    }

    private fun addDeleteButtonForHabit() {
        binding.deleteButton.isVisible = true
        binding.deleteButton.setOnClickListener {
            arguments?.let {
                onClickDeleteExistingHabit(arguments?.parcelable(habitTag)!!)
            }
        }
    }

    private fun onClickDeleteExistingHabit(habit: Habit) {
        HabitsProvider.deleteHabit(habit)
        navController.navigate(R.id.action_habitDetailsFragment_to_mainFragment)
    }

    private fun enableEditModeFor(habit: Habit) {
        isEditMode = true

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

        if (isEditMode)
            HabitsProvider.changeHabit(arguments?.parcelable(habitTag)!!, createNewHabit())
        else {
            HabitsProvider.insert(createNewHabit())
        }

        navController.navigate(R.id.action_habitDetailsFragment_to_mainFragment)
    }

    private fun dataIsCorrect(): Boolean {
        if (binding.habitNameInput.text.toString().trim().isEmpty()) {
            binding.habitNameInput.error = "Name is required!"
            return false
        }

        if (binding.habitCompletionAmountInput.text.toString().trim().isEmpty()) {
            binding.habitCompletionAmountInput.error = "Completion amount is required!"
            return false
        }

        if (binding.timesAmountInput.text.toString().trim().isEmpty()) {
            binding.timesAmountInput.error = "Interval is required!"
            return false
        }
        return true
    }

    private fun createNewHabit(): Habit {
        return Habit(
            name = binding.habitNameInput.text.toString(),
            description = binding.habitDescriptionInput.text.toString(),
            completionAmount = binding.habitCompletionAmountInput.text.toString().trim().toInt(),
            priority = binding.prioritySpinner.selectedItem as Priority,
            type = HabitType.values()[binding.typeButtonsRadiogroup.indexOfChild(
                binding.root.findViewById<RadioButton>(
                    binding.typeButtonsRadiogroup.checkedRadioButtonId
                )
            )],
            periodicity = TimeInterval(
                binding.timesAmountInput.text.toString().trim().toInt(),
                binding.intervalSpinner.selectedItem as TimeUnit
            ),
            color = (pickedColorImageView.background as ColorDrawable).color
        )
    }
}