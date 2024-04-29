package com.example.unischedule.presentation.main_screen.components

import android.icu.util.Calendar
import android.util.Log
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.compose.ui.zIndex
import com.example.unischedule.domain.model.Course
import com.example.unischedule.ui.theme.BackgroundColor
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch

val HOURS_SIZE = 100.dp

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabBar(
    modifier: Modifier = Modifier,
    daysList: List<String>,
    courses: List<Course>
) {
    val pagerState = rememberPagerState()
    val indicator = @Composable { tabPositions: List<TabPosition> ->
        CustomIndicator(tabPositions, pagerState)
    }
    val coroutineScope = rememberCoroutineScope()
    val currentDayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
    var isPagerInitialized by remember { mutableStateOf(false) }

    LaunchedEffect(currentDayOfWeek) {
        if (currentDayOfWeek != 1 && currentDayOfWeek != 7 && !isPagerInitialized) {
            isPagerInitialized = true
            pagerState.scrollToPage(currentDayOfWeek - 2)
        }
    }

    Column(modifier = modifier) {
        ScrollableTabRow(
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth(),
            selectedTabIndex = pagerState.currentPage,
            indicator = indicator,
            backgroundColor = BackgroundColor,
            edgePadding = 0.dp
        ) {
            daysList.forEachIndexed { index, title ->
                Tab(
                    modifier = Modifier
                        .padding(4.dp)
                        .clip(RoundedCornerShape(50))
                        .zIndex(6f),
                    text = {
                        Text(
                            text = title,
                            color = if (pagerState.currentPage == index) Color.White else Color.Black
                        )
                    },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    selectedContentColor = Color.DarkGray
                )
            }
        }

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
                            .padding(start = 8.dp)
                    )
                    // Pager content takes up the remaining space
                    HorizontalPager(
                        modifier = Modifier.fillMaxSize(),
                        count = daysList.size,
                        state = pagerState
                    ) { page ->
                        when (page) {
                            0 -> {
                                CustomLayout(courseList = courses.filter { it.dayOfWeek=="Monday" }) {
                                    for (i in courses.filter { it.dayOfWeek=="Monday" })
                                        CourseCard(course = i)
                                    for (i in 0..14)
                                        Divider()
                                }
                            }
                            1 -> {
                                CustomLayout(courseList = courses.filter { it.dayOfWeek=="Tuesday" }) {
                                    for (i in courses.filter { it.dayOfWeek=="Tuesday" })
                                        CourseCard(course = i)
                                    for(i in 0..14)
                                        Divider()
                                }
                            }
                            2 -> {
                                CustomLayout(courseList = courses.filter { it.dayOfWeek=="Wednesday" }) {
                                    for (i in courses.filter { it.dayOfWeek=="Wednesday" })
                                        CourseCard(course = i)
                                    for(i in 0..14)
                                        Divider()
                                }
                            }
                            3 -> {
                                CustomLayout(courseList = courses.filter { it.dayOfWeek=="Thursday" }) {
                                    for (i in courses.filter { it.dayOfWeek=="Thursday" })
                                        CourseCard(course = i)
                                    for(i in 0..14)
                                        Divider()
                                }
                            }
                            4 -> {
                                CustomLayout(courseList = courses.filter { it.dayOfWeek=="Friday"}) {
                                    for (i in courses.filter { it.dayOfWeek=="Friday"})
                                        CourseCard(course = i)
                                    for(i in 0..14)
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

@Composable
fun CustomLayout(
    modifier: Modifier = Modifier,
    courseList: List<Course>,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier.height(HOURS_SIZE*14),
        content = content
    ) { items, constraints ->

        //measurements
        val itemsPlaceable = items.mapIndexed {i , it ->
            if(i<courseList.size){
                val elHeight = (courseList[i].endTime-courseList[i].startTime)/ 60f
                Log.e("Height", elHeight.toString())
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
            // 8:00 - 0
            // 9:00 - 100.dp
            // 10:00 - 200.dp
            // 10:45 - 645 - 480 = 165
            // 165/60*100 = 275
            val courseListPlaceable = courseList.reversed()
            var divY = 0
            itemsPlaceable.reversed().forEachIndexed { i, el ->
                //place dividers first because we want they on background
                if(i<15) {
                    el.placeRelative(0, divY)
                    divY+=100.dp.roundToPx()
                }else{
                    val elY = ((courseListPlaceable[i-15].startTime - 480) / 60f * HOURS_SIZE)
                    Log.e("Height", el.height.toString())
                    Log.e("Y", elY.toString())
                    el.placeRelative(0, elY.roundToPx())

                }
            }
        }
    }
}

@Composable
fun FixedHoursComponent(modifier: Modifier) {
    // Your fixed component content here
    Column(
        modifier = modifier
            .fillMaxHeight()
    ) {
        for (i in 0..14) {
            Text(
                text = "${i + 8}:00",
                modifier = Modifier
                    .heightIn(HOURS_SIZE)
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun CustomIndicator(tabPositions: List<TabPosition>, pagerState: PagerState) {
    val transition = updateTransition(pagerState.currentPage, label = "transition")
    val indicatorStart by transition.animateDp(
        transitionSpec = {
            if (initialState < targetState) {
                spring(dampingRatio = 1f, stiffness = 50f)
            } else {
                spring(dampingRatio = 1f, stiffness = 1000f)
            }
        }, label = ""
    ) {
        tabPositions[it].left
    }

    val indicatorEnd by transition.animateDp(
        transitionSpec = {
            if (initialState < targetState) {
                spring(dampingRatio = 1f, stiffness = 1000f)
            } else {
                spring(dampingRatio = 1f, stiffness = 50f)
            }
        }, label = ""
    ) {
        tabPositions[it].right
    }

    Box(
        Modifier
            .offset(x = indicatorStart)
            .wrapContentSize(align = Alignment.BottomStart)
            .width(indicatorEnd - indicatorStart)
            .padding(4.dp)
            .fillMaxSize()
            .background(color = Color.Gray, RoundedCornerShape(50))
            .zIndex(1f)
    )
}