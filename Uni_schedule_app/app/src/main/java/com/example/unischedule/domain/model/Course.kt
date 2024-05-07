package com.example.unischedule.domain.model

import androidx.compose.ui.graphics.Color

data class Course(
    val name: String = "",
    val startTime: Int = 0, //time in minutes
    val endTime: Int = 0, //time in minutes
    val color: Color = Color.Black,
    val dayOfWeek: String = "",
    val leader: String = "",
    val room: String = "",
    val type: String = ""
)

fun Course.formattedTime(): String{
    return "${startTime/60}:${if(startTime%60==0) "00" else startTime%60} - ${endTime/60}:${if(endTime%60==0) "00" else endTime%60}"
}