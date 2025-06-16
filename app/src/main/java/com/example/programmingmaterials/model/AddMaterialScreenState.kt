package com.example.programmingmaterials.model

import com.example.programmingmaterials.data.DTOClasses.FeedbackDTO

data class AddMaterialScreenState(
    val nameMaterialText: String = "",
    val isExpandedTypeMenu: Boolean = false,
    val selectedType: String = "Выберите тип...",
    val materialText: String = "",
    val typesOfMaterials: List<String> = listOf("Теоретический материал","Видеоматериал","Практическое задание","Статья"),
    val categories: List<Category> = emptyList(),
    val selectedCategory: String = "Выберите категорию...",
    val isExpandedCategoryMenu: Boolean = false,
    val isLoading: Boolean = false
)