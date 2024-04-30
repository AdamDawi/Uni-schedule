package com.example.unischedule.presentation.main_screen.components

import android.icu.util.Calendar
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.TabPosition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.unischedule.presentation.main_screen.MainState
import com.example.unischedule.ui.theme.BackgroundColor
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

val HOURS_SIZE = 70.dp
const val NUMBER_OF_HOURS = 14
val PADDING_TO_CENTER_FIXED_HOURS = 9.dp
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    daysList: List<String>,
    state: MainState
) {
    val pagerState = rememberPagerState()
    val indicator = @Composable { tabPositions: List<TabPosition> ->
        AnimatedTabIndicator(tabPositions, pagerState)
    }
    val currentDayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
    var isPagerInitialized by remember { mutableStateOf(false) }

    LaunchedEffect(currentDayOfWeek) {
        if (currentDayOfWeek != 1 && currentDayOfWeek != 7 && !isPagerInitialized) {
            isPagerInitialized = true
            pagerState.scrollToPage(currentDayOfWeek - 2)
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
                .fillMaxSize()
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
                                DailyScheduleLayout(courseList = state.mondayCourses) {
                                    for (i in state.mondayCourses)
                                        CourseCard(course = i)
                                    for (i in 0 until NUMBER_OF_HOURS)
                                        Divider()
                                }
                            }
                            1 -> {
                                DailyScheduleLayout(courseList = state.tuesdayCourses) {
                                    for (i in state.tuesdayCourses)
                                        CourseCard(course = i)
                                    for(i in 0 until NUMBER_OF_HOURS)
                                        Divider()
                                }
                            }
                            2 -> {
                                DailyScheduleLayout(courseList = state.wednesdayCourses) {
                                    for (i in state.wednesdayCourses)
                                        CourseCard(course = i)
                                    for(i in 0 until NUMBER_OF_HOURS)
                                        Divider()
                                }
                            }
                            3 -> {
                                DailyScheduleLayout(courseList = state.thursdayCourses) {
                                    for (i in state.thursdayCourses)
                                        CourseCard(course = i)
                                    for(i in 0 until NUMBER_OF_HOURS)
                                        Divider()
                                }
                            }
                            4 -> {
                                DailyScheduleLayout(courseList = state.fridayCourses) {
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





