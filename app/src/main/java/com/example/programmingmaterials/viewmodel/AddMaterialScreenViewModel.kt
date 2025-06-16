package com.example.programmingmaterials.viewmodel

import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.programmingmaterials.AuthManager
import com.example.programmingmaterials.RetrofitClient
import com.example.programmingmaterials.data.DTOClasses.CreateMaterialRequest
import com.example.programmingmaterials.data.repositories.FeedbackRepo
import com.example.programmingmaterials.data.repositories.MaterialRepo
import com.example.programmingmaterials.model.AddMaterialScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddMaterialScreenViewModel @Inject constructor(
    private val materialRepo: MaterialRepo,
    private val authManager: AuthManager
) : ViewModel() {

    private val _state = mutableStateOf(AddMaterialScreenState())
    val state: State<AddMaterialScreenState> = _state

    init {
        viewModelScope.launch {
            val categories = materialRepo.getAllCategories()
            _state.value = _state.value.copy(categories = categories)
        }
    }

    fun onNameMaterialTextEdit(value: String) {
        _state.value = _state.value.copy(nameMaterialText = value)
    }

    fun onDismissTypeMenu() {
        _state.value = _state.value.copy(isExpandedTypeMenu = false)
    }

    fun onExpandTypeMenu() {
        _state.value = _state.value.copy(isExpandedTypeMenu = true)
    }

    fun onTypeSelected(type: String) {
        _state.value = _state.value.copy(selectedType = type, isExpandedTypeMenu = false)
    }

    fun onMaterialTextEdit(value: String) {
        _state.value = _state.value.copy(materialText = value)
    }

    fun onCategorySelected(category: String) {
        _state.value =
            _state.value.copy(selectedCategory = category, isExpandedCategoryMenu = false)
    }

    fun onDismissCategoryMenu() {
        _state.value = _state.value.copy(isExpandedCategoryMenu = false)
    }

    fun onExpandCategoryMenu() {
        _state.value = _state.value.copy(isExpandedCategoryMenu = true)
    }

    fun onCreateMaterialButtonClick() {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            val response = RetrofitClient.apiService.createMaterial(
                CreateMaterialRequest(
                    materialName = state.value.nameMaterialText,
                    typeMaterial = state.value.selectedType,
                    materialContent = state.value.materialText,
                    authorId = authManager.getAuthorId(),
                    categoryId = state.value.categories.find { it.name == state.value.selectedCategory }!!.id
                )
            )
        }
        _state.value = _state.value.copy(isLoading = false)
    }
}