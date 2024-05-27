package com.example.unischedule.presentation.main_screen

sealed class MainEvent {
    data class OnConfirmScheduleLink(val newLink: String): MainEvent()
    data object OnRefreshClick: MainEvent()
}