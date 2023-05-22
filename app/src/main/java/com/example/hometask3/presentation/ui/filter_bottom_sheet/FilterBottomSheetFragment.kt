package com.example.hometask3.presentation.ui.filter_bottom_sheet

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import com.example.domain.models.entities.HabitType
import com.example.domain.models.filters.HabitFilter
import com.example.hometask3.MainApplication
import com.example.hometask3.databinding.FragmentBottomSheetBinding
import com.example.hometask3.presentation.ui.habits_list.HabitListViewModel
import com.example.hometask3.presentation.ui.habits_list.adapter.HabitListViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

class FilterBottomSheetFragment : BottomSheetDialogFragment() {

    private val binding by lazy { FragmentBottomSheetBinding.inflate(layoutInflater) }

    @Inject
    lateinit var habitListViewModelFactory: HabitListViewModelFactory
    private lateinit var viewModel: HabitListViewModel
    private val filter by lazy { HabitFilter() }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MainApplication)
            .appComponent
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(requireActivity(), habitListViewModelFactory)[HabitListViewModel::class.java]
        with(binding) {
            goodTypeButton.setOnClickListener {
                viewModel.triggerFilter(filter.copy(type = HabitType.GOOD))
            }

            badTypeButton.setOnClickListener {
                viewModel.triggerFilter(filter.copy(type = HabitType.BAD))
            }

            allTypesButton.setOnClickListener {
                viewModel.triggerFilter(filter.copy(type = null))
            }

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(query: String?): Boolean {
                    viewModel.triggerFilter(filter.copy(query = query.toString()))
                    return true
                }

                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }
            })
        }

//        BottomSheetBehavior.from(binding.bscLayout).apply {
//                peekHeight = 200
//                maxHeight = 600
//                state = BottomSheetBehavior.STATE_COLLAPSED
//        }

        return binding.root
    }
}