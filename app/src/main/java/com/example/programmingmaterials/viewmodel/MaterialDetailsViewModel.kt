package com.example.programmingmaterials.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.programmingmaterials.AuthManager
import com.example.programmingmaterials.data.repositories.FeedbackRepo
import com.example.programmingmaterials.data.repositories.MaterialRepo
import com.example.programmingmaterials.data.repositories.ProgressRepo
import com.example.programmingmaterials.model.MaterialDetailsScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MaterialDetailsViewModel @Inject constructor(
    materialRepo: MaterialRepo,
    private val progressRepo: ProgressRepo,
    private val feedbackRepo: FeedbackRepo,
    private val authManager: AuthManager,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val initState = MaterialDetailsScreenState()
    val state = mutableStateOf(initState)

    init {
        viewModelScope.launch {
            val materialId = savedStateHandle.get<Int>("materialId")
            val material = materialRepo.getMaterialById(materialId = materialId!!, userID = authManager.getUserId())
            state.value = state.value.copy(
                materialName = material.name,
                categoryName = material.category,
                authorName = material.author,
                materialText = material.content,
                status = material.status ?: "Не начато"
            )
        }
    }

    fun handleStartButtonClick() {
        viewModelScope.launch {
            try {
                when (state.value.status) {
                    "Не начато" -> {
                        progressRepo.createProgressEntry(
                            userId = authManager.getUserId(),
                            materialId = savedStateHandle.get<Int>("materialId")!!,
                            status = "В процессе"
                        )
                        updateState("В процессе")
                    }

                    "Отложено" -> {
                        progressRepo.updateProgressStatus(
                            userId = authManager.getUserId(),
                            materialId = savedStateHandle.get<Int>("materialId")!!,
                            newStatus = "В процессе"
                        )
                        updateState("В процессе")
                    }

                    "В процессе" -> {
                        progressRepo.updateProgressStatus(
                            userId = authManager.getUserId(),
                            materialId = savedStateHandle.get<Int>("materialId")!!,
                            newStatus = "Завершено"
                        )
                        updateState("Завершено")
                    }
                }
            } catch (e: Exception) {
                throw e
            }
        }
    }

    fun handleSecondButtonClick() {
        viewModelScope.launch {
            try {
                when (state.value.status) {
                    "В процессе" -> {
                        progressRepo.updateProgressStatus(
                            userId = authManager.getUserId(),
                            materialId = savedStateHandle.get<Int>("materialId")!!,
                            newStatus = "Отложено"
                        )
                        updateState("Отложено")
                    }
                    "Не начато" -> {
                        progressRepo.createProgressEntry(
                            userId = authManager.getUserId(),
                            materialId = savedStateHandle.get<Int>("materialId")!!,
                            status = "Отложено"
                        )
                        updateState("Отложено")
                    }

                }
            } catch (e: Exception) {
                throw e
            }
        }
    }

    private fun updateState(newStatus: String) {
        state.value = state.value.copy(
            status = newStatus
        )
    }

    fun loadReviews() {
        viewModelScope.launch {
            try {
                val reviews = feedbackRepo.getMaterialFeedbacks(
                    savedStateHandle.get<Int>("materialId")!!
                )
                state.value = state.value.copy(
                    reviews = reviews
                )
            } catch (e: Exception) {
                throw e
            }
        }
    }

    fun addReview(content: String, rating: Int) {
        viewModelScope.launch {
            try {
                feedbackRepo.AddFeedback(
                    materialId = savedStateHandle.get<Int>("materialId")!!,
                    userId = 1, // Замените на реальный ID пользователя
                    content = content,
                    rating = rating

                )
                loadReviews()
                closeAddFeedbackDialog()
            } catch (e: Exception) {
                throw e
            }
        }
    }

    fun openAddFeedbackDialog() {
        state.value = state.value.copy(showAddFeedbackDialog = true)
    }

    fun closeAddFeedbackDialog() {
        state.value = state.value.copy(showAddFeedbackDialog = false)
    }
}

