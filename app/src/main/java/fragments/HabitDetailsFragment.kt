package fragments

import adapters.AdapterUtils
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.icu.util.TimeUnit
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.hometask3.R
import com.example.hometask3.databinding.FragmentHabitDetailsBinding
import data.HabitDatabase
import models.Duration
import models.Habit
import models.HabitType
import models.Priority
import view_models.HabitViewModel
import view_models.factories.HabitViewModelFactory

class HabitDetailsFragment : Fragment() {
    private lateinit var binding: FragmentHabitDetailsBinding
    private lateinit var prioritySpinnerAdapter: ArrayAdapter<Priority>
    private lateinit var intervalSpinnerAdapter: ArrayAdapter<Duration>
    private lateinit var habitViewModel: HabitViewModel
    private var isEditMode = false

    companion object {
        const val habitIdTag = "habit_id_tag"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHabitDetailsBinding.inflate(layoutInflater)
        val habitDao = HabitDatabase.getInstance(requireContext()).getHabitDao()
        habitViewModel = ViewModelProvider(
            requireActivity(),
            HabitViewModelFactory(habitDao)
        )[HabitViewModel::class.java]
        with(binding) {
            prioritySpinnerAdapter = AdapterUtils.setupAdapterForSpinner(
                prioritySpinner, Priority.values(), requireContext()
            )
            intervalSpinnerAdapter = AdapterUtils.setupAdapterForSpinner(
                intervalSpinner, Duration.values(), requireContext()
            )

            setupColors(colorsContainer,
                resources.getStringArray(R.array.habit_colors).map { str -> Color.parseColor(str) })
        }

        arguments?.getInt(habitIdTag)?.let { id ->
            isEditMode = true
            HabitDatabase.getInstance(requireContext()).getHabitDao().getById(id)
                .observe(viewLifecycleOwner) { habit ->
                    habitViewModel.postHabit(habit)
                    enableEditModeFor(habit)
                    binding.habitModel = habitViewModel
                }
        }.run {
            habitViewModel.postHabit(Habit())
            enableEditModeFor(Habit())
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            habitNameInput.addTextChangedListener { habitViewModel.changeName(it.toString()) }
            habitDescriptionInput.addTextChangedListener { habitViewModel.changeDescription(it.toString()) }
            timesAmountInput.addTextChangedListener {
                habitViewModel.changeTimesAmount(it.toString().toIntOrNull() ?: 0)
            }
            habitCompletionAmountInput.addTextChangedListener {
                habitViewModel.changeCompletionAmount(it.toString().toIntOrNull() ?: 0)
            }

            completeCreationButton.setOnClickListener { onClickComplete() }
            typeButtonsRadiogroup.setOnCheckedChangeListener { _, checkedId ->
                val checkedType = when (checkedId) {
                    R.id.good_habit_button -> HabitType.Good
                    else -> HabitType.Bad
                }
                habitViewModel.changeType(checkedType)
            }

            prioritySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?, position: Int, id: Long
                ) {
                    habitViewModel.changePriority(parent?.selectedItem as Priority)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }


            intervalSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?, position: Int, id: Long
                ) {
                    habitViewModel.changeInterval(parent?.selectedItem as Duration)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

            if (isEditMode) {
                deleteButton.apply {
                    isVisible = true
                    setOnClickListener {
                        habitViewModel.deleteHabit()
                        findNavController().navigate(R.id.action_habitDetailsFragment_to_mainFragment)
                    }
                }
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun enableEditModeFor(habit: Habit) {
        with(binding) {
            prioritySpinner.setSelection(prioritySpinnerAdapter.getPosition(habit.priority))
            intervalSpinner.setSelection(intervalSpinnerAdapter.getPosition(habit.periodicity.interval))
            (typeButtonsRadiogroup.children.first {
                (it as TextView).text.toString() == habit.type.name
            } as RadioButton).isChecked = true
        }
    }

    private fun onClickComplete() {
        if (!dataIsCorrect()) return
        if (isEditMode) habitViewModel.edit() else habitViewModel.save()
        findNavController().navigate(R.id.action_habitDetailsFragment_to_mainFragment)
    }

    private fun setupColors(colorContainer: LinearLayout, colors: List<Int>) {
        val density = resources.displayMetrics.density.toInt()
        for (color in colors) {
            val imageView = FrameLayout(requireContext()).apply {
                layoutParams = FrameLayout.LayoutParams(density * 100, density * 100)
                setBackgroundColor(color)
                setOnClickListener {
                    binding.pickedColor.setBackgroundColor(color)
                    habitViewModel.changeColor(color)
                }
            }
            colorContainer.addView(imageView)
        }
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
}