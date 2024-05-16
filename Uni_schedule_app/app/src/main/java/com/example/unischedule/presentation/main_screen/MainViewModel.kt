package com.example.unischedule.presentation.main_screen

import android.icu.util.Calendar
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unischedule.common.Constants
import com.example.unischedule.common.Resource
import com.example.unischedule.common.Session
import com.example.unischedule.domain.model.toCourse
import com.example.unischedule.domain.model.toCourseEntity
import com.example.unischedule.domain.use_case.MainScreenUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainScreenUseCases: MainScreenUseCases,
    private val session: Session
): ViewModel() {

    private val _state = mutableStateOf(MainState())
    val state: State<MainState> = _state
    init {
        getCurrentDayOfWeek()
        getLinkFromDataStore()

    }
    fun setLink(newLink: String): Int{
        //validation
        val linkPattern = Regex("""^http://planwe\.pollub\.pl/plan\.php\?type=\d&id=\d+(&winW=\d+&winH=\d+&loadBG=\d{6})?$""")
        if (linkPattern.matches(newLink)) {
        viewModelScope.launch {

            session.setLink(newLink)
            _state.value = _state.value.copy(linkToSchedule = newLink)
            getAllCoursesApi()
            }
        }else return -1
        return 1
    }
    private fun getLinkFromDataStore() {
        viewModelScope.launch {
            _state.value = _state.value.copy(linkToSchedule = session.getLink().first())
            if(_state.value.linkToSchedule.isNotEmpty()) getAllCoursesDb()
        }
    }

    fun getAllCoursesApi(){
        mainScreenUseCases.getAllCoursesApiUseCase(_state.value.linkToSchedule).onEach { result ->
            when(result){
                is Resource.Success -> {
                    //delete previous courses from database
                    mainScreenUseCases.deleteAllCoursesDbUseCase(_state.value.allCourses)
                    val courses = result.data ?: emptyList()
                    //custom id for transform course to course entity
                    var i = 0
                    _state.value = _state.value.copy(
                        allCourses = courses.map { it.toCourseEntity(++i) },
                        mondayCourses = courses.filter { it.dayOfWeek==Constants.FULL_TIME_STUDIES_DAYS_LIST[0] },
                        tuesdayCourses = courses.filter { it.dayOfWeek==Constants.FULL_TIME_STUDIES_DAYS_LIST[1] },
                        wednesdayCourses = courses.filter { it.dayOfWeek==Constants.FULL_TIME_STUDIES_DAYS_LIST[2] },
                        thursdayCourses = courses.filter { it.dayOfWeek==Constants.FULL_TIME_STUDIES_DAYS_LIST[3] },
                        fridayCourses = courses.filter { it.dayOfWeek==Constants.FULL_TIME_STUDIES_DAYS_LIST[4] },
                        saturdayCourses = courses.filter { it.dayOfWeek==Constants.PART_TIME_STUDIES_DAYS_LIST[0] },
                        sundayCourses = courses.filter { it.dayOfWeek==Constants.PART_TIME_STUDIES_DAYS_LIST[1] },
                        isLoading = false
                    )
                    mainScreenUseCases.addCoursesToDbUseCase(state.value.allCourses)
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

    private fun getAllCoursesDb(){
        mainScreenUseCases.getAllCoursesDbUseCase().onEach { result ->
            when(result){
                is Resource.Success -> {
                    val courses = result.data?.map { it.toCourse() } ?: emptyList()
                    _state.value = _state.value.copy(
                        allCourses = result.data ?: emptyList(),
                        mondayCourses = courses.filter { it.dayOfWeek==Constants.FULL_TIME_STUDIES_DAYS_LIST[0] },
                        tuesdayCourses = courses.filter { it.dayOfWeek==Constants.FULL_TIME_STUDIES_DAYS_LIST[1] },
                        wednesdayCourses = courses.filter { it.dayOfWeek==Constants.FULL_TIME_STUDIES_DAYS_LIST[2] },
                        thursdayCourses = courses.filter { it.dayOfWeek==Constants.FULL_TIME_STUDIES_DAYS_LIST[3] },
                        fridayCourses = courses.filter { it.dayOfWeek==Constants.FULL_TIME_STUDIES_DAYS_LIST[4] },
                        saturdayCourses = courses.filter { it.dayOfWeek==Constants.PART_TIME_STUDIES_DAYS_LIST[0] },
                        sundayCourses = courses.filter { it.dayOfWeek==Constants.PART_TIME_STUDIES_DAYS_LIST[1] },
                    )
                    _state.value = _state.value.copy(
                        isFullTimeStudies = _state.value.saturdayCourses.isEmpty() && _state.value.sundayCourses.isEmpty()
                    )
                    //when we don't have any courses in database
                    if(_state.value.allCourses.isEmpty())
                        getAllCoursesApi()
                    else _state.value = _state.value.copy(isLoading = false)
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

    fun getCurrentTimeInMinutes(): Int{
        val currentMinute = Calendar.getInstance().get(Calendar.MINUTE)
        val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        return currentHour*60+currentMinute
    }

    private fun getCurrentDayOfWeek(){
        _state.value = _state.value.copy(currentDayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK))
    }
}