package data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import models.Habit
import models.HabitType

@Dao
interface HabitDao {
    @Insert
    fun insert(habit: Habit)

    @Delete
    fun delete(habit: Habit)

    @Update
    fun update(habit: Habit)

    @Query("SELECT * FROM Habit WHERE id=:id")
    fun getById(id: Int): LiveData<Habit>

    @Query("SELECT * FROM Habit")
    fun getAllHabits(): LiveData<List<Habit>>

//    @Query(
//        "SELECT * FROM Habit ORDER BY " +
//                "CASE WHEN :isAsc = 1 THEN priority END ASC, " +
//                "CASE WHEN :isAsc = 0 THEN priority END DESC"
//    )
//    fun getHabitsOrderedByPriority(isAsc: Int?): LiveData<List<Habit>>

    @Query("select * from Habit order by priority asc")
    fun getHabitsOrderedByPriority() : LiveData<List<Habit>>



    @Query("SELECT * FROM Habit WHERE name LIKE :query")
    fun searchByName(query : String) : LiveData<List<Habit>>


    @Query("SELECT * FROM Habit WHERE type=:type")
    fun getAllWithType(type: HabitType): LiveData<List<Habit>>
}