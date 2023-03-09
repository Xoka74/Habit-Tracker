package com.example.hometask3

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.hometask3.HabitState.*
import com.example.hometask3.databinding.ActivityMainBinding

val list = (0..15).map {
    Habit(
        name = "name$it",
        description = "String",
        priority = Priority.High,
        type = HabitType.Bad,
        completionAmount = 10,
        periodicity = TimeInterval(12, Interval.Day),
        color = Color.RED
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
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        habitAdapter = HabitAdapter(habits,
            onItemClick = { habit, position -> onClickEditHabit(habit, position) },
            //onItemLongClick = { position -> onLongClickItem(position)
        )
        binding.recyclerView.adapter = habitAdapter
        binding.addHabitButton.setOnClickListener { onClickAddHabit() }
    }

    private fun onClickEditHabit(habit: Habit, position: Int) {
        val intent = Intent(this, HabitCreationActivity::class.java).apply {
            putExtra("habit", habit)
            putExtra("position", position)
        }
        openHabitCreationActivityForResult(intent)
    }

    private fun onLongClickItem(position: Int): Boolean {
        //removeRecyclerItemAtIndex(position, habits, habitAdapter)
        return true
    }

    private fun onClickAddHabit() {
        openHabitCreationActivityForResult(Intent(this, HabitCreationActivity::class.java))
    }

    private fun openHabitCreationActivityForResult(intent: Intent) {
        resultLauncher.launch(intent)
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            onReceiveResult(result)
        }

    private fun onReceiveResult(result: ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent = result.data ?: return
            val habit = data.parcelable<Habit>("habit")
            when (data.serializable<HabitState>("habit_state")) {
                Created -> {
                    habits.add(habit ?: return)
                    habitAdapter.notifyItemInserted(habits.size)
                }
                Edited -> {
                    val index = data.getIntExtra("position", 0)
                    habits[index] = habit ?: return
                    habitAdapter.notifyItemChanged(index)
                }
                Deleted -> {
                    val index = data.getIntExtra("position", 0)
                    removeRecyclerItemAtIndex(index, habits, habitAdapter)
                }
                else -> {}
            }
        }
    }

    private fun removeRecyclerItemAtIndex(
        index: Int,
        list: MutableList<Habit>,
        adapter: HabitAdapter
    ) {
        list.removeAt(index)
        adapter.notifyItemRemoved(index)
        adapter.notifyItemRangeChanged(index, list.size)
    }
}

