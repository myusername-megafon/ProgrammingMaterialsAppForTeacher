package com.example.programmingmaterials.model

data class LoginState(
    val emailText: String = "",
    val passwordText: String = "",
    val isProgress: Boolean = false,
    val isDataError: Boolean = false,
    val isLoginScreen: Boolean = true,
    val FIOValue: String = "",
    val errorMessage: String = ""
)