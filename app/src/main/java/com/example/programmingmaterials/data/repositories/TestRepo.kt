package com.example.programmingmaterials.data.repositories

import android.util.Log
import com.example.programmingmaterials.RetrofitClient
import com.example.programmingmaterials.data.DTOClasses.SaveResultDTO
import com.example.programmingmaterials.data.DTOClasses.TestDTO
import javax.inject.Inject

class TestRepo @Inject constructor() {

    suspend fun getTest(userId: Int, categoryName: String): TestDTO{
        val test = RetrofitClient.apiService.getTest(userId, categoryName)
        return test
    }

    suspend fun saveTestResult(userId: Int,testId: Int,result: Int){
        val request = SaveResultDTO(userId = userId, testId = testId, result = result)
        val response = RetrofitClient.apiService.saveResult(request)
    }
}