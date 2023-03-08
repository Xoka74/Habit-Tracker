package com.example.hometask3

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.hometask3.databinding.ActivityHabitCreationBinding
import kotlin.time.Duration

class HabitCreationActivity : AppCompatActivity(), OnItemSelectedListener {
    private lateinit var binding: ActivityHabitCreationBinding
    private val habitTypes: Array<HabitType> = HabitType.values()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHabitCreationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.typeButtonsRadiogroup.setOnClickListener { onHabitTypeRadioButtonClicked() }
        binding.completeCreationButton.setOnClickListener { onCompleteHabitCreationButton() }


        binding.prioritySpinner.onItemSelectedListener = this

        val prioritySpinnerAdapter: ArrayAdapter<Priority> = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            Priority.values()
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        binding.prioritySpinner.adapter = prioritySpinnerAdapter

        val habit = intent.getSerializableExtra("habit") as Habit?
        if (habit != null) {
            // Edit mode
            binding.habitNameInput.setText(habit.name);
            binding.habitDescriptionInput.setText(habit.description)
            binding.prioritySpinner.setSelection(prioritySpinnerAdapter.getPosition(habit.priority) + 1)
//            binding.typeButtonsRadiogroup.
        }
        // else Adding mode
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
            completionAmount = binding.habitCompletionAmountInput.text.toString().toInt(),
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

    private fun onCompleteHabitCreationButton() {
        if (!dataIsCorrect()) return
        intent.putExtra("habit", createNewHabit())
        setResult(RESULT_OK, Intent(this, MainActivity::class.java))
        finish()
    }

    private fun onHabitTypeRadioButtonClicked() {

    }

    override fun onItemSelected(
        parent: AdapterView<*>?,
        view: View, position: Int,
        id: Long
    ) {
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}
}