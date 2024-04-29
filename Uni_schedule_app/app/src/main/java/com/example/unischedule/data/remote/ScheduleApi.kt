package com.example.unischedule.data.remote

import com.example.unischedule.data.remote.dto.CoursesDto
import retrofit2.http.GET

interface ScheduleApi {
    @GET("?url=http://planwe.pollub.pl/plan.php?type=0&id=9968&winW=1904&winH=947&loadBG=000000")
    suspend fun getCourses(): CoursesDto
}