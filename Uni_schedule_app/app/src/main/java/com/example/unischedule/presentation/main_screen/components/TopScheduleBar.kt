package com.example.unischedule.presentation.main_screen.components

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopScheduleBar() {
    CenterAlignedTopAppBar(title = { Text(
        text = "Schedule",
        fontWeight = FontWeight.Bold
    ) })
}