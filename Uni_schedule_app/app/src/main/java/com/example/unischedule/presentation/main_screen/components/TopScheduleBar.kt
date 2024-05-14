package com.example.unischedule.presentation.main_screen.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.unischedule.presentation.main_screen.MainState
import com.example.unischedule.ui.theme.BackgroundColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopScheduleBar(
    onRefreshClick: () -> Unit,
    onSettingsClick: () -> Unit,
    state: MainState
) {
    val rotationTransition = rememberInfiniteTransition(label = "infinite transition")

    val rotationValue by rotationTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation value",

    )

    TopAppBar(title = { Text(
        text = "Schedule",
        fontWeight = FontWeight.Bold
    ) },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = BackgroundColor,
            titleContentColor = Color.Black
        ),
        actions = {
            IconButton(
                onClick = { onRefreshClick() },
                enabled = !((state.isLoading) || state.linkToSchedule.isEmpty())
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    tint = Color.Black,
                    contentDescription = Icons.Default.Refresh.name,
                    modifier = Modifier
                        .rotate(if(state.isLoading && state.allCourses.isNotEmpty()) rotationValue else 0f)
                )
            }
            IconButton(
                onClick = { onSettingsClick() },
                enabled = !((state.isLoading) || state.linkToSchedule.isEmpty())
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    tint = Color.Black,
                    contentDescription = Icons.Default.Settings.name
                )
            }
        }
    )
}

@Preview
@Composable
private fun TopScheduleBarPreview() {
    TopScheduleBar(
        onRefreshClick = { },
        onSettingsClick = { },
        state = MainState()
    )

}