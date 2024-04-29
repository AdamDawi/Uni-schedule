package com.example.unischedule.presentation.main_screen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.TabPosition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.unischedule.ui.theme.BackgroundColor
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ScrollableDayTabs(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    indicator: @Composable (tabPositions: List<TabPosition>) -> Unit,
    daysList: List<String>
) {
    val coroutineScope = rememberCoroutineScope()
    ScrollableTabRow(
        modifier = modifier
            .height(50.dp)
            .fillMaxWidth(),
        selectedTabIndex = pagerState.currentPage,
        indicator = indicator,
        backgroundColor = BackgroundColor,
        edgePadding = 0.dp
    ) {
        daysList.forEachIndexed { index, title ->
            key(index) {
                Tab(modifier = Modifier
                    .padding(4.dp)
                    .clip(RoundedCornerShape(50))
                    .zIndex(6f), text = {
                    Text(
                        text = title,
                        color = if (pagerState.currentPage == index) Color.White else Color.Black
                    )
                }, selected = pagerState.currentPage == index, onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }, selectedContentColor = Color.DarkGray
                )
            }
        }
    }
}