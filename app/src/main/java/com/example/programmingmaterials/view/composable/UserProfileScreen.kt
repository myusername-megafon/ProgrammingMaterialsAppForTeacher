package com.example.programmingmaterials.view.composable

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.programmingmaterials.model.UserProfileScreenState
import com.example.programmingmaterials.navigation.Routes
import com.example.programmingmaterials.ui.theme.ProgrammingMaterialsTheme
import com.example.programmingmaterials.viewmodel.UserProfileScreenViewModel

@Composable
fun UserProfileScreen(navHostController: NavHostController) {
    ProgrammingMaterialsTheme {
        Scaffold {
            it
            val viewModel = hiltViewModel<UserProfileScreenViewModel>()
            val state = viewModel.state.value
            val context = LocalContext.current
            UserProfileScreenContent(
                state = state,
                onUserMailTextEdit = { viewModel.onEmailUserTextEdit(it) },
                onButtonClick = { viewModel.onButtonClick() },
                onExportToPdf = { viewModel.exportToPdf(context, state) }
            )
        }
    }
}

@Composable
fun UserProfileScreenContent(
    state: UserProfileScreenState,
    onUserMailTextEdit: (String) -> Unit,
    onButtonClick: () -> Unit,
    onExportToPdf: () -> Unit
) {
    if (state.isLoading) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Text(
                modifier = Modifier.padding(bottom = 8.dp),
                text = "Введите email пользователя, чтобы получить отчет по его прогрессу:",
                textAlign = TextAlign.Center
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.userMailText,
                onValueChange = { onUserMailTextEdit(it) },
                label = { Text(text = "Email") })

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                onClick = onButtonClick
            ) {
                Text(text = "Получить отчет")
            }

            if (state.isDataValid) {
                Text(
                    text = "ОТЧЕТ",
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.secondary
                )
                Text(text = "Дата отчета:" + state.currentDate)
                Text(text = "Пользователь:" + state.userName)
                Text(text = "Завершено материалов:" + state.finishedMaterials)
                Text(text = "Отложено материалов:" + state.pendingMaterials)
                Text(text = "Начато материалов:" + state.startedMaterials)
                Text(text = "Средняя сложность материалов:" + state.difficultyAVG)
                Text(text = "Выполнено тестов:" + state.finishedTests)
                Text(text = "Среднее число выполненных условий теста:" + state.finishedTestsAVG)

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onExportToPdf,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    )
                ) {
                    Text(text = "Экспортировать в PDF")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserProfileScreenPreview() {
    ProgrammingMaterialsTheme {
        UserProfileScreenContent(UserProfileScreenState(), {}, {}, {})
    }
}