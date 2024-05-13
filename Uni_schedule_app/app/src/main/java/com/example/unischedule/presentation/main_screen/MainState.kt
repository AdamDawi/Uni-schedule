package com.example.unischedule.presentation.main_screen

import com.example.unischedule.domain.model.Course
import com.example.unischedule.domain.model.CourseEntity

data class MainState(
    val isLoading: Boolean = false,
    val allCourses: List<CourseEntity> = emptyList(),
    val mondayCourses: List<Course> = emptyList(),
    val tuesdayCourses: List<Course> = emptyList(),
    val wednesdayCourses: List<Course> = emptyList(),
    val thursdayCourses: List<Course> = emptyList(),
    val fridayCourses: List<Course> = emptyList(),
    val saturdayCourses: List<Course> = emptyList(),
    val sundayCourses: List<Course> = emptyList(),
    val errorMessage: String = "",
    val linkToSchedule: String = ""
)