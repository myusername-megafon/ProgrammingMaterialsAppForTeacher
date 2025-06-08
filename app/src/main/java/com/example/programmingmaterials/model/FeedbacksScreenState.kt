package com.example.programmingmaterials.model

import com.example.programmingmaterials.data.DTOClasses.FeedbackDTO

data class FeedbacksScreenState (
    val feedbacksList: List<FeedbackDTO> = emptyList()
)