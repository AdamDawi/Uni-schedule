package com.example.unischedule.common

import android.graphics.Color.rgb
import androidx.compose.ui.graphics.Color
import com.example.unischedule.domain.model.Course


object Constants {
    const val BASE_URL = "https://uni-schedule-rest-api.onrender.com/?url="
    val FULL_TIME_STUDIES_DAYS_LIST = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday")
    val PART_TIME_STUDIES_DAYS_LIST = listOf("Saturday", "Sunday")
    val DUMMY_LIST_OF_MONDAY = listOf(
        Course("Podstawy techniki cyfrowej, wy", 495, 585, color = Color(rgb(202, 255, 14))),
        Course("Bezpieczeństwo informacji, wy", startTime = 750, endTime = 840, color = Color(rgb(202, 255, 14))),
        Course("Podstawy paradygmatów programowania, lab", startTime = 855, endTime = 945, color = Color(rgb(255, 194, 14))),
        Course("Bezpieczeństwo informacji, lab", startTime = 1005, endTime = 1095, color = Color(rgb(255, 194, 14)))
    )
    const val COURSES_TABLE_NAME = "schedule_table"
    const val LINK_PLACEHOLDER = "http://planwe.pollub.pl/plan.php?type=example"
}

enum class DayOfWeek {
    SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY
}

fun Color.darkerColor(): Color{
    return Color(
        rgb(
            this.red * 0.9f,
            this.green * 0.9f,
            this.blue * 0.9f
        )
    )
}