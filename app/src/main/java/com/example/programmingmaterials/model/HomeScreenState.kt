package com.example.programmingmaterials.model

data class HomeScreenState(
    val authorName: String = "UserName",
    val authorMail: String = "UserMail",
    val createdMaterials: Int = 0,
    val newMaterialsList: List<MaterialProgressUiModel> = listOf(),
    val searchQuery: String = "",
    val filteredNewMaterialsList: List<MaterialProgressUiModel> = listOf(),
    val isLoading: Boolean = false)
