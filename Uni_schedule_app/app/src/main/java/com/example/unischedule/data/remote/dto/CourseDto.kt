package com.example.unischedule.data.remote.dto

import android.graphics.Color.rgb
import androidx.compose.ui.graphics.Color
import com.example.unischedule.domain.model.Course

data class CourseDto(
    val color: List<Int>,
    val dayOfWeek: String,
    val endTime: Int,
    val leader: String,
    val name: String,
    val room: String,
    val startTime: Int,
    val type: String
)

fun CourseDto.toCourse(): Course{
    return Course(
        name = name,
        startTime = startTime,
        endTime = endTime,
        color = Color(rgb(color[0], color[1], color[2])),
        dayOfWeek = dayOfWeek,
        leader = leader,
        room = room,
        type = type
    )
}