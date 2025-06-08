package com.example.programmingmaterials.data.repositories

import com.example.programmingmaterials.RetrofitClient
import com.example.programmingmaterials.data.DTOClasses.MaterialDTO
import javax.inject.Inject

class MaterialRepo @Inject constructor() {

    suspend fun getAllMaterials(userID: Int): List<MaterialDTO>{
        val materials = RetrofitClient.apiService.getAllMaterials(userID)
        return materials
    }
    suspend fun getNewMaterials(userID: Int): List<MaterialDTO> {
        val materials = RetrofitClient.apiService.getNewMaterials(userID)
        return materials
    }

    suspend fun getStartedMaterials(userID: Int): List<MaterialDTO> {
        val materials = RetrofitClient.apiService.getStartedMaterials(userID)
        return materials
    }

    suspend fun getMaterialById(userID: Int,materialId: Int): MaterialDTO {
        val material = RetrofitClient.apiService.getMaterialById(userID,materialId)
        return material
    }

}
