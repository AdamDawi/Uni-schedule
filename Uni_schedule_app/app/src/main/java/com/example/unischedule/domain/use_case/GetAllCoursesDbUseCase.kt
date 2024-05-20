package com.example.unischedule.domain.use_case

import com.example.unischedule.common.Resource
import com.example.unischedule.domain.model.CourseEntity
import com.example.unischedule.domain.repository.ScheduleDbRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetAllCoursesDbUseCase@Inject constructor(
    private val repository: ScheduleDbRepository
) {
    operator fun invoke(): Flow<Resource<List<CourseEntity>>> = flow {
        try {
            emit(Resource.Loading())
            val courses = repository.getAllCourses().first()
            emit(Resource.Success(courses))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach database."))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error"))
        }
    }
}