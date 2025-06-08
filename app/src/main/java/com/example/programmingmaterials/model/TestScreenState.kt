package com.example.programmingmaterials.model

data class TestScreenState(
    val categoryList: List<String> = emptyList(),
    val selectedCategory: String? = null,
    val isCategoryMenuExpanded: Boolean = false,
    val idTest: Int = 0,
    val difficulty: Int = 0,
    val testContent: String = "",
    val isShowDialog: Boolean = false,
    val selectResult: Int = 0
)
