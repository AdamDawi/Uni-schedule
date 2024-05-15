package com.example.unischedule.presentation.main_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainContentPartTime(
    modifier: Modifier = Modifier,
    daysList: List<String>,
    state: MainState,
    viewModel: MainViewModel
) {
        val pagerState = rememberPagerState()
        val indicator = @Composable { tabPositions: List<TabPosition> ->
            AnimatedTabIndicator(tabPositions, pagerState)
        }

        var isPagerInitialized by remember { mutableStateOf(false) }
        val lazyListState = rememberLazyListState()

        LaunchedEffect(state.currentDayOfWeek) {
            if (!isPagerInitialized) {
                isPagerInitialized = true
                if(state.currentDayOfWeek==0)
                    pagerState.scrollToPage( 0)
                else if(state.currentDayOfWeek==7)
                    pagerState.scrollToPage( 1)
                //when time is above 17:00 scroll to next part of the plan
                if(viewModel.getCurrentTimeInMinutes()>=960)
                    lazyListState.scrollToItem(0, scrollOffset = ((viewModel.getCurrentTimeInMinutes()) / 60f * HOURS_SIZE).value.toInt())
                else lazyListState.scrollToItem(0)
            }
        }

        Column(modifier = modifier.fillMaxSize()) {
            ScrollableDayTabs(
                modifier = Modifier.width(220.dp),
                pagerState = pagerState,
                indicator = indicator,
                daysList = daysList,
                state = state
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
                                    DailyScheduleLayout(
                                        courseList = state.saturdayCourses,
                                        viewModel = viewModel
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .alpha(if (state.currentDayOfWeek == 1) 1f else 0f),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Box(
                                                modifier = Modifier
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
                                        for (i in state.saturdayCourses)
                                            CourseCard(course = i)
                                        for (i in 0 until NUMBER_OF_HOURS)
                                            Divider()
                                    }

                                }
                                1 ->{
                                    DailyScheduleLayout(
                                        courseList = state.sundayCourses,
                                        viewModel = viewModel
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .alpha(if (state.currentDayOfWeek == 7) 1f else 0f),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Box(
                                                modifier = Modifier
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
                                        for (i in state.sundayCourses)
                                            CourseCard(course = i)
                                        for (i in 0 until NUMBER_OF_HOURS)
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