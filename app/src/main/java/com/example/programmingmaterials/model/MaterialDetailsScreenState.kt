package com.example.programmingmaterials.model

import com.example.programmingmaterials.data.DTOClasses.FeedbackDTO

data class MaterialDetailsScreenState(
    val materialName: String = "materialName",
    val categoryName: String = "categoryName",
    val authorName: String = "authorName",
    val materialText: String = "materialText",
    val status: String = "Не начато",
    val reviews: List<FeedbackDTO> = emptyList(),
    val showAddFeedbackDialog: Boolean = false
)