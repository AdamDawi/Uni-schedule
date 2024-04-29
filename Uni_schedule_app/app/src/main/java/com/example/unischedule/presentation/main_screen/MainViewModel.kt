package com.example.unischedule.presentation.main_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unischedule.common.Constants
import com.example.unischedule.common.Resource
import com.example.unischedule.domain.use_case.GetCoursesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCoursesUseCase: GetCoursesUseCase,
): ViewModel() {

    private val _state = mutableStateOf(MainState())
    val state: State<MainState> = _state
    init {
        getCourses()
    }

    private fun getCourses(){
        getCoursesUseCase().onEach { result ->
            when(result){
                is Resource.Success -> {
                    val courses = result.data ?: emptyList()
                    _state.value = _state.value.copy(
                        mondayCourses = courses.filter { it.dayOfWeek==Constants.FULL_TIME_STUDIES_DAYS_LIST[0] },
                        tuesdayCourses = courses.filter { it.dayOfWeek==Constants.FULL_TIME_STUDIES_DAYS_LIST[1] },
                        wednesdayCourses = courses.filter { it.dayOfWeek==Constants.FULL_TIME_STUDIES_DAYS_LIST[2] },
                        thursdayCourses = courses.filter { it.dayOfWeek==Constants.FULL_TIME_STUDIES_DAYS_LIST[3] },
                        fridayCourses = courses.filter { it.dayOfWeek==Constants.FULL_TIME_STUDIES_DAYS_LIST[4] },
                        saturdayCourses = courses.filter { it.dayOfWeek==Constants.PART_TIME_STUDIES_DAYS_LIST[0] },
                        sundayCourses = courses.filter { it.dayOfWeek==Constants.PART_TIME_STUDIES_DAYS_LIST[1] },
                        isLoading = false
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(errorMessage = result.message ?: "An unexpected error")
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}