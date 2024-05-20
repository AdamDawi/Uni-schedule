package com.example.unischedule.domain.use_case

import com.example.unischedule.common.Resource
import com.example.unischedule.data.remote.dto.toCourse
import com.example.unischedule.domain.model.Course
import com.example.unischedule.domain.repository.ScheduleApiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetAllCoursesApiUseCase@Inject constructor(
    private val repository: ScheduleApiRepository
) {
    operator fun invoke(link: String): Flow<Resource<List<Course>>> = flow{
        try {
            emit(Resource.Loading())
            val courses = repository.getCourses(link).courses.map { it.toCourse() }
            emit(Resource.Success(courses))
        }catch (e: IOException){
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }catch (e: Exception){
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error"))
        }
    }
}