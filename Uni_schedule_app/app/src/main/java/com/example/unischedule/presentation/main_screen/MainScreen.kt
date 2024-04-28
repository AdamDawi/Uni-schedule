package com.example.unischedule.presentation.main_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.unischedule.common.Constants
import com.example.unischedule.presentation.main_screen.components.DaysSection
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            DaysSection(daysList = Constants.FULL_TIME_STUDIES_DAYS_LIST)
            Divider(modifier = Modifier
                .padding(top = 4.dp, bottom = 4.dp)
            )
            LazyColumn(modifier = Modifier
                .fillMaxSize()
                .padding(8.dp))
            {
                item {for(i in 10..18)
                    Text(text = "$i AM",
                        modifier = Modifier.padding(bottom = 80.dp))

                }
            }
        }
    }
}

@Preview
@Composable
private fun MainScreenPreview() {
    MainScreen()
}