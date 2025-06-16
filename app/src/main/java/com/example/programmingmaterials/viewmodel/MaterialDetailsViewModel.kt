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
            val material = materialRepo.getMaterialById(materialId = materialId!!, userID = authManager.getAuthorId())
            state.value = state.value.copy(
                materialName = material.name,
                categoryName = material.category,
                authorName = material.author,
                materialText = material.content,
                status = material.status ?: "Не начато"
            )
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

}

