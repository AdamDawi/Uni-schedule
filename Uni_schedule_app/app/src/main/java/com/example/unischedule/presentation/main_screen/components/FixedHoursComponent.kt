package com.example.unischedule.presentation.main_screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun FixedHoursComponent(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxHeight()
    ) {
        for (i in 0 until NUMBER_OF_HOURS) {
            Text(
                text = "${i + 8}:00",
                color = Color.DarkGray,
                modifier = Modifier
                    .heightIn(HOURS_SIZE)
            )
        }
    }
}