package com.example.unischedule.presentation.main_screen.components

import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.example.unischedule.domain.model.Course
import com.example.unischedule.domain.model.getDurationInHours
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

        var redLineItem: Measurable? = null
        val courseItems = mutableListOf<Measurable>()
        val dividerItems = mutableListOf<Measurable>()

        items.forEachIndexed { i, item ->
            if(i == 0) redLineItem = item
            else if(i<courseList.size+1) courseItems.add(item)
            else dividerItems.add(item)
        }
        //measurements
        //redLine
        val redLineItemPlaceable = redLineItem?.measure(Constraints.fixed(constraints.maxWidth, 15.dp.roundToPx()))
        //courses
        val courseItemsPlaceable = mutableListOf<Placeable>()
        courseItems.forEachIndexed{ i, course ->
            val elHeight = courseList[i].getDurationInHours()
            courseItemsPlaceable.add(course.measure(Constraints.fixed(constraints.maxWidth, (elHeight* HOURS_SIZE).roundToPx())))
        }
        //dividers
        val dividerItemsPlaceable = mutableListOf<Placeable>()
        dividerItems.forEach{
            dividerItemsPlaceable.add(it.measure(Constraints.fixed(constraints.maxWidth, 2.dp.roundToPx())))
        }

        //placement
        layout(
            width = constraints.maxWidth,
            height = constraints.maxHeight
        ) {
            //dividers
            var divY = 0
            dividerItemsPlaceable.forEach {
                it.placeRelative(0, divY)
                divY+= HOURS_SIZE.roundToPx()
            }
            //courses
            courseItemsPlaceable.forEachIndexed { i, course ->
                val elY = ((courseList[i].startTime - START_HOUR_OF_DAY_IN_MINUTES) / 60f * HOURS_SIZE)
                course.placeRelative(0, elY.roundToPx())
            }
            //redLine
            val redLineItemY = ((viewModel.getCurrentTimeInMinutes() - START_HOUR_OF_DAY_IN_MINUTES) / 60f * HOURS_SIZE)
            redLineItemPlaceable?.placeRelative(0, redLineItemY.roundToPx()-RED_LINE_FOR_CURRENT_TIME_HEIGHT.roundToPx())
        }
    }
}