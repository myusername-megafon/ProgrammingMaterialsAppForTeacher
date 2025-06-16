package com.example.programmingmaterials.model

data class UserProfileScreenState(
    val isLoading: Boolean = false,
    val userMailText: String = "",
    val isDataValid: Boolean = false,
    val finishedMaterials: Int = 0,
    val pendingMaterials: Int = 0,
    val startedMaterials: Int = 0,
    val userName: String = "",
    val difficultyAVG: Float = 0f,
    val finishedTestsAVG: Float = 0f,
    val currentDate: String = "",
    val finishedTests: Int = 0
)