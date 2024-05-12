package com.example.unischedule.domain.use_case

data class MainScreenUseCases(
    val addCoursesToDbUseCase: AddCoursesToDbUseCase,
    val getAllCoursesDbUseCase: GetAllCoursesDbUseCase,
    val getAllCoursesApiUseCase: GetAllCoursesApiUseCase
)