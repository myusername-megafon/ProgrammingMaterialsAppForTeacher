package com.example.programmingmaterials.data.DTOClasses

import com.google.gson.annotations.SerializedName

data class ProgressDTO(
    val userName: String,
    val startedMaterials: Int,
    val pendingMaterials: Int,
    val finishedMaterials: Int,
    val difficultyAVG: Float,
    val finishedTestsAVG: Float,
    val currentDate: String,
    val finishedTests: Int
)

data class ProgressRequestDTO(
    @SerializedName("userName") val userName: Int,
)
