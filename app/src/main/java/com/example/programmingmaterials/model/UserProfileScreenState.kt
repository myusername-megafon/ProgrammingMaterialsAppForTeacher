package com.example.programmingmaterials.model

data class UserProfileScreenState(
    val userName: String = "User",
    val finishedMaterials: Int = 0,
    val pendingMaterials: Int = 0,
    val startedMaterials: Int = 0
) {

}