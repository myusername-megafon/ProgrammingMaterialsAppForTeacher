package com.example.programmingmaterials.data.repositories

import com.example.programmingmaterials.RetrofitClient
import com.example.programmingmaterials.data.DTOClasses.ProgressDTO
import com.example.programmingmaterials.model.User
import javax.inject.Inject

class ProfileRepo @Inject constructor() {

    suspend fun getUser(userId: Int): User {
        val user = RetrofitClient.apiService.getUser(userId)
        return user
    }

    suspend fun getUserProgressInNumbers(userId: Int): ProgressDTO {
        val progressInNumbers = RetrofitClient.apiService.getUserProgressInNumbers(userId)
        return progressInNumbers
    }
}