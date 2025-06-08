package com.example.programmingmaterials.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.programmingmaterials.AuthManager
import com.example.programmingmaterials.data.repositories.MaterialRepo
import com.example.programmingmaterials.data.repositories.ProgressRepo
import com.example.programmingmaterials.model.UserProgressScreenState
import com.example.programmingmaterials.model.MaterialProgressUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProgressScreenViewModel @Inject constructor(
    private val materialRepo: MaterialRepo,
    private val progressRepo: ProgressRepo,
    private val authManager: AuthManager
) : ViewModel() {

    private val initialScreenState = UserProgressScreenState()
    val screenState = mutableStateOf(initialScreenState)


    init {
        viewModelScope.launch {
            val allCategories = progressRepo.getAllCategories()
            val allStatuses = progressRepo.getAllStatuses()
            val allMaterials = materialRepo.getAllMaterials(authManager.getUserId())
                .map {
                    MaterialProgressUiModel(
                        id = it.id,
                        materialName = it.name,
                        categoryName = it.category,
                        status = it.status ?: "Не начато"
                    )
                }

            screenState.value = screenState.value.copy(
                categoryMenuItemsList = allCategories.map { it.name },
                statusMenuItemsList = allStatuses,
                filteredMaterials = allMaterials,
                materialProgressList = allMaterials
            )
        }
    }

    private fun applyFilters(
        materials: List<MaterialProgressUiModel>,
        category: String?,
        status: String?
    ): List<MaterialProgressUiModel> {
        return materials
            .filter { material ->
                (category == "Category" || category == null || material.categoryName == category)
            }
            .filter { material ->
                (status == "Status" || status == null || material.status.equals(status, ignoreCase = true))
            }
    }

    fun onCategorySelected(category: String) {
        val newState = screenState.value.copy(
            selectedCategory = category,
            isCategoryMenuExpanded = false
        )

        screenState.value = newState.copy(
            filteredMaterials = applyFilters(
                materials = newState.materialProgressList,
                category = newState.selectedCategory,
                status = newState.selectedStatus
            )
        )
    }

    fun onStatusSelected(status: String) {
        val newState = screenState.value.copy(
            selectedStatus = status,
            isStatusMenuExpanded = false
        )

        screenState.value = newState.copy(
            filteredMaterials = applyFilters(
                materials = newState.materialProgressList,
                category = newState.selectedCategory,
                status = newState.selectedStatus
            )
        )
    }

    fun onClickStatusMenuButton() {
        screenState.value = screenState.value.copy(isStatusMenuExpanded = true)
    }

    fun onDismissStatusMenu() {
        screenState.value = screenState.value.copy(isStatusMenuExpanded = false)
    }

    fun onClickCategoryMenuButton() {
        screenState.value = screenState.value.copy(isCategoryMenuExpanded = true)
    }

    fun onDismissCategoryMenu() {
        screenState.value = screenState.value.copy(isCategoryMenuExpanded = false)
    }
}