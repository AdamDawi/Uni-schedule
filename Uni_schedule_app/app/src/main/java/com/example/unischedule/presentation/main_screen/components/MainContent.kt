package com.example.unischedule.presentation.main_screen.components

import android.icu.util.Calendar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.TabPosition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.example.unischedule.presentation.main_screen.MainState
import com.example.unischedule.presentation.main_screen.MainViewModel
import com.example.unischedule.ui.theme.BackgroundColor
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

val HOURS_SIZE = 70.dp
const val NUMBER_OF_HOURS = 14 //8:00 - 21:00
val PADDING_TO_CENTER_FIXED_HOURS = 9.dp //vertically center
val RED_LINE_FOR_CURRENT_TIME_HEIGHT = 9.dp
@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    daysList: List<String>,
    state: MainState,
    viewModel: MainViewModel
) {
    val pagerState = rememberPagerState()
    val indicator = @Composable { tabPositions: List<TabPosition> ->
        AnimatedTabIndicator(tabPositions, pagerState)
    }
    val currentDayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
    var isPagerInitialized by remember { mutableStateOf(false) }
    val lazyListState = rememberLazyListState()

    LaunchedEffect(currentDayOfWeek) {
        if (currentDayOfWeek != 1 && currentDayOfWeek != 7 && !isPagerInitialized) {
            isPagerInitialized = true
            pagerState.scrollToPage(currentDayOfWeek - 2)
            //when time is above 17:00 scroll to next part of the plan
            if(viewModel.getCurrentTimeInMinutes()>=960)
                lazyListState.scrollToItem(0, scrollOffset = ((viewModel.getCurrentTimeInMinutes()) / 60f * HOURS_SIZE).value.toInt())
            else lazyListState.scrollToItem(0)
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        ScrollableDayTabs(
            pagerState = pagerState,
            indicator = indicator,
            daysList = daysList
        )
        LazyColumn(
            modifier = Modifier
                .background(BackgroundColor)
                .fillMaxSize(),
            state = lazyListState
        ) {
            item {
                Row(
                    modifier = Modifier
                ) {
                    // Fixed component on the left
                    FixedHoursComponent(
                        modifier = Modifier
                            .padding(start = 8.dp, end = 8.dp)
                    )
                    // Pager content takes up the remaining space
                    HorizontalPager(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = PADDING_TO_CENTER_FIXED_HOURS),
                        count = daysList.size,
                        state = pagerState
                    ) { page ->
                        when (page) {
                            0 -> {
                                DailyScheduleLayout(courseList = state.mondayCourses, viewModel = viewModel) {
                                    Row(modifier = Modifier
                                        .alpha(if(currentDayOfWeek==2) 1f else 0f),
                                        verticalAlignment = Alignment.CenterVertically) {
                                        Box(modifier = Modifier
                                            .size(RED_LINE_FOR_CURRENT_TIME_HEIGHT)
                                            .clip(CircleShape)
                                            .background(Color.Red)
                                        )
                                        Divider(
                                            modifier = Modifier
                                                .height(2.dp),
                                            color = Color.Red
                                        )
                                    }
                                    for (i in state.mondayCourses)
                                        CourseCard(course = i)
                                    for (i in 0 until NUMBER_OF_HOURS)
                                        Divider()
                                }
                            }
                            1 -> {
                                DailyScheduleLayout(courseList = state.tuesdayCourses, viewModel = viewModel) {
                                    Row(modifier = Modifier
                                        .alpha(if(currentDayOfWeek==3) 1f else 0f),
                                        verticalAlignment = Alignment.CenterVertically) {
                                        Box(modifier = Modifier
                                            .size(RED_LINE_FOR_CURRENT_TIME_HEIGHT)
                                            .clip(CircleShape)
                                            .background(Color.Red)
                                        )
                                        Divider(
                                            modifier = Modifier
                                                .height(2.dp),
                                            color = Color.Red
                                        )
                                    }
                                    for (i in state.tuesdayCourses)
                                        CourseCard(course = i)
                                    for(i in 0 until NUMBER_OF_HOURS)
                                        Divider()

                                }
                            }
                            2 -> {
                                DailyScheduleLayout(courseList = state.wednesdayCourses, viewModel = viewModel) {
                                    Row(modifier = Modifier
                                        .alpha(if(currentDayOfWeek==4) 1f else 0f),
                                        verticalAlignment = Alignment.CenterVertically) {
                                        Box(modifier = Modifier
                                            .size(RED_LINE_FOR_CURRENT_TIME_HEIGHT)
                                            .clip(CircleShape)
                                            .background(Color.Red)
                                        )
                                        Divider(
                                            modifier = Modifier
                                                .height(2.dp),
                                            color = Color.Red
                                        )
                                    }
                                    for (i in state.wednesdayCourses)
                                        CourseCard(course = i)
                                    for(i in 0 until NUMBER_OF_HOURS)
                                        Divider()

                                }
                            }
                            3 -> {
                                DailyScheduleLayout(courseList = state.thursdayCourses, viewModel = viewModel) {
                                    Row(modifier = Modifier
                                        .alpha(if(currentDayOfWeek==5) 1f else 0f),
                                        verticalAlignment = Alignment.CenterVertically) {
                                        Box(modifier = Modifier
                                            .size(RED_LINE_FOR_CURRENT_TIME_HEIGHT)
                                            .clip(CircleShape)
                                            .background(Color.Red)
                                        )
                                        Divider(
                                            modifier = Modifier
                                                .height(2.dp),
                                            color = Color.Red
                                        )
                                    }
                                    for (i in state.thursdayCourses)
                                        CourseCard(course = i)
                                    for(i in 0 until NUMBER_OF_HOURS)
                                        Divider()

                                }
                            }
                            4 -> {
                                DailyScheduleLayout(courseList = state.fridayCourses, viewModel = viewModel) {
                                    Row(modifier = Modifier
                                        .alpha(if(currentDayOfWeek==6) 1f else 0f),
                                        verticalAlignment = Alignment.CenterVertically) {
                                        Box(modifier = Modifier
                                            .size(RED_LINE_FOR_CURRENT_TIME_HEIGHT)
                                            .clip(CircleShape)
                                            .background(Color.Red)
                                        )
                                        Divider(
                                            modifier = Modifier
                                                .height(2.dp),
                                            color = Color.Red
                                        )
                                    }
                                    for (i in state.fridayCourses)
                                        CourseCard(course = i)
                                    for(i in 0 until NUMBER_OF_HOURS)
                                        Divider()

                                }
                            }
                        }
                    }
                }
            }
        }
    }
}





