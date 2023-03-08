package com.example.hometask3

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.hometask3.databinding.ActivityMainBinding
import kotlin.time.Duration

//val list = (0..5).map {
//    Habit(
//        name = "name$it",
//        description = "String",
//        priority = Priority.High,
//        type = HabitType.Work,
//        completionAmount = 10,
//        periodicity = Duration.ZERO,
//        color = Color.BLACK
//    )
//}

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var habitAdapter: HabitAdapter
    private val habits: MutableList<Habit> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        habitAdapter = HabitAdapter(habits)
        binding.recyclerView.adapter = habitAdapter
        binding.addHabitButton.setOnClickListener { onClickAddHabit() }
    }

    private fun onEditHabitClick(){
        val intent = Intent(this, HabitCreationActivity::class.java).apply {
            TODO("On click listener for recycler view")
        }

        openHabitCreationActivityForResult(intent)
    }


    private fun onClickAddHabit() {
        openHabitCreationActivityForResult(Intent(this, HabitCreationActivity::class.java))
    }

    private fun openHabitCreationActivityForResult(intent : Intent){
        resultLauncher.launch(intent)
    }

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            habits.add(data!!.getSerializableExtra("habit") as Habit)
            habitAdapter.notifyItemInserted(habits.size)
        }
    }
}

