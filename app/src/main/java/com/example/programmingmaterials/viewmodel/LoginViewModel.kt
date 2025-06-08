package com.example.programmingmaterials.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.programmingmaterials.AuthManager
import com.example.programmingmaterials.LoginRequest
import com.example.programmingmaterials.RetrofitClient
import com.example.programmingmaterials.model.LoginEvent
import com.example.programmingmaterials.model.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authManager: AuthManager
) : ViewModel() {

    private val initialState = LoginState()
    val state: MutableState<LoginState> = mutableStateOf(initialState)
    val event: MutableState<LoginEvent?> = mutableStateOf(null)

    init {
        if (authManager.isLoggedIn()) {
            navigateMain()
        }
    }

    fun onEditEmail(newEmailText: String) {
        state.value = state.value.copy(emailText = newEmailText)
    }

    fun onEditPassword(passwordText: String) {
        state.value = state.value.copy(passwordText = passwordText)
    }

    fun onClickButton() {
        viewModelScope.launch {
            state.value = state.value.copy(isProgress = true)

            val success = authManager.logIn(
                state.value.emailText,
                state.value.passwordText
            )

            state.value = state.value.copy(
                isProgress = false,
                isDataError = !success
            )

            if (success) {
                navigateMain()
            }
        }
    }

    private fun navigateMain() {
        event.value = LoginEvent.NavigateMain()
    }

    fun onRegButtonClick() {
        state.value = state.value.copy(isLoginScreen = false)
    }

    fun onEditFIOReg(newText: String) {
        state.value = state.value.copy(FIOValue = newText)
    }

    fun onClickRegButtonReg() {
        viewModelScope.launch {
            state.value = state.value.copy(
                isProgress = true,
                errorMessage = ""
            )

            val success = authManager.register(
                state.value.FIOValue,
                state.value.emailText,
                state.value.passwordText
            )

            state.value = state.value.copy(isProgress = false)

            if (!success) {
                state.value = state.value.copy(
                    errorMessage = "Ошибка регистрации. Проверьте данные",
                    isDataError = true
                )
            }
            else{
                state.value = state.value.copy(isProgress = true)

                val successLogIn = authManager.logIn(
                    state.value.emailText,
                    state.value.passwordText
                )

                state.value = state.value.copy(
                    isProgress = false,
                    isDataError = !successLogIn
                )

                if (successLogIn) {
                    navigateMain()
                }
            }
        }
    }

    fun onClickLoginButtonReg() {
        state.value = state.value.copy(isLoginScreen = true)
    }
}