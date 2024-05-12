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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.unischedule.common.Constants
import com.example.unischedule.presentation.main_screen.components.MainContent
import com.example.unischedule.presentation.main_screen.components.ScheduleAlertDialog
import com.example.unischedule.presentation.main_screen.components.TopScheduleBar
import com.example.unischedule.ui.theme.BackgroundColor

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    var isAlertDialogOpen by remember { mutableStateOf(false) }
    var textInAlertDialog by remember { mutableStateOf("") }
    Scaffold(
        topBar = {
            TopScheduleBar(
                onRefreshClick = { viewModel.getAllCoursesApi()},
                onSettingsClick = { isAlertDialogOpen = true}
            )
        }
    ) {
        if(state.isLoading){
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(BackgroundColor),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                if(state.errorMessage.isEmpty()){
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(40.dp),
                        color = Color.Black
                    )
                }
                Text(
                    modifier = Modifier.padding(12.dp),
                    text = state.errorMessage.ifEmpty { "Loading data..." },
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
            }
        }else{
            MainContent(
                modifier = Modifier.padding(it),
                daysList = Constants.FULL_TIME_STUDIES_DAYS_LIST,
                state,
                viewModel
            )
            if(isAlertDialogOpen)
                ScheduleAlertDialog(
                    onDismissRequest = {
                        isAlertDialogOpen = false
                        textInAlertDialog = ""
                    },
                    onConfirmation = {
                        isAlertDialogOpen = false
                        textInAlertDialog = ""
                    },
                    dialogTitle = "Write your link to schedule",
                    onValueChange = { text -> textInAlertDialog = text},
                    text = {textInAlertDialog}
                )
        }

    }
}

