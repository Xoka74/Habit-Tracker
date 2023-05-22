package com.example.hometask3.presentation.ui.habits_list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.hometask3.MainApplication
import com.example.hometask3.R
import com.example.hometask3.databinding.FragmentHabitListBinding
import com.example.hometask3.presentation.ui.filter_bottom_sheet.FilterBottomSheetFragment
import com.example.hometask3.presentation.ui.habit_details.HabitDetailsFragment
import com.example.hometask3.presentation.ui.habits_list.adapter.HabitAdapter
import com.example.hometask3.presentation.ui.habits_list.adapter.HabitListViewModelFactory
import com.example.hometask3.utils.runOnUiThread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class HabitListFragment : Fragment() {
    private val binding by lazy { FragmentHabitListBinding.inflate(layoutInflater) }

    @Inject
    lateinit var habitListViewModelFactory: HabitListViewModelFactory
    private lateinit var viewModel: HabitListViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MainApplication)
            .appComponent
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding.addHabitButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_habitDetailsFragment)
        }
        viewModel =
            ViewModelProvider(requireActivity(), habitListViewModelFactory)[HabitListViewModel::class.java]
        val habitAdapter = HabitAdapter { onClickEditHabit(it) }
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

    private fun onClickEditHabit(habit: com.example.domain.models.entities.Habit) {
        val args = Bundle().apply { putString(HabitDetailsFragment.habitIdTag, habit.uid) }
        findNavController().navigate(R.id.action_mainFragment_to_habitDetailsFragment, args)
    }
}