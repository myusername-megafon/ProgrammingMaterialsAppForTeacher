package com.example.programmingmaterials.data.DTOClasses

import com.google.gson.annotations.SerializedName

data class ProgressDTO(
    @SerializedName("ID_User") val userId: Int,
    val startedMaterials: Int,
    val pendingMaterials: Int,
    val finishedMaterials: Int
)

data class ProgressRequestDTO(
    @SerializedName("userId") val userId: Int,
    @SerializedName("materialId") val materialId: Int,
    @SerializedName("statusId") val statusId: Int
)

data class UpdateProgressRequestDTO(
    @SerializedName("userId") val userId: Int,
    @SerializedName("materialId") val materialId: Int,
    @SerializedName("newStatusId") val statusId: Int
)
