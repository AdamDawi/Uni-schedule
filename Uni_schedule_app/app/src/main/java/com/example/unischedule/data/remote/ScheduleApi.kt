package com.example.unischedule.data.remote

import com.example.unischedule.data.remote.dto.CoursesDto
import retrofit2.http.GET
import retrofit2.http.Url

interface ScheduleApi {
    @GET
    suspend fun getCourses(@Url url: String): CoursesDto
}