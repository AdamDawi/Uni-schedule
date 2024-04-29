package com.example.unischedule.presentation.main_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.unischedule.common.Constants
import com.example.unischedule.presentation.main_screen.components.MainContent
import com.example.unischedule.presentation.main_screen.components.TopScheduleBar
import com.example.unischedule.ui.theme.BackgroundColor

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    Scaffold(
        topBar = {
            TopScheduleBar()
        }
    ) {
        if(state.isLoading){
            Column(modifier = Modifier
                .fillMaxSize()
                .background(BackgroundColor),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(40.dp)
                        .padding(3.dp),
                    color = Color.Black
                )
                Text(
                    modifier = Modifier.padding(12.dp),
                    text = state.errorMessage.ifEmpty { "Loading data from server..." },
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }else{
            MainContent(
                modifier = Modifier.padding(it),
                daysList = Constants.FULL_TIME_STUDIES_DAYS_LIST,
                state
            )
        }

    }
}

@Preview
@Composable
private fun MainScreenPreview() {
    MainScreen()
}