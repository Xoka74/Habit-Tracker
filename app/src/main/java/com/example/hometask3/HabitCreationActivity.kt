package com.example.hometask3

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hometask3.databinding.ActivityHabitCreationBinding
import kotlin.time.Duration


class HabitCreationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHabitCreationBinding
    private lateinit var prioritySpinnerAdapter: ArrayAdapter<Priority>
    private var habitState = HabitState.Created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHabitCreationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.typeButtonsRadiogroup.setOnClickListener { onHabitTypeRadioButtonClicked() }
        binding.completeCreationButton.setOnClickListener { onCompleteHabitCreationButton() }

        setupPrioritySpinnerAdapter()

        val habit = intent.serializable<Habit>("habit")
        if (habit != null) {
            enableEditModeFor(habit)
            addDeleteButtonFor(habit)
        }
    }

    private fun addDeleteButtonFor(habit: Habit) {
        binding.deleteButton.visibility = View.VISIBLE
        binding.deleteButton.setOnClickListener {
            onClickDeleteExistingHabit(
                habit,
                intent.getIntExtra("position", 0)
            )
        }
    }

    private fun onClickDeleteExistingHabit(habit: Habit, position: Int) {
        habitState = HabitState.Deleted
        val i = Intent(this, MainActivity::class.java).apply {
            putExtra("habit_state", habitState)
            putExtra("position", intent.getIntExtra("position", 0))
        }

        setResult(RESULT_OK, i)
        finish()
    }

    private fun enableEditModeFor(habit: Habit) {
        habitState = HabitState.Edited
        binding.habitNameInput.setText(habit.name)
        binding.habitDescriptionInput.setText(habit.description)
        binding.prioritySpinner.setSelection(prioritySpinnerAdapter.getPosition(habit.priority))
        //            binding.typeButtonsRadiogroup.

        binding.habitCompletionAmountInput.setText(habit.completionAmount.toString())
        //.setText(habit.completionAmount)
    }

    private fun setupPrioritySpinnerAdapter() {
        val prioritySpinnerAdapter: ArrayAdapter<Priority> = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            Priority.values()
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        binding.prioritySpinner.onItemSelectedListener = HabitPriorityAdapter()
        this.prioritySpinnerAdapter = prioritySpinnerAdapter
        binding.prioritySpinner.adapter = prioritySpinnerAdapter
    }

    private fun onHabitTypeRadioButtonClicked() {

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
            Toast.makeText(this, "Name of habit is required!", Toast.LENGTH_SHORT)
                .show()
            return false
        }

        if (binding.habitCompletionAmountInput.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Completion amount of habit is required!", Toast.LENGTH_SHORT)
                .show()
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
            type = HabitType.Work,
            periodicity = Duration.ZERO,
            color = Color.BLACK
//            periodicity=bundle.getString("periodicity")!!,
//            color=bundle.getString("color")!!,
//            priority=bundle.getString("priority")!!,
//            type=bundle.getString("type")!!,,
        )
        //val checkedHabitTypeId = binding.typeButtonsRadiogroup.checkedRadioButtonId
        //bundle.putSerializable("priority", binding.typeButtonsRadiogroup.findViewById<RadioButton>(checkedHabitTypeId))
    }
}