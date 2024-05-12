package com.example.unischedule.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.unischedule.domain.model.CourseEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ScheduleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addCourses(mealEntity: List<CourseEntity>)

    @Query("select * from `schedule_table`")
    abstract fun getAllCourses(): Flow<List<CourseEntity>>
}