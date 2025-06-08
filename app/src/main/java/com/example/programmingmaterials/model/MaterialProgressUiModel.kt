package com.example.programmingmaterials.model

data class MaterialProgressUiModel(
    val id: Int,
    val materialName: String,
    val categoryName: String,
    val status: String,
    val image: Int? = null
)