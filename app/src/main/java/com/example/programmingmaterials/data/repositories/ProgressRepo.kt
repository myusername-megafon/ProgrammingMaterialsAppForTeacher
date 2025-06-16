package com.example.programmingmaterials.data.repositories

import com.example.programmingmaterials.RetrofitClient
import com.example.programmingmaterials.data.DTOClasses.ProgressDTO
import com.example.programmingmaterials.model.Category
import com.example.programmingmaterials.data.DTOClasses.ProgressRequestDTO
import com.example.programmingmaterials.navigation.Routes
import java.io.IOException
import javax.inject.Inject

class ProgressRepo @Inject constructor() {

    suspend fun getUserProgressByUserEmail(userEmail: String): ProgressDTO{
        return RetrofitClient.apiService.getProgress(userEmail)
    }
}