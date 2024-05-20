package com.example.unischedule.presentation.main_screen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun FixedHoursComponent(
    modifier: Modifier = Modifier
) {
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

@Preview
@Composable
private fun FixedHoursComponentPreview() {
    Surface(color = Color.White) {
        Box(modifier = Modifier.fillMaxSize()) {
            FixedHoursComponent()
        }
    }
}