package com.example.hometask3.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.hometask3.data.models.entities.Habit
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {
    @Query("SELECT * FROM Habit")
    fun getAllHabits(): Flow<List<Habit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(habit: Habit)
    @Update
    suspend fun update(habit: Habit)

    @Query("SELECT * FROM Habit WHERE uid=:id")
    fun getHabitById(id: String): LiveData<Habit>

    @Query("SELECT * FROM Habit WHERE name LIKE '%' || :query || '%'")
    fun searchHabits(query : String) : Flow<List<Habit>>
}