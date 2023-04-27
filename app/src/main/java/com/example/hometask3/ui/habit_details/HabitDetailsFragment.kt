package com.example.hometask3.ui.habit_details

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import androidx.core.view.children
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.hometask3.R
import com.example.hometask3.data.HabitDatabase
import com.example.hometask3.data.models.Duration
import com.example.hometask3.data.models.Habit
import com.example.hometask3.data.models.HabitType
import com.example.hometask3.data.models.Priority
import com.example.hometask3.databinding.FragmentHabitDetailsBinding
import com.example.hometask3.utils.AdapterUtils

class HabitDetailsFragment : Fragment() {
    private val binding by lazy { FragmentHabitDetailsBinding.inflate(layoutInflater) }
    private val priorityAdapter by lazy {
        AdapterUtils.createSpinnerAdapter(
            binding.prioritySpinner, Priority.values(), requireContext()
        )
    }

    private val intervalAdapter by lazy {
        AdapterUtils.createSpinnerAdapter(
            binding.intervalSpinner, Duration.values(), requireContext()
        )
    }

    private val habitViewModel: HabitViewModel by activityViewModels(factoryProducer = {
        HabitViewModelFactory(
            HabitDatabase.getInstance(requireContext()).getHabitDao()
        )
    })
    private var isEditMode = false

    companion object {
        const val habitIdTag = "habit_id_tag"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        setupColors(binding.colorsContainer,
            resources.getStringArray(R.array.habit_colors).map { str -> Color.parseColor(str) })
        arguments?.getInt(habitIdTag)?.let { id ->
            isEditMode = true
            HabitDatabase.getInstance(requireContext()).getHabitDao().getHabitById(id)
                .observe(viewLifecycleOwner) { habit ->
                    habitViewModel.postHabit(habit)
                    enableEditMode(habit)
                    binding.habitModel = habitViewModel
                }
        }.run {
            habitViewModel.postHabit(Habit())
            enableEditMode(Habit())
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            habitNameInput.addTextChangedListener { habitViewModel.triggerName(it.toString()) }
            habitDescriptionInput.addTextChangedListener { habitViewModel.triggerDescription(it.toString()) }
            timesAmountInput.addTextChangedListener {
                habitViewModel.triggerTimesAmount(it.toString().toIntOrNull() ?: 0)
            }
            habitCompletionAmountInput.addTextChangedListener {
                habitViewModel.triggerCompletionAmount(it.toString().toIntOrNull() ?: 0)
            }

            completeCreationButton.setOnClickListener { onClickComplete() }
            typeButtonsRadiogroup.setOnCheckedChangeListener { _, checkedId ->
                val checkedType = when (checkedId) {
                    R.id.good_habit_button -> HabitType.Good
                    else -> HabitType.Bad
                }
                habitViewModel.triggerType(checkedType)
            }

            prioritySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?, position: Int, id: Long
                ) {
                    habitViewModel.triggerPriority(parent?.selectedItem as Priority)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }



            intervalSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?, position: Int, id: Long
                ) {
                    habitViewModel.triggerInterval(parent?.selectedItem as Duration)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun enableEditMode(habit: Habit) {
        with(binding) {
            prioritySpinner.setSelection(priorityAdapter.getPosition(habit.priority))
            intervalSpinner.setSelection(intervalAdapter.getPosition(habit.periodicity.interval))
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
                    habitViewModel.triggerColor(color)
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