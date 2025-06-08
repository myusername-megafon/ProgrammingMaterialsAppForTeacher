package com.example.programmingmaterials.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.programmingmaterials.navigation.Routes

class MainActivityViewModel : ViewModel() {
    private val initState = MainActivityState()
    val state = mutableStateOf(initState)

    fun navigateTo(route: Any) {
        state.value = state.value.copy(enabledScreen = route)
    }

    fun setBottomBarVisibility(visible: Boolean) {
        state.value = state.value.copy(showBottomBar = visible)
    }

}

data class MainActivityState(
    val enabledScreen: Any = Routes.Home,
    val showBottomBar: Boolean = true
)