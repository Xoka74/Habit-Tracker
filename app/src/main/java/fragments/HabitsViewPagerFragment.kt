package fragments

import adapters.HabitAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.hometask3.R
import com.example.hometask3.databinding.FragmentHabitsViewPagerBinding
import com.example.hometask3.serializable
import data.HabitsProvider
import models.Habit
import models.HabitType


class HabitsViewPagerFragment : Fragment() {
    private lateinit var binding: FragmentHabitsViewPagerBinding
    private lateinit var habits: List<Habit>
    private lateinit var habitAdapter: HabitAdapter
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHabitsViewPagerBinding.inflate(layoutInflater)
        navController = findNavController()

        habits =
            HabitsProvider.getHabitsByType(arguments?.serializable(habitTypeTag) ?: HabitType.Good)

        setupRecyclerView()

        return binding.root
    }

    private fun setupRecyclerView() {
        habitAdapter = HabitAdapter(
            habits,
            onItemClick = { habit -> onClickEditHabit(habit) },
        )
        binding.recyclerView.adapter = habitAdapter
        habitAdapter.notifyDataSetChanged()
    }

    private fun onClickEditHabit(habit: Habit) {

        val args = Bundle().apply {
            putParcelable(HabitDetailsFragment.habitTag, habit)
        }

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