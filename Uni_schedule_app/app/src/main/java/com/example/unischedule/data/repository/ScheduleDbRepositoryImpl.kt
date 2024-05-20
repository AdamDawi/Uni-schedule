package com.example.unischedule.data.repository

import com.example.unischedule.data.local.dao.ScheduleDao
import com.example.unischedule.domain.model.CourseEntity
import com.example.unischedule.domain.repository.ScheduleDbRepository
import kotlinx.coroutines.flow.Flow

class ScheduleDbRepositoryImpl(
    private val scheduleDao: ScheduleDao
): ScheduleDbRepository {
    override suspend fun addCourses(courses: List<CourseEntity>) {
        scheduleDao.addCourses(courses)
    }

    override fun getAllCourses(): Flow<List<CourseEntity>> {
        return scheduleDao.getAllCourses()
    }

    override suspend fun deleteCourses(courses: List<CourseEntity>) {
        scheduleDao.deleteCourses(courses)
    }
}