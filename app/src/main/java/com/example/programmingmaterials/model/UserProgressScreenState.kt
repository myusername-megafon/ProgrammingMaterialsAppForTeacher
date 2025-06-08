package com.example.programmingmaterials.model

data class UserProgressScreenState(
    val isStatusMenuExpanded: Boolean = false,
    val isCategoryMenuExpanded: Boolean = false,
    val onStatusMenuButtonClick: () -> Unit = {},
    val onCategoryMenuButtonClick: () -> Unit = {},
    val materialProgressList: List<MaterialProgressUiModel> = listOf(),
    val statusMenuItemsList: List<String> = listOf("status1","status2"),
    val categoryMenuItemsList: List<String> = listOf("category1","category2"),
    val selectedCategory: String? = null,
    val selectedStatus: String? = null,
    val filteredMaterials: List<MaterialProgressUiModel> = emptyList()
)
