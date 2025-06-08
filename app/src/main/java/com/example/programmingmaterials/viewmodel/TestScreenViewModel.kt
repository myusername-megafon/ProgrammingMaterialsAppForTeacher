package com.example.programmingmaterials.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.programmingmaterials.AuthManager
import com.example.programmingmaterials.data.repositories.ProgressRepo
import com.example.programmingmaterials.data.repositories.TestRepo
import com.example.programmingmaterials.model.TestScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestScreenViewModel @Inject constructor(
    private val progressRepo: ProgressRepo,
    private val testRepo: TestRepo,
    private val authManager: AuthManager
) : ViewModel() {
    private val _state = mutableStateOf(TestScreenState())
    val state: State<TestScreenState> = _state

    init {
        viewModelScope.launch {
            val allCategory = progressRepo.getAllCategories()
            _state.value = _state.value.copy(categoryList = allCategory.map { it.name })
        }
    }

    fun onClickCategoryMenuButton() {
        _state.value = _state.value.copy(isCategoryMenuExpanded = true)
    }

    fun onDismissCategoryMenu() {
        _state.value = _state.value.copy(isCategoryMenuExpanded = false)
    }

    fun onCategorySelected(category: String) {
        _state.value =
            _state.value.copy(selectedCategory = category, isCategoryMenuExpanded = false)
        viewModelScope.launch {
            val test = testRepo.getTest(userId = authManager.getUserId(), categoryName = category)
            _state.value =
                _state.value.copy(
                    testContent = test.content_Test,
                    difficulty = test.difficulty,
                    idTest = test.iD_Test
                )
        }
    }

    fun onDismissDialog() {
        _state.value = _state.value.copy(isShowDialog = false)
    }

    fun showResultDialog() {
        _state.value = _state.value.copy(isShowDialog = true)
    }

    fun onResultSelected(result: Int) {
        viewModelScope.launch {
            try {
                val test = testRepo.getTest(authManager.getUserId(), state.value.selectedCategory ?: "Category")

                testRepo.saveTestResult(
                    userId = authManager.getUserId(),
                    testId = test.iD_Test,
                    result = result
                )

                _state.value = _state.value.copy(
                    selectResult = result,
                    isShowDialog = false
                )

            } catch (e: Exception) {
                throw e
            }
        }
    }
}
