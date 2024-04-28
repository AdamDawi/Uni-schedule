package com.example.unischedule.presentation.main_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.unischedule.common.Constants
import com.example.unischedule.ui.theme.BackgroundColor

@Composable
fun DaysSection(
    modifier: Modifier = Modifier,
    daysList: List<String>
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        for(i in daysList.indices){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = daysList[i],
                    color = Color.LightGray,
                    modifier = Modifier.padding(4.dp)
                )
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                ) {
                    Text(modifier = Modifier
                        .align(Alignment.Center),
                        text = (i+1).toString()
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun DaysSectionFullWeekPreview() {
    DaysSection(modifier = Modifier.background(BackgroundColor),
        daysList = Constants.FULL_TIME_STUDIES_DAYS_LIST
    )
}

@Preview
@Composable
private fun DaysSectionTwoDaysPreview() {
    DaysSection(modifier = Modifier.background(BackgroundColor),
        daysList = Constants.PART_TIME_STUDIES_DAYS_LIST
    )
}