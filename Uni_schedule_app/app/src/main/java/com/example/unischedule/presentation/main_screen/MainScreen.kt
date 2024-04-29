package com.example.unischedule.presentation.main_screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.unischedule.common.Constants
import com.example.unischedule.presentation.main_screen.components.TabBar
import com.example.unischedule.presentation.main_screen.components.TopScheduleBar

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopScheduleBar()
        }
    ) {

        TabBar(modifier = Modifier.padding(it),
            daysList = Constants.FULL_TIME_STUDIES_DAYS_LIST2,
            viewModel.state.value.courses
        )


    }
}

@Preview
@Composable
private fun MainScreenPreview() {
    MainScreen()
}