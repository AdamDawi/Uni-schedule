package com.example.unischedule.presentation.main_screen.components

import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.example.unischedule.domain.model.Course

@Composable
fun DailyScheduleLayout(
    modifier: Modifier = Modifier,
    courseList: List<Course>,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier.height(HOURS_SIZE* NUMBER_OF_HOURS),
        content = content
    ) { items, constraints ->

        //measurements
        val itemsPlaceable = items.mapIndexed {i , it ->
            if(i<courseList.size){
                val elHeight = (courseList[i].endTime-courseList[i].startTime)/ 60f
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
                if(i<NUMBER_OF_HOURS) {
                    el.placeRelative(0, divY)
                    divY+= HOURS_SIZE.roundToPx()
                }else{
                    val elY = ((courseListPlaceable[i-NUMBER_OF_HOURS].startTime - 480) / 60f * HOURS_SIZE)
                    el.placeRelative(0, elY.roundToPx())
                }
            }
        }
    }
}