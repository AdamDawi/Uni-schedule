package com.example.unischedule.di

import android.app.Application
import androidx.room.Room
import com.example.unischedule.common.Constants
import com.example.unischedule.common.Constants.BASE_URL
import com.example.unischedule.data.local.ScheduleDatabase
import com.example.unischedule.data.remote.ScheduleApi
import com.example.unischedule.data.repository.ScheduleApiRepositoryImpl
import com.example.unischedule.data.repository.ScheduleDbRepositoryImpl
import com.example.unischedule.domain.repository.ScheduleApiRepository
import com.example.unischedule.domain.repository.ScheduleDbRepository
import com.example.unischedule.domain.use_case.AddCoursesToDbUseCase
import com.example.unischedule.domain.use_case.GetAllCoursesDbUseCase
import com.example.unischedule.domain.use_case.GetCoursesApiUseCase
import com.example.unischedule.domain.use_case.MainScreenUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.readTimeout(600, TimeUnit.SECONDS) // Set read timeout to 10 minutes
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideScheduleApi(okHttpClient: OkHttpClient): ScheduleApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ScheduleApi::class.java)
    }

    @Provides
    @Singleton
    fun provideScheduleApiRepository(api: ScheduleApi): ScheduleApiRepository{
        return ScheduleApiRepositoryImpl(api)
    }
    @Provides
    @Singleton
    fun provideScheduleDatabase(app: Application): ScheduleDatabase{
        return Room.databaseBuilder(
            app,
            ScheduleDatabase::class.java,
            Constants.COURSES_TABLE_NAME
        ).build()
    }
    @Provides
    @Singleton
    fun provideScheduleDbRepository(db: ScheduleDatabase): ScheduleDbRepository {
        return ScheduleDbRepositoryImpl(db.scheduleDao)
    }

    @Provides
    @Singleton
    fun provideGetAllCoursesDbUseCase(repository: ScheduleDbRepository): GetAllCoursesDbUseCase{
        return GetAllCoursesDbUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideAddCoursesToDbUseCase(repository: ScheduleDbRepository): AddCoursesToDbUseCase{
        return AddCoursesToDbUseCase(repository)
    }

    @Provides
    @Singleton
    fun MainScreenUseCases(repositoryDb: ScheduleDbRepository, repositoryApi: ScheduleApiRepository): MainScreenUseCases {
        return MainScreenUseCases(
            AddCoursesToDbUseCase(repositoryDb),
            GetAllCoursesDbUseCase(repositoryDb),
            GetCoursesApiUseCase(repositoryApi)
        )
    }
}