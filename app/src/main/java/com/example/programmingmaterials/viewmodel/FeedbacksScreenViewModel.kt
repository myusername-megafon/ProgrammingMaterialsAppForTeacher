package com.example.programmingmaterials.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.programmingmaterials.AuthManager
import com.example.programmingmaterials.data.repositories.FeedbackRepo
import com.example.programmingmaterials.model.FeedbacksScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedbacksScreenViewModel @Inject constructor(
    private val feedbackRepo: FeedbackRepo,
    private val authManager: AuthManager
) : ViewModel() {

    private val _state = mutableStateOf(FeedbacksScreenState())
    val state: State<FeedbacksScreenState> = _state

    init {
        viewModelScope.launch {
            val userFeedbacks = feedbackRepo.getUserFeedbacks(authManager.getUserId())
            _state.value = _state.value.copy(feedbacksList = userFeedbacks)
        }
    }

    fun onClickDeleteButton(feedbackId: Int) {
        viewModelScope.launch {
            feedbackRepo.deleteFeedback(feedbackId)
            val updatedFeedbacks = feedbackRepo.getUserFeedbacks(authManager.getUserId())
            _state.value = _state.value.copy(feedbacksList = updatedFeedbacks)
        }
    }
}