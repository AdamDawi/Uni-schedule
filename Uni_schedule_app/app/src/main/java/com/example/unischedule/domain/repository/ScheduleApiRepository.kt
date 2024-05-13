package com.example.unischedule.domain.repository

import com.example.unischedule.data.remote.dto.CoursesDto

interface ScheduleApiRepository {
    suspend fun  getCourses(link: String): CoursesDto
}