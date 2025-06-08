package com.example.programmingmaterials.viewmodel

import android.app.Activity
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.programmingmaterials.AuthManager
import com.example.programmingmaterials.data.repositories.ProfileRepo
import com.example.programmingmaterials.model.UserProfileScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileScreenViewModel @Inject constructor(
    private val profileRepo: ProfileRepo,
    private val authManager: AuthManager
) : ViewModel() {
    private val initState = UserProfileScreenState()
    val state = mutableStateOf(initState)

    init {
        viewModelScope.launch {
            val user = profileRepo.getUser(authManager.getUserId())
            val userProgressInNumbers = profileRepo.getUserProgressInNumbers(authManager.getUserId())
            state.value = state.value.copy(
                userName = user.name,
                startedMaterials = userProgressInNumbers.startedMaterials,
                pendingMaterials = userProgressInNumbers.pendingMaterials,
                finishedMaterials = userProgressInNumbers.finishedMaterials
            )
        }
    }
    fun logOut(){
        authManager.logout()
    }
}