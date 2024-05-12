package com.example.unischedule.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.unischedule.data.local.dao.ScheduleDao
import com.example.unischedule.domain.model.CourseEntity

@Database(
    entities = [CourseEntity::class],
    version = 1,
    exportSchema = false
)

abstract class ScheduleDatabase: RoomDatabase(){
    abstract val scheduleDao: ScheduleDao
}