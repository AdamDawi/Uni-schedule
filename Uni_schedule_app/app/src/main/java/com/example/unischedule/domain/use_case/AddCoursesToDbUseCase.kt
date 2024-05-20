package com.example.unischedule.domain.use_case

import com.example.unischedule.domain.model.CourseEntity
import com.example.unischedule.domain.repository.ScheduleDbRepository
import javax.inject.Inject

class AddCoursesToDbUseCase@Inject constructor(
    private val repository: ScheduleDbRepository
) {
    suspend operator fun invoke(courses: List<CourseEntity>) {
        repository.addCourses(courses)
    }
}