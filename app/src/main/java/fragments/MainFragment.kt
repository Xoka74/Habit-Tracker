package fragments

import adapters.HabitAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.hometask3.R
import com.example.hometask3.databinding.FragmentMainBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import filters.HabitFilter
import models.Habit
import models.HabitType
import view_models.HabitViewModel
import view_models.HabitsListViewModel


class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var habitViewModel: HabitViewModel
    private lateinit var habitsListViewModel: HabitsListViewModel
    private lateinit var filter: HabitFilter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater)

        habitsListViewModel =
            ViewModelProvider(requireActivity())[HabitsListViewModel::class.java]
        habitViewModel =
            ViewModelProvider(requireActivity())[HabitViewModel::class.java]

        habitViewModel.postHabit(null)

        binding.addHabitButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_habitDetailsFragment)
        }
        filter = HabitFilter(null, null, null)
        habitsListViewModel.applyFilters(filter)

        val habitAdapter = HabitAdapter(
            mutableListOf(),
            onItemClick = { habit -> onClickEditHabit(habit) },
        )

        habitsListViewModel.habits.observe(viewLifecycleOwner) { habits ->
            binding.recyclerView.scrollToPosition(0)
            habitAdapter.updateList(habits)
        }

        binding.recyclerView.adapter = habitAdapter
        setupBottomSheetFragment()

        return binding.root
    }

    private fun setupBottomSheetFragment() {
        binding.bottomSheetContainer.searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                habitsListViewModel.applyFilters(filter.apply {
                    name = newText
                })
                return true
            }
        })

        binding.bottomSheetContainer.orderByAscendingButton.setOnClickListener {
            habitsListViewModel.orderByPriority(descending = false)
        }

        binding.bottomSheetContainer.orderByDescendingButton.setOnClickListener {
            habitsListViewModel.orderByPriority(descending = true)
        }

        BottomSheetBehavior.from(binding.bottomSheetContainer.bscLayout).apply {
            peekHeight = 200
            maxHeight = 600
            state = BottomSheetBehavior.STATE_COLLAPSED
        }

        binding.bottomSheetContainer.allTypesButton.setOnClickListener{
            habitsListViewModel.applyFilters(filter.apply {
                type = null
            })
        }

        binding.bottomSheetContainer.goodTypeButton.setOnClickListener{
            habitsListViewModel.applyFilters(filter.apply {
                type = HabitType.Good
            })
        }

        binding.bottomSheetContainer.badTypeButton.setOnClickListener{
            habitsListViewModel.applyFilters(filter.apply {
                type = HabitType.Bad
            })
        }
    }

    private fun onClickEditHabit(habit: Habit) {
        habitViewModel.postHabit(habit)
        findNavController().navigate(R.id.action_mainFragment_to_habitDetailsFragment)
    }
}