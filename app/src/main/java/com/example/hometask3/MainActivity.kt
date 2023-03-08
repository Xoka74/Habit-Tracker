package com.example.hometask3

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.hometask3.HabitState.*
import com.example.hometask3.databinding.ActivityMainBinding
import kotlin.time.Duration

val list = (0..15).map {
    Habit(
        name = "name$it",
        description = "String",
        priority = Priority.High,
        type = HabitType.Work,
        completionAmount = 10,
        periodicity = Duration.ZERO,
        color = Color.BLACK,
    )
}.toMutableList()

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var habitAdapter: HabitAdapter
    private val habits: MutableList<Habit> = list

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecycler()
    }

    private fun setupRecycler() {
        habitAdapter = HabitAdapter(habits) { habit, position -> onClickEditHabit(habit, position) }
        binding.recyclerView.adapter = habitAdapter
        //habitAdapter.
        binding.addHabitButton.setOnClickListener { onClickAddHabit() }
    }

    private fun onClickEditHabit(habit: Habit, position: Int) {
        val intent = Intent(this, HabitCreationActivity::class.java).apply {
            putExtra("habit", habit)
            putExtra("position", position)
        }
        openHabitCreationActivityForResult(intent)
    }

    private fun onClickAddHabit() {
        openHabitCreationActivityForResult(Intent(this, HabitCreationActivity::class.java))
    }

    private fun openHabitCreationActivityForResult(intent: Intent) {
        resultLauncher.launch(intent)
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent = result.data!!
                val habit = data.serializable<Habit>("habit")
                Log.e("HABIT_STATE", data.serializable<HabitState>("habit_state").toString())
                when (data.serializable<HabitState>("habit_state")) {
                    Created -> {
                        habits.add(habit!!)
                        habitAdapter.notifyItemInserted(habits.size)
                    }
                    Edited -> {
                        val index = data.getIntExtra("position", 0)
                        habits[index] = habit!!
                        habitAdapter.notifyItemChanged(index)
                    }
                    else -> {
                        val index = data.getIntExtra("position", 0)
                        Log.e("POSITION", data.getIntExtra("position", 0).toString())
                        Log.e("SIZE", habits.size.toString())
                        habits.removeAt(index)
                        habitAdapter.notifyItemRemoved(index)
                    }
                }
            }
        }
}

