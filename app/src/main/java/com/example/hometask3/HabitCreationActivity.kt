package com.example.hometask3

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.core.view.isVisible
import com.example.hometask3.databinding.ActivityHabitCreationBinding


class HabitCreationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHabitCreationBinding
    private lateinit var prioritySpinnerAdapter: ArrayAdapter<Priority>
    private lateinit var intervalSpinnerAdapter: ArrayAdapter<Interval>
    private var habitState = HabitState.Created

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHabitCreationBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.typeButtonsRadiogroup.setOnClickListener { onHabitTypeRadioButtonClicked() }
//        binding.typeButtonsRadiogroup.setOnCheckedChangeListener { radioGroup, i ->
//            Log.e("CHECKED", radioGroup.findViewById<RadioButton>(i).text.toString())
//        }
        binding.completeCreationButton.setOnClickListener { onCompleteHabitCreationButton() }

        prioritySpinnerAdapter = setupAdapter(binding.prioritySpinner, Priority.values())
        intervalSpinnerAdapter = setupAdapter(binding.intervalSpinner, Interval.values())

        val habit = intent.parcelable<Habit>("habit")
        if (habit != null) {
            enableEditModeFor(habit)
            addDeleteButtonForHabit()
        }
    }

    private fun <T> setupAdapter(spinner: Spinner, array: Array<T>): ArrayAdapter<T> {
        val prioritySpinnerAdapter: ArrayAdapter<T> = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            array
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        spinner.onItemSelectedListener = SpinnerSelectedItemListener()
        spinner.adapter = prioritySpinnerAdapter
        return prioritySpinnerAdapter
    }

    private fun addDeleteButtonForHabit() {
        binding.deleteButton.isVisible = true
        binding.deleteButton.setOnClickListener {
            onClickDeleteExistingHabit(
                intent.getIntExtra("position", 0)
            )
        }
    }

    private fun onClickDeleteExistingHabit(position: Int) {
        habitState = HabitState.Deleted
        val i = Intent(this, MainActivity::class.java).apply {
            putExtra("habit_state", habitState)
            putExtra("position", position)
        }

        setResult(RESULT_OK, i)
        finish()
    }

    private fun enableEditModeFor(habit: Habit) {
        habitState = HabitState.Edited
        binding.habitNameInput.setText(habit.name)
        binding.habitDescriptionInput.setText(habit.description)
        binding.prioritySpinner.setSelection(prioritySpinnerAdapter.getPosition(habit.priority))
        binding.timesAmountInput.setText(habit.periodicity.timesPerInterval.toString())
        binding.intervalSpinner.setSelection(intervalSpinnerAdapter.getPosition(habit.periodicity.interval))
        binding.habitCompletionAmountInput.setText(habit.completionAmount.toString())

        for (view in binding.typeButtonsRadiogroup.children) {
            val btn = view as RadioButton
            if (btn.text.toString() == habit.type.name){
                btn.isChecked = true
                break
            }
        }
    }

    private fun onCompleteHabitCreationButton() {
        if (!dataIsCorrect()) return

        val i = Intent(this, MainActivity::class.java).apply {
            putExtra("habit", createNewHabit())
            putExtra("habit_state", habitState)
        }

        if (habitState == HabitState.Edited) {
            i.putExtra("position", intent.getIntExtra("position", 0))
        }
        setResult(RESULT_OK, i)
        finish()
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
        if (binding.typeButtonsRadiogroup.checkedRadioButtonId == -1) {
            //binding.typeButtonsRadiogroup.error = "Type is required!"
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
                findViewById<RadioButton>(
                    binding.typeButtonsRadiogroup.checkedRadioButtonId
                )
            )],
            periodicity = TimeInterval(
                binding.timesAmountInput.text.toString().trim().toInt(),
                binding.intervalSpinner.selectedItem as Interval
            ),
            color = Color.BLACK
        )
    }
}