package com.example.unischedule.presentation.main_screen.components

import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.example.unischedule.domain.model.Course
import com.example.unischedule.presentation.main_screen.MainViewModel

@Composable
fun DailyScheduleLayout(
    modifier: Modifier = Modifier,
    courseList: List<Course>,
    viewModel: MainViewModel,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier.height(HOURS_SIZE* NUMBER_OF_HOURS),
        content = content
    ) { //items = [ Red line for current time ], [ Courses ], [ Dividers ]
      items, constraints ->

        //measurements
        val itemsPlaceable = items.mapIndexed {i , it ->
            if(i==0){
                it.measure(Constraints.fixed(constraints.maxWidth, 15.dp.roundToPx()))
            }
            else if(i<courseList.size+1){
                val elHeight = (courseList[i-1].endTime-courseList[i-1].startTime)/ 60f
                it.measure(Constraints.fixed(constraints.maxWidth, (elHeight* HOURS_SIZE).roundToPx()))
            }
            else{
                it.measure(Constraints.fixed(constraints.maxWidth, 2.dp.roundToPx()))
            }
        }
        //placement
        layout(
            width = constraints.maxWidth,
            height = constraints.maxHeight
        ) {
            val courseListPlaceable = courseList.reversed()
            var divY = 0

            itemsPlaceable.reversed().forEachIndexed { i, el ->
                //place dividers first because we want they on background
                if(i==itemsPlaceable.size-1){
                    val elY = ((viewModel.getCurrentTimeInMinutes() - 480) / 60f * HOURS_SIZE)
                    el.placeRelative(0, elY.roundToPx()-RED_LINE_FOR_CURRENT_TIME_HEIGHT.roundToPx())
                }
                else if(i<NUMBER_OF_HOURS) {
                    el.placeRelative(0, divY)
                    divY+= HOURS_SIZE.roundToPx()
                }
                else
                {
                    val elY = ((courseListPlaceable[i-NUMBER_OF_HOURS].startTime - 480) / 60f * HOURS_SIZE)
                    el.placeRelative(0, elY.roundToPx())
                }
            }
        }
    }
}