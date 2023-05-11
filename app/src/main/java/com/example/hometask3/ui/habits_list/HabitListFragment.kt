package com.example.hometask3.ui.habits_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.hometask3.R
import com.example.hometask3.data.HabitDatabase
import com.example.hometask3.data.data_sources.HabitApi
import com.example.hometask3.data.filters.HabitFilter
import com.example.hometask3.data.models.entities.Habit
import com.example.hometask3.data.models.entities.HabitType
import com.example.hometask3.data.repositories.HabitRepository
import com.example.hometask3.databinding.FragmentHabitListBinding
import com.example.hometask3.ui.habit_details.HabitDetailsFragment
import com.example.hometask3.ui.habits_list.adapter.HabitAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior


class HabitListFragment : Fragment() {
    private val binding by lazy { FragmentHabitListBinding.inflate(layoutInflater) }
    private val filter by lazy { HabitFilter() }
    private val viewModel: HabitListViewModel by viewModels(
        factoryProducer = {
            HabitListViewModelFactory(
                HabitRepository(HabitDatabase.getInstance(requireContext()).getHabitDao()),
                HabitApi()
            )
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding.addHabitButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_habitDetailsFragment)
        }

        viewModel.setFilter(filter)


        val habitAdapter = HabitAdapter { onClickEditHabit(it) }

        binding.recyclerView.adapter = habitAdapter
        setupBottomSheetFragment()


        viewModel.habits.observe(viewLifecycleOwner) {
            if (it != null) {
                activity?.runOnUiThread {
                    habitAdapter.updateList(it)
                }
            }
        }

        return binding.root
    }

    private fun setupBottomSheetFragment() {
        with(binding.bottomSheet) {
            goodTypeButton.setOnClickListener {
                viewModel.setFilter(filter.apply { type = HabitType.GOOD })
            }

            badTypeButton.setOnClickListener {
                viewModel.setFilter(filter.apply { type = HabitType.BAD })
            }

            allTypesButton.setOnClickListener {
                viewModel.setFilter(filter.apply { type = null })
            }

            searchView.setOnQueryTextListener(object :
                SearchView.OnQueryTextListener {
                override fun onQueryTextChange(query: String?): Boolean {
                    viewModel.setFilter(filter.apply {
                        this.query = query.toString()
                    })
                    return true
                }

                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }
            })

            BottomSheetBehavior.from(bscLayout).apply {
                peekHeight = 200
                maxHeight = 600
                state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }
    }

    private fun onClickEditHabit(habit: Habit) {
        val args = Bundle().apply { putString(HabitDetailsFragment.habitIdTag, habit.uid) }
        findNavController().navigate(R.id.action_mainFragment_to_habitDetailsFragment, args)
    }
}