package com.example.programmingmaterials.navigation

import kotlinx.serialization.Serializable

object Routes {

    @Serializable
    object Home

    @Serializable
    object Login

    @Serializable
    object UserProgress

    @Serializable
    object UserProfile

    @Serializable
    object UserFeedbacks

    @Serializable
    object Tests

    @Serializable
    data class MaterialDetails(val materialId: Int) {
        companion object {
            const val route = "material_details/{materialId}"
            fun createRoute(materialId: Int) = "material_details/$materialId"
        }
    }
}