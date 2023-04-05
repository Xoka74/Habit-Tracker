package fragments

import adapters.HabitAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.hometask3.R
import com.example.hometask3.databinding.FragmentHabitsViewPagerBinding
import com.example.hometask3.serializable
import com.google.android.material.bottomsheet.BottomSheetBehavior
import models.Habit
import models.HabitType
import view_models.SharedHabitViewModel
import view_models.HabitsListViewModel


class HabitsViewPagerFragment : Fragment() {
    private lateinit var binding: FragmentHabitsViewPagerBinding
    private lateinit var habitsListViewModel: HabitsListViewModel
    private lateinit var sharedHabitViewModel: SharedHabitViewModel
    private lateinit var habitAdapter: HabitAdapter
    private lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHabitsViewPagerBinding.inflate(layoutInflater)
        navController = findNavController()
        val habitType = arguments?.serializable(habitTypeTag) ?: HabitType.Good
        habitsListViewModel = ViewModelProvider(requireActivity())[HabitsListViewModel::class.java]
        sharedHabitViewModel = ViewModelProvider(requireActivity())[SharedHabitViewModel::class.java]

        habitAdapter = HabitAdapter(
            mutableListOf(),
            onItemClick = { habit -> onClickEditHabit(habit) },
        )

        binding.recyclerView.adapter = habitAdapter

        habitsListViewModel.habits.observe(viewLifecycleOwner) { habits ->
            val results = habits.filter { it.type == habitType }.toMutableList()

            habitAdapter.updateList(results)
        }

        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                habitAdapter.filter.filter(newText)
                return true
            }
        })

        BottomSheetBehavior.from(binding.bottomSheetContainer).apply {
            peekHeight = 150
            state = BottomSheetBehavior.STATE_COLLAPSED
        }

        return binding.root
    }

    private fun onClickEditHabit(habit: Habit) {

        val args = Bundle().apply {
            putSerializable(HabitDetailsFragment.editModeTag, true)
        }
        sharedHabitViewModel.mutableHabit.postValue(habit)
        navController.navigate(R.id.action_mainFragment_to_habitDetailsFragment, args)
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