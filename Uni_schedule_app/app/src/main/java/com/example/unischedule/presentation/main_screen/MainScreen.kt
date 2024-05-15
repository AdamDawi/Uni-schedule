package com.example.unischedule.presentation.main_screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.unischedule.common.Constants
import com.example.unischedule.presentation.main_screen.components.CustomTextField
import com.example.unischedule.presentation.main_screen.components.MainContentFullTime
import com.example.unischedule.presentation.main_screen.components.AlertDialogToWriteLink
import com.example.unischedule.presentation.main_screen.components.MainContentPartTime
import com.example.unischedule.presentation.main_screen.components.TopScheduleBar
import com.example.unischedule.ui.theme.BackgroundColor

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    var isAlertDialogOpen by remember { mutableStateOf(false) }
    var textInAlertDialog by remember { mutableStateOf("") }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopScheduleBar(
                onRefreshClick = { viewModel.getAllCoursesApi()},
                onSettingsClick = { isAlertDialogOpen = true},
                state
            )
        }
    ) {
        //screen for loading and optionally displaying error
        if(state.isLoading && state.allCourses.isEmpty()){
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
            //screen for writing link for the first time
        }else if(state.linkToSchedule.isEmpty()){
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(BackgroundColor),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                Text(
                    modifier = Modifier.padding(12.dp),
                    text = "Write your link to schedule",
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
                CustomTextField(
                    text = {textInAlertDialog},
                    onValueChange = { text -> textInAlertDialog = text},
                    placeholderText = Constants.LINK_PLACEHOLDER
                )
                TextButton(
                    onClick = {
                        viewModel.setLink(textInAlertDialog)
                        viewModel.getAllCoursesApi()
                        textInAlertDialog = ""
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = Color.Black)
                ) {
                    Text("Confirm")
                }
            }
            //main screen with schedule
        }else{
            LaunchedEffect(state.isLoading) {
                if(!state.isLoading && state.errorMessage.isEmpty()) Toast.makeText(context, "Successfully loaded schedule", Toast.LENGTH_SHORT).show()
                else if(state.errorMessage.isNotEmpty()) Toast.makeText(context, state.errorMessage, Toast.LENGTH_LONG).show()
            }
            if(state.isFullTimeStudies){
                MainContentFullTime(
                    modifier = Modifier.padding(it),
                    daysList = Constants.FULL_TIME_STUDIES_DAYS_LIST,
                state,
                viewModel
                )
            }else{
                MainContentPartTime(
                    modifier = Modifier.padding(it),
                    daysList = Constants.PART_TIME_STUDIES_DAYS_LIST,
                    state,
                    viewModel
                )
            }

            if(isAlertDialogOpen)
                AlertDialogToWriteLink(
                    onDismissRequest = {
                        isAlertDialogOpen = false
                        textInAlertDialog = ""
                    },
                    onConfirmation = {
                        viewModel.setLink(textInAlertDialog)
                        viewModel.getAllCoursesApi()
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

