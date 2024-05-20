package com.example.unischedule.data.repository

import com.example.unischedule.common.Constants
import com.example.unischedule.data.remote.ScheduleApi
import com.example.unischedule.data.remote.dto.CoursesDto
import com.example.unischedule.domain.repository.ScheduleApiRepository
import javax.inject.Inject

class ScheduleApiRepositoryImpl @Inject constructor(
    private val api: ScheduleApi
): ScheduleApiRepository {
    override suspend fun getCourses(link: String): CoursesDto {
        return api.getCourses(Constants.BASE_URL +link)
    }
}