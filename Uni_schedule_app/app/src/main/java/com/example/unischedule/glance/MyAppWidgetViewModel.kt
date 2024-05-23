package com.example.unischedule.glance

import androidx.lifecycle.ViewModel
import com.example.unischedule.domain.model.CourseEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MyAppWidgetViewModel : ViewModel() {
    private val _courses = MutableStateFlow<List<CourseEntity>>(emptyList())
    val courses: StateFlow<List<CourseEntity>> = _courses.asStateFlow()
}