package com.example.unischedule.glance

import android.content.Context
import android.icu.util.Calendar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.LocalSize
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.CircularProgressIndicator
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.updateAll
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.example.unischedule.MainActivity
import com.example.unischedule.common.Constants
import com.example.unischedule.common.DayOfWeek
import com.example.unischedule.common.darkerColor
import com.example.unischedule.domain.model.Course
import com.example.unischedule.domain.model.CourseEntity
import com.example.unischedule.domain.model.formattedTime
import com.example.unischedule.domain.model.toCourse
import com.example.unischedule.domain.repository.ScheduleDbRepository
import com.example.unischedule.presentation.main_screen.MainState
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class MyAppWidget : GlanceAppWidget() {
    private val state = mutableStateOf(MainState())
    private var courses: List<CourseEntity> = emptyList()
    private var currentCourse: Course? = Course()

    // a way to get hilt inject what you need in non-supported class
    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface CoursesProviderEntryPoint {
        fun scheduleDbRepository(): ScheduleDbRepository
    }
    companion object {
        private val SMALL_SQUARE = DpSize(100.dp, 100.dp)
        private val HORIZONTAL_RECTANGLE = DpSize(250.dp, 100.dp)
        private val BIG_SQUARE = DpSize(250.dp, 250.dp)
    }

    override val sizeMode = SizeMode.Responsive(
        setOf(
            SMALL_SQUARE,
            HORIZONTAL_RECTANGLE,
            BIG_SQUARE
        )
    )

    private fun setupPeriodicUpdate(context: Context) {
        val updateRequest = PeriodicWorkRequest.Builder(
            UpdateWidgetWorker::class.java,
            15, TimeUnit.MINUTES // 15 minutes
        ).build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "UpdateWidgetWork",
            ExistingPeriodicWorkPolicy.UPDATE,
            updateRequest
        )
    }
    private fun getCurrentDayOfWeek(): DayOfWeek{
        val calendarDayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        val currentDayOfWeek = when (calendarDayOfWeek) {
            1 -> DayOfWeek.SUNDAY
            2 -> DayOfWeek.MONDAY
            3 -> DayOfWeek.TUESDAY
            4 -> DayOfWeek.WEDNESDAY
            5 -> DayOfWeek.THURSDAY
            6 -> DayOfWeek.FRIDAY
            7 -> DayOfWeek.SATURDAY
            else -> throw IllegalArgumentException("Invalid day of week: $calendarDayOfWeek")
        }
        return currentDayOfWeek
    }

    private fun getCurrentTimeInMinutes(): Int{
        val currentMinute = Calendar.getInstance().get(Calendar.MINUTE)
        val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        return currentHour*60+currentMinute
    }
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        // In this method, load data needed to render the AppWidget.
        // Use `withContext` to switch to another thread for long running
        // operations.
        setupPeriodicUpdate(context)
        //get repository from hilt
        val appContext = context.applicationContext ?: throw IllegalStateException()
        val coursesEntryPoint =
            EntryPointAccessors.fromApplication(
                appContext,
                CoursesProviderEntryPoint::class.java,
            )
        val scheduleDbRepository = coursesEntryPoint.scheduleDbRepository()

        //set value
        withContext(Dispatchers.IO) {
            val result = scheduleDbRepository.getAllCourses().first()
                state.value = state.value.copy(
                    allCourses = result,
                    mondayCourses = courses.filter { it.dayOfWeek == Constants.FULL_TIME_STUDIES_DAYS_LIST[0] }
                        .map { it.toCourse() },
                    tuesdayCourses = courses.filter { it.dayOfWeek == Constants.FULL_TIME_STUDIES_DAYS_LIST[1] }
                        .map { it.toCourse() },
                    wednesdayCourses = courses.filter { it.dayOfWeek == Constants.FULL_TIME_STUDIES_DAYS_LIST[2] }
                        .map { it.toCourse() },
                    thursdayCourses = courses.filter { it.dayOfWeek == Constants.FULL_TIME_STUDIES_DAYS_LIST[3] }
                        .map { it.toCourse() },
                    fridayCourses = courses.filter { it.dayOfWeek == Constants.FULL_TIME_STUDIES_DAYS_LIST[4] }
                        .map { it.toCourse() },
                    saturdayCourses = courses.filter { it.dayOfWeek == Constants.PART_TIME_STUDIES_DAYS_LIST[0] }
                        .map { it.toCourse() },
                    sundayCourses = courses.filter { it.dayOfWeek == Constants.PART_TIME_STUDIES_DAYS_LIST[1] }
                        .map { it.toCourse() },
                    isLoading = false
                )
            val currentTimeInMinutes = getCurrentTimeInMinutes()
            var dayOfWeek = getCurrentDayOfWeek()
            currentCourse = state.value.allCourses
                .filter { it.dayOfWeek.equals(dayOfWeek.name, ignoreCase = true) }
                .filter { it.endTime >= currentTimeInMinutes }
                .sortedBy { it.endTime }.getOrNull(0)?.toCourse()

            //loop to find next courses
            while (currentCourse == null) {
                dayOfWeek = DayOfWeek.entries[(dayOfWeek.ordinal + 1) % DayOfWeek.entries.size]
                currentCourse = state.value.allCourses
                    .filter { it.dayOfWeek.equals(DayOfWeek.entries[dayOfWeek.ordinal].name, ignoreCase = true) }
                    .sortedBy { it.endTime }.getOrNull(0)?.toCourse()
            }
            updateAll(context)
        }

            provideContent {
                // create your AppWidget here

                MyContent(state.value, currentCourse!!)
            }

    }
    @Composable
    private fun MyContent(
        state: MainState,
        currentCourse: Course
    ) {
        Column(
            modifier = GlanceModifier.fillMaxSize().clickable(onClick = actionStartActivity<MainActivity>()),
            verticalAlignment = Alignment.CenterVertically,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = GlanceModifier
                    .fillMaxSize()
                    .background(currentCourse.color.darkerColor())
                    .padding(6.dp),
                horizontalAlignment = Alignment.End,
                verticalAlignment = Alignment.Top
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator()
                } else {
                    Row(horizontalAlignment = Alignment.CenterHorizontally) {
                        CourseCardWidget(currentCourse)
                    }
                }
            }
        }
    }
    @Composable
    fun CourseCardWidget(course: Course) {
        val darkerColor = course.color.darkerColor()
        // Size will be one of the sizes defined above.
        val size = LocalSize.current
        Box(
            modifier = GlanceModifier
                .fillMaxSize()
                .cornerRadius(12.dp)
                .background(course.color),
            contentAlignment = Alignment.Center
        ) {

            Box{
                if (course.type.isNotEmpty()) {
                    Row(
                        modifier = GlanceModifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalAlignment = Alignment.End
                    ) {
                        Box(
                            modifier = GlanceModifier
                                .cornerRadius(12.dp)
                                .padding(12.dp)
                                .background(darkerColor)
                        ) {
                            Text(
                                text = course.type,
                                style = TextStyle(textAlign = TextAlign.End)
                            )
                        }
                    }
                }
                Column(
                    modifier = GlanceModifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = GlanceModifier
                            .padding(8.dp),
                        text = course.name,
                        maxLines = 2,
                        style = TextStyle(
                            color = ColorProvider(Color.Black),
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                        )
                    )
//                    if(size.height >= BIG_SQUARE.height) {
//                        Text(text = course.room.ifEmpty { "No room" }, modifier = GlanceModifier.padding(12.dp))
//                        Text(text = course.leader.ifEmpty { "No leader" }, modifier = GlanceModifier.padding(12.dp))
//                        Text(text = course.formattedTime(), modifier = GlanceModifier.padding(12.dp))
//                    }else{
                        Text(text = course.room.ifEmpty { "No room" })
                        Text(text = course.leader.ifEmpty { "No leader" })
                        Text(text = course.formattedTime())
//                    }
                }

            }

        }
    }
}

class UpdateWidgetWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        // Update widget
        MyAppWidget().updateAll(applicationContext)
        return Result.success()
    }
}


