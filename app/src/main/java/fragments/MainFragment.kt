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
import data.HabitDatabase
import models.Habit
import view_models.HabitListViewModel
import view_models.factories.HabitListViewModelFactory


class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var habitListViewModel: HabitListViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater)

        habitListViewModel = ViewModelProvider(
            requireActivity(),
            HabitListViewModelFactory(HabitDatabase.getInstance(requireContext()).getHabitDao())
        )[HabitListViewModel::class.java]

        binding.addHabitButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_habitDetailsFragment)
        }

        habitListViewModel.filter.observe(viewLifecycleOwner){
            habitListViewModel.searchByName(it)
        }

        val habitAdapter = HabitAdapter { habit -> onClickEditHabit(habit) }

        habitListViewModel.habits.observe(viewLifecycleOwner) { habits ->
            habitAdapter.updateList(habits)
        }
        HabitDatabase.getInstance(requireContext()).getHabitDao().searchByName("hs").observe(viewLifecycleOwner){

        }

        binding.recyclerView.adapter = habitAdapter
        setupBottomSheetFragment()

        return binding.root
    }

    private fun setupBottomSheetFragment() {
        binding.bottomSheetContainer.searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextChange(query: String?): Boolean {
//                habitListViewModel.searchByName(filter.apply { name = query })
                habitListViewModel.applyName(query ?: "")
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
        })

        BottomSheetBehavior.from(binding.bottomSheetContainer.bscLayout).apply {
            peekHeight = 200
            maxHeight = 600
            state = BottomSheetBehavior.STATE_COLLAPSED
        }

        binding.bottomSheetContainer.orderByAscendingButton.setOnClickListener {
            habitListViewModel.orderByPriority()
        }

        binding.bottomSheetContainer.orderByDescendingButton.setOnClickListener {
            habitListViewModel.orderByPriority()
        }
    }

    private fun onClickEditHabit(habit: Habit) {
        val args = Bundle().apply { putInt(HabitDetailsFragment.habitIdTag, habit.id ?: return) }
        findNavController().navigate(R.id.action_mainFragment_to_habitDetailsFragment, args)
    }
}