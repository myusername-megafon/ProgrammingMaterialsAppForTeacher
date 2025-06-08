package com.example.programmingmaterials.data.repositories

import com.example.programmingmaterials.RetrofitClient
import com.example.programmingmaterials.model.Category
import com.example.programmingmaterials.data.DTOClasses.ProgressRequestDTO
import com.example.programmingmaterials.data.DTOClasses.UpdateProgressRequestDTO
import java.io.IOException
import javax.inject.Inject

class ProgressRepo @Inject constructor() {
    suspend fun getAllCategories(): List<Category>{
        return RetrofitClient.apiService.getAllCategories()
    }

    fun getAllStatuses(): List<String> {
        return listOf("В процессе", "Отложено", "Завершено")
    }

    suspend fun createProgressEntry(userId: Int, materialId: Int, status: String) {
        val statusId = when (status) {
            "В процессе" -> 1
            "Отложено" -> 2
            "Завершено" -> 3
            else -> 1 // По умолчанию "В процессе"
        }

        val request = ProgressRequestDTO(
            userId = userId,
            materialId = materialId,
            statusId = statusId
        )

        val response = RetrofitClient.apiService.createProgressEntry(request)
        if (!response.isSuccessful) {
            throw IOException("Ошибка создания записи: ${response.code()}")
        }
    }

    suspend fun updateProgressStatus(
        userId: Int,
        materialId: Int,
        newStatus: String
    ) {
        val statusId = when (newStatus) {
            "В процессе" -> 1
            "Отложено" -> 2
            "Завершено" -> 3
            else -> throw IllegalArgumentException("Неизвестный статус")
        }

        val request = UpdateProgressRequestDTO(
            userId = userId,
            materialId = materialId,
            statusId = statusId
        )

        val response = RetrofitClient.apiService.updateProgressStatus(request)
        if (!response.isSuccessful) {
            throw IOException("Ошибка обновления статуса: ${response.code()}")
        }
    }
}