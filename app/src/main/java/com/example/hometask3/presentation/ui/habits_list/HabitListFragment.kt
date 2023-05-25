package com.example.hometask3.presentation.ui.habits_list

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.domain.models.entities.Habit
import com.example.domain.models.entities.HabitType
import com.example.hometask3.MainApplication
import com.example.hometask3.R
import com.example.hometask3.databinding.FragmentHabitListBinding
import com.example.hometask3.presentation.ui.habit_details.HabitDetailsFragment
import com.example.hometask3.presentation.ui.habits_list.adapter.HabitAdapter
import com.example.hometask3.utils.runOnUiThread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class HabitListFragment : Fragment() {
    private val binding by lazy { FragmentHabitListBinding.inflate(layoutInflater) }

    @Inject
    lateinit var habitListViewModelFactory: HabitListViewModelFactory
    private val viewModel: HabitListViewModel by viewModels(
        factoryProducer = { habitListViewModelFactory }
    )

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MainApplication)
            .appComponent
            .inject(this)
    }

    private fun onClickPerformHabit(habit: Habit) {
        val doneDates = viewModel.performHabit(habit)
        val delta = habit.count - doneDates.size

        val text : String = if (habit.type == HabitType.BAD){
            if(delta < 0) "Хватит это делать"
            else "Можно выполнить ещё $delta раз"
        }
        else{
            if(delta < 0) "You are breathtaking!"
            else "Стоит выполнить ещё $delta раз"
        }
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding.addHabitButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_habitDetailsFragment)
        }

        val habitAdapter = HabitAdapter(listOf(),
                { onClickEditHabit(it) },
                { onClickPerformHabit(it) })

        binding.recyclerView.adapter = habitAdapter

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            viewModel.habits.collect {
                runOnUiThread {
                    habitAdapter.updateList(it)
                }
            }
        }

        return binding.root
    }

    private fun onClickEditHabit(habit: Habit) {
        val args = Bundle().apply { putString(HabitDetailsFragment.habitIdTag, habit.uid) }
        findNavController().navigate(R.id.action_mainFragment_to_habitDetailsFragment, args)
    }
}