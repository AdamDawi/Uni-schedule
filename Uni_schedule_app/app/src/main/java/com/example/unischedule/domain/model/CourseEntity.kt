package com.example.unischedule.domain.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.unischedule.common.Constants

@Entity(tableName = Constants.COURSES_TABLE_NAME)
data class CourseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "",
    val startTime: Int = 0, //time in minutes
    val endTime: Int = 0, //time in minutes
    val color: Int = Color.Black.toArgb(),
    val dayOfWeek: String = "",
    val leader: String = "",
    val room: String = "",
    val type: String = ""
)
