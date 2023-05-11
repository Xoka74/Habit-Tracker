package com.example.hometask3.ui.habits_list

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hometask3.data.data_sources.HabitApi
import com.example.hometask3.data.data_sources.generics.ApiResult
import com.example.hometask3.data.filters.HabitFilter
import com.example.hometask3.data.models.entities.Habit
import com.example.hometask3.data.repositories.HabitRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HabitListViewModel(
    private val habitRepository: HabitRepository,
    private val habitApi: HabitApi,
    app: Application
) : AndroidViewModel(app) {

    private var roomHabits: LiveData<List<Habit>>? = null

    init {
        viewModelScope.launch(Dispatchers.IO) {
            roomHabits = habitRepository.getAllHabits()
            fetchHabits()
        }
    }
    private val mutableHabits = MutableLiveData<List<Habit>?>(listOf())
    private suspend fun fetchHabits(){
        while (true) {
            val response = habitApi.getHabits()
            if (response is ApiResult.Success) {
                mutableHabits.postValue(response.data)
                break
            } else {
                mutableHabits.postValue(roomHabits?.value ?: listOf())
            }
            delay(1000)
        }
    }

    val habits: LiveData<List<Habit>?>
        get() = mutableHabits

    private val mutableFilter = MutableStateFlow(HabitFilter())

    private fun synchronize() {
        viewModelScope.launch(Dispatchers.IO) {
            roomHabits?.value?.forEach {
                it.isSynced = true
                habitRepository.update(it)
            }
        }
    }

    fun setFilter(filter: HabitFilter) {
        mutableFilter.value = filter
    }
}
