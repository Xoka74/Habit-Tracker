package fragments

import adapters.HabitAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.hometask3.R
import com.example.hometask3.databinding.FragmentHabitsViewPagerBinding
import com.example.hometask3.serializable
import com.google.android.material.bottomsheet.BottomSheetBehavior
import filters.HabitFilter
import models.Habit
import models.HabitType
import view_models.HabitsListViewModel
import view_models.HabitViewModel


class HabitsViewPagerFragment : Fragment() {
    private lateinit var binding: FragmentHabitsViewPagerBinding
    private lateinit var habitsListViewModel: HabitsListViewModel
    private lateinit var habitViewModel: HabitViewModel
    private lateinit var filter: HabitFilter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHabitsViewPagerBinding.inflate(layoutInflater)
        val habitType = arguments?.serializable(habitTypeTag) ?: HabitType.Good
        habitsListViewModel =
            ViewModelProvider(requireActivity())[habitType.toString(), HabitsListViewModel::class.java]
        habitViewModel =
            ViewModelProvider(requireActivity())[HabitViewModel::class.java]
        val habitAdapter = HabitAdapter(
            mutableListOf(),
            onItemClick = { habit -> onClickEditHabit(habit) },
        )

        binding.recyclerView.adapter = habitAdapter

        filter = HabitFilter(null, habitType, null)

        habitsListViewModel.applyFilters(filter)

        habitsListViewModel.habits.observe(viewLifecycleOwner) { habits ->
            binding.recyclerView.scrollToPosition(0)
            habitAdapter.updateList(habits)
        }

        binding.bottomSheetContainer.searchView.setOnQueryTextListener(object :
            OnQueryTextListener {
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
            peekHeight = 150
            state = BottomSheetBehavior.STATE_COLLAPSED
        }
        return binding.root
    }

    private fun onClickEditHabit(habit: Habit) {
        habitViewModel.postHabit(habit)
        findNavController().navigate(R.id.action_mainFragment_to_habitDetailsFragment)
    }

    companion object {
        private const val habitTypeTag = "habit_type"
        fun newInstance(habitType: HabitType) = HabitsViewPagerFragment().apply {
            arguments = Bundle().apply {
                putSerializable(habitTypeTag, habitType)
            }
        }
    }
}