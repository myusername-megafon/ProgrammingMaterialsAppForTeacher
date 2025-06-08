package com.example.programmingmaterials.data.DTOClasses

import com.google.gson.annotations.SerializedName

data class TestDTO(val iD_Test: Int, val content_Test: String, val difficulty: Int, val result: Int)

data class SaveResultDTO(
    @SerializedName("userId")val userId: Int,
    @SerializedName("testId")val testId: Int,
    @SerializedName("result")val result: Int
)
