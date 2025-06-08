package com.example.programmingmaterials.view.composable

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.programmingmaterials.view.MainActivity
import com.example.programmingmaterials.model.LoginEvent
import com.example.programmingmaterials.model.LoginState
import com.example.programmingmaterials.ui.theme.ProgrammingMaterialsTheme
import com.example.programmingmaterials.viewmodel.LoginViewModel

@Composable
fun LoginScreen() {
    ProgrammingMaterialsTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val viewModel = hiltViewModel<LoginViewModel>()
            val screenState = viewModel.state
            ConsumeEvents(viewModel)

            Box(modifier = Modifier.padding(innerPadding)) {
                LoginContent(
                    screenState = screenState.value,
                    onEditEmail = { newEmail -> viewModel.onEditEmail(newEmail) },
                    onEditPassword = viewModel::onEditPassword,
                    onButtonClick = { viewModel.onClickButton() },
                    onRegButtonClick = { viewModel.onRegButtonClick() },
                    onEditFIOReg = { newText -> viewModel.onEditFIOReg(newText) },
                    onClickLoginButtonReg = { viewModel.onClickLoginButtonReg() },
                    onClickRegButtonReg = { viewModel.onClickRegButtonReg() },
                )
            }
        }
    }
}

@Composable
private fun ConsumeEvents(viewModel: LoginViewModel) {
    val event = viewModel.event.value
    if (event?.isConsumed == false) {
        event.isConsumed = true
        if (event is LoginEvent.NavigateMain) {
            val activity: Activity = LocalActivity.current!!
            activity.startActivity(Intent(activity, MainActivity::class.java))
        }
    }
}

@Composable
private fun LoginContent(
    screenState: LoginState,
    onEditEmail: (String) -> Unit = {},
    onEditPassword: (String) -> Unit = {},
    onButtonClick: () -> Unit = {},
    onRegButtonClick: () -> Unit = {},
    onEditFIOReg: (String) -> Unit = {},
    onClickRegButtonReg: () -> Unit = {},
    onClickLoginButtonReg: () -> Unit = {}
) {
    when {
        !screenState.isLoginScreen -> {
            if (screenState.isProgress) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                Column(
                    Modifier.padding(horizontal = 32.dp).fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedTextField(
                        isError = screenState.isDataError,
                        value = screenState.FIOValue,
                        label = { Text("ФИО") },
                        onValueChange = onEditFIOReg,
                        maxLines = 1
                    )
                    OutlinedTextField(
                        isError = screenState.isDataError,
                        value = screenState.emailText,
                        label = { Text("Email") },
                        onValueChange = onEditEmail,
                        maxLines = 1
                    )
                    OutlinedTextField(
                        isError = screenState.isDataError,
                        visualTransformation = PasswordVisualTransformation(),
                        value = screenState.passwordText,
                        label = { Text("Пароль") },
                        onValueChange = onEditPassword,
                        maxLines = 1
                    )
                    Spacer(Modifier.size(8.dp))
                    Button(
                        onClick = { onClickRegButtonReg() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Text("Зарегистрироваться", style = MaterialTheme.typography.labelLarge)
                    }
                    Text(text = "или", color = MaterialTheme.colorScheme.onSurface)
                    Button(
                        onClick = onClickLoginButtonReg,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = MaterialTheme.colorScheme.onSecondary
                        )
                    ) {
                        Text("Войти", style = MaterialTheme.typography.labelLarge)
                    }
                }
            }
        }

        screenState.isLoginScreen -> {
            if (screenState.isProgress) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                Column(
                    Modifier.padding(horizontal = 32.dp).fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedTextField(
                        isError = screenState.isDataError,
                        value = screenState.emailText,
                        label = { Text("Email") },
                        onValueChange = onEditEmail,
                        maxLines = 1
                    )
                    OutlinedTextField(
                        isError = screenState.isDataError,
                        visualTransformation = PasswordVisualTransformation(),
                        value = screenState.passwordText,
                        label = { Text("Пароль") },
                        onValueChange = onEditPassword,
                        maxLines = 1
                    )
                    Spacer(Modifier.size(8.dp))
                    Button(
                        onClick = onButtonClick,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Text("Войти", style = MaterialTheme.typography.labelLarge)
                    }
                    Text(text = "или", color = MaterialTheme.colorScheme.onSurface)
                    Button(
                        onClick = onRegButtonClick,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = MaterialTheme.colorScheme.onSecondary
                        )
                    ) {
                        Text("Зарегистрироваться", style = MaterialTheme.typography.labelLarge)
                    }
                }
            }
        }

        else -> {}
    }
}


@Composable
@Preview(showBackground = true)
private fun Preview() {
    ProgrammingMaterialsTheme {
        LoginContent(
            LoginState(
                emailText = "this is preview"
            )
        )
    }
}
