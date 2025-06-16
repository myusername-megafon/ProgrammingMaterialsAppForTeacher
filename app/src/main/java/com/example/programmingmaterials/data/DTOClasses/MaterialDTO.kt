package com.example.programmingmaterials.data.DTOClasses

import com.google.gson.annotations.SerializedName

data class MaterialDTO(
    @SerializedName("iD_Material") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("category") val category: String,
    @SerializedName("author") val author: String,
    @SerializedName("content") val content: String,
    @SerializedName("status") val status: String?
)

data class CreateMaterialRequest(
    val materialName: String,
    val typeMaterial: String,
    val materialContent: String,
    val authorId: Int,
    val categoryId: Int
)

data class CreateMaterialResponse(
    val message: String
)


