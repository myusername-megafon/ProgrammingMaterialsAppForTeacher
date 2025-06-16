package com.example.programmingmaterials.data.DTOClasses

import com.google.gson.annotations.SerializedName

data class FeedbackDTO(
    @SerializedName("id")val id: Int,
    @SerializedName("materialName") val materialName: String,
    @SerializedName("userName")val userName: String,
    @SerializedName("content")val content: String,
    @SerializedName("rating")val rating: Int,
    @SerializedName("createdDate")val date: String
)