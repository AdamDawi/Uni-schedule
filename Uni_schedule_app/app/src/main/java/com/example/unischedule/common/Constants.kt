package com.example.unischedule.common

import android.graphics.Color.rgb
import androidx.compose.ui.graphics.Color
import com.example.unischedule.domain.model.Course


object Constants {
    const val BASE_URL = "https://uni-schedule-rest-api.onrender.com/"
    val FULL_TIME_STUDIES_DAYS_LIST = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday")
    val PART_TIME_STUDIES_DAYS_LIST = listOf("Saturday", "Sunday")
    val DUMMY_LIST_OF_MONDAY = listOf(
        Course("Podstawy techniki cyfrowej, wy", 495, 585, color = Color(rgb(202, 255, 14))),
        Course("Bezpieczeństwo informacji, wy", startTime = 750, endTime = 840, color = Color(rgb(202, 255, 14))),
        Course("Podstawy paradygmatów programowania, lab", startTime = 855, endTime = 945, color = Color(rgb(255, 194, 14))),
        Course("Bezpieczeństwo informacji, lab", startTime = 1005, endTime = 1095, color = Color(rgb(255, 194, 14)))
    )
    val DUMMY_LIST_OF_TUESDAY: List<Course> = listOf(
        Course("Technika mikroprocesorowa - tyg.6-15, lab", startTime = 480, endTime = 615, color = Color(rgb(255, 194, 14))),
        Course("Podstawy techniki cyfrowej - tyg.6-15, lab", startTime = 630, endTime = 765, color = Color(rgb(255, 194, 14)))
    )
    val DUMMY_LIST_OF_WEDNESDAY: List<Course> = listOf(
        Course("Podstawy paradygmatów programowania, wy", startTime = 480, endTime = 570, color = Color(rgb(202, 255, 14))),
        Course("Podstawy aplikacji internetowych, lab", startTime = 585, endTime = 675, color = Color(rgb(255, 194, 14))),
        Course("Wychowanie fizyczne II, ćw", startTime = 750, endTime = 840, color = Color(rgb(14, 255, 70))),
        Course("Język angielski II, lekt", startTime = 855, endTime = 960, color = Color(rgb(255, 87, 252)))
    )
    val DUMMY_LIST_OF_THURSDAY: List<Course> = listOf(
        Course("Podstawy inżynierii oprogramowania, wy", startTime = 495, endTime = 600, color = Color(rgb(202, 255, 14))),
        Course("Technika mikroprocesorowa, wy", startTime = 615, endTime = 720, color = Color(rgb(202, 255, 14))),
        Course("Podstawy inżynierii oprogramowania, lab", startTime = 735, endTime = 825, color = Color(rgb(255, 194, 14))),
        Course("Podstawy grafiki komputerowej, lab", startTime = 825, endTime = 915, color = Color(rgb(255, 194, 14)))
    )
    val DUMMY_LIST_OF_FRIDAY: List<Course> = listOf(
        Course("Podstawy grafiki komputerowej, wy", startTime = 645, endTime = 735, color = Color(rgb(202, 255, 14))),
        Course("Podstawy aplikacji internetowych, wy", startTime = 750, endTime = 840, color = Color(rgb(202, 255, 14))),
        Course("Podstawy inżynierii oprogramowania, lab", startTime = 855, endTime = 960, color = Color(rgb(233, 255, 233))),
    )
}