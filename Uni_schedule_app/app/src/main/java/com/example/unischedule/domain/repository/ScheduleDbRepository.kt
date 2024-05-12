package com.example.unischedule.domain.repository

import com.example.unischedule.domain.model.CourseEntity
import kotlinx.coroutines.flow.Flow

interface ScheduleDbRepository {
    suspend fun addCourses(courses: List<CourseEntity>)

    fun getAllCourses(): Flow<List<CourseEntity>>
}