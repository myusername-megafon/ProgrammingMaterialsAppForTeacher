package com.example.programmingmaterials.data.repositories

import com.example.programmingmaterials.RetrofitClient
import com.example.programmingmaterials.data.DTOClasses.FeedbackDTO
import javax.inject.Inject

class FeedbackRepo @Inject constructor() {
    suspend fun getMaterialFeedbacks(materialId: Int): List<FeedbackDTO>{
        val allFeedbacks = RetrofitClient.apiService.getMaterialFeedbacks(materialId)
        return allFeedbacks
    }

}
