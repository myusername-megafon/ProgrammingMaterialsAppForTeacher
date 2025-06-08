package com.example.programmingmaterials.view.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.programmingmaterials.model.TestScreenState
import com.example.programmingmaterials.ui.theme.ProgrammingMaterialsTheme
import com.example.programmingmaterials.view.composable.cards.TestDialog
import com.example.programmingmaterials.viewmodel.TestScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestScreen(navController: NavController) {
    ProgrammingMaterialsTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            "Тесты",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                )
            }
        ) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                val viewModel = hiltViewModel<TestScreenViewModel>()
                TestScreenContent(
                    state = viewModel.state.value,
                    navController = navController,
                    onDismissCategoryMenu = { viewModel.onDismissCategoryMenu() },
                    onCategorySelected = { viewModel.onCategorySelected(it) },
                    onClickCategoryMenu = { viewModel.onClickCategoryMenuButton() },
                    onDismissDialog = { viewModel.onDismissDialog() },
                    onResultSelected = { viewModel.onResultSelected(it) },
                    onDialogShow = { viewModel.showResultDialog() }
                )
            }
        }
    }
}

@Composable
fun TestScreenContent(
    state: TestScreenState,
    navController: NavController,
    onDismissCategoryMenu: () -> Unit,
    onCategorySelected: (String) -> Unit,
    onClickCategoryMenu: () -> Unit,
    onDismissDialog: () -> Unit,
    onResultSelected: (Int) -> Unit,
    onDialogShow: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Button(
            onClick = onClickCategoryMenu,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.AutoMirrored.Default.List, null)
            Text(state.selectedCategory ?: "Категория", style = MaterialTheme.typography.bodyMedium)

            DropdownMenu(
                expanded = state.isCategoryMenuExpanded,
                onDismissRequest = onDismissCategoryMenu,
                modifier = Modifier.background(MaterialTheme.colorScheme.surface)
            ) {
                state.categoryList.forEach { category ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                category,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        },
                        onClick = { onCategorySelected(category) }
                    )
                }
            }
        }

        if (state.selectedCategory != null) {
            Text(
                text = "Сложность:" + when (state.difficulty) {
                    1 -> " Очень легко"
                    2 -> " Легко"
                    3 -> " Средне"
                    4 -> " Тяжело"
                    5 -> " Очень тяжело"
                    else -> " difficulty"
                },
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        Text(
            text = state.testContent,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        if(state.testContent != "")
        Button(
            onClick = onDialogShow,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Выполнено", style = MaterialTheme.typography.bodyLarge)
        }

        if (state.isShowDialog) {
            TestDialog(
                onDismissDialog = onDismissDialog,
                onResultSelected = onResultSelected
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TestScreenPreview() {
    ProgrammingMaterialsTheme {
        TestScreenContent(
            state = TestScreenState(
                categoryList = listOf("category"),
                testContent = "is a test. 1. its first condition. 2. its second condition. 3. its third condition."
            ),
            navController = rememberNavController(),
            onDismissCategoryMenu = {},
            onCategorySelected = {},
            onClickCategoryMenu = {},
            onDismissDialog = {},
            onResultSelected = {},
            onDialogShow = {}
        )
    }
}