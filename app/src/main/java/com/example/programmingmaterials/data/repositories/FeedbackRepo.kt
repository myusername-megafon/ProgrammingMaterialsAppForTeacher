package com.example.programmingmaterials.data.repositories

import com.example.programmingmaterials.RetrofitClient
import com.example.programmingmaterials.data.DTOClasses.AddFeedbackRequest
import com.example.programmingmaterials.data.DTOClasses.FeedbackDTO
import javax.inject.Inject

class FeedbackRepo @Inject constructor() {
    suspend fun getMaterialFeedbacks(materialId: Int): List<FeedbackDTO>{
        val allFeedbacks = RetrofitClient.apiService.getMaterialFeedbacks(materialId)
        return allFeedbacks
    }

    suspend fun AddFeedback(materialId: Int,userId: Int,content: String,rating: Int){
        val request = AddFeedbackRequest(materialId,userId, content, rating)
        val response = RetrofitClient.apiService.createMaterialFeedback(request)
    }

    suspend fun getUserFeedbacks(userId: Int): List<FeedbackDTO>{
        val userFeedbacks = RetrofitClient.apiService.getUserFeedbacks(userId)
        return userFeedbacks
    }

    suspend fun deleteFeedback(feedbackId: Int): Boolean {
        return try {
            val response = RetrofitClient.apiService.deleteFeedback(feedbackId)
            response.isSuccessful
        } catch (e: Exception) {
            false
        }
    }
}
