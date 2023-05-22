package com.example.hometask3.presentation.ui.habit_details

import android.content.Context
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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.domain.models.entities.Duration
import com.example.domain.models.entities.Habit
import com.example.domain.models.entities.HabitType
import com.example.domain.models.entities.Priority
import com.example.hometask3.MainApplication
import com.example.hometask3.R
import com.example.hometask3.databinding.FragmentHabitDetailsBinding
import com.example.hometask3.di.AppComponent
import com.example.hometask3.utils.AdapterUtils
import javax.inject.Inject


class HabitDetailsFragment : Fragment() {
    private val binding by lazy { FragmentHabitDetailsBinding.inflate(layoutInflater) }
    private val priorityAdapter by lazy {
        AdapterUtils.createSpinnerAdapter(
            binding.prioritySpinner,
            Priority.values(),
            requireContext(),
        )
    }
    private val intervalAdapter by lazy {
        AdapterUtils.createSpinnerAdapter(
            binding.intervalSpinner,
            com.example.domain.models.entities.Duration.values(),
            requireContext(),
        )
    }

    @Inject
    lateinit var habitViewModelFactory: HabitViewModelFactory
    private lateinit var habitViewModel: HabitViewModel

    companion object {
        const val habitIdTag = "habit_id_tag"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MainApplication)
            .appComponent
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        setupColors(binding.colorsContainer,
            resources.getStringArray(R.array.habit_colors).map { str -> Color.parseColor(str) })

        habitViewModel = ViewModelProvider(this, habitViewModelFactory)[HabitViewModel::class.java]

        val habitId = arguments?.getString(habitIdTag)
        if (habitId != null) {
            habitViewModel.triggerHabit(habitId)
            binding.habitModel = habitViewModel
        } else {
            val defaultHabit = Habit()
            habitViewModel.triggerHabit(null)
            enableEditMode(defaultHabit)
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
                habitViewModel.triggerCount(it.toString().toIntOrNull() ?: 0)
            }

            completeCreationButton.setOnClickListener { onClickComplete() }
            typeButtonsRadiogroup.setOnCheckedChangeListener { _, checkedId ->
                val checkedType = when (checkedId) {
                    R.id.good_habit_button -> HabitType.GOOD
                    else -> HabitType.BAD
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
                (it as TextView).text.toString().uppercase() == habit.type.name
            } as RadioButton).isChecked = true
        }
    }

    private fun onClickComplete() {
        if (!dataIsCorrect()) return
        habitViewModel.save()
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