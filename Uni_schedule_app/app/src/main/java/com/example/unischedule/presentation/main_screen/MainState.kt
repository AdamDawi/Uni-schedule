package com.example.unischedule.presentation.main_screen

import com.example.unischedule.domain.model.Course

data class MainState(
    val isLoading: Boolean = false,
    val courses: List<Course> = emptyList(),
    val errorMessage: String = "",
)