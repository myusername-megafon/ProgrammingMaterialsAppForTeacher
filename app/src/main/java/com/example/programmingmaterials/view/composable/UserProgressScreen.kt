@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.programmingmaterials.view.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.programmingmaterials.model.UserProgressScreenState
import com.example.programmingmaterials.model.MaterialProgressUiModel
import com.example.programmingmaterials.ui.theme.ProgrammingMaterialsTheme
import com.example.programmingmaterials.view.composable.cards.MaterialProgressCard
import com.example.programmingmaterials.viewmodel.UserProgressScreenViewModel

@Composable
fun UserProgressScreen(navController: NavController) {
    ProgrammingMaterialsTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            "Прогресс",
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
                val viewModel: UserProgressScreenViewModel = hiltViewModel()
                UserProgressScreenContent(
                    state = viewModel.screenState.value,
                    navController = navController,
                    onStatusMenuButtonClick = { viewModel.onClickStatusMenuButton() },
                    onDismissStatusMenu = { viewModel.onDismissStatusMenu() },
                    onCategoryMenuButtonClick = { viewModel.onClickCategoryMenuButton() },
                    onDismissCategoryMenu = { viewModel.onDismissCategoryMenu() },
                    onCategorySelected = { viewModel.onCategorySelected(it) },
                    onStatusSelected = { viewModel.onStatusSelected(it) }
                )
            }
        }
    }
}

@Composable
fun UserProgressScreenContent(
    state: UserProgressScreenState,
    navController: NavController,
    onStatusMenuButtonClick: () -> Unit,
    onDismissStatusMenu: () -> Unit,
    onCategoryMenuButtonClick: () -> Unit,
    onDismissCategoryMenu: () -> Unit,
    onCategorySelected: (String) -> Unit,
    onStatusSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            Button(
                onClick = onStatusMenuButtonClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(Icons.AutoMirrored.Default.List, null)
                Text(
                    state.selectedStatus ?: "Статус",
                    style = MaterialTheme.typography.bodyMedium
                )
                DropdownMenu(
                    expanded = state.isStatusMenuExpanded,
                    onDismissRequest = onDismissStatusMenu,
                    modifier = Modifier.background(MaterialTheme.colorScheme.surface)
                ) {
                    state.statusMenuItemsList.forEach { status ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    status,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            },
                            onClick = { onStatusSelected(status) }
                        )
                    }
                }
            }

            Button(
                onClick = onCategoryMenuButtonClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(Icons.AutoMirrored.Default.List, null)
                Text(
                    state.selectedCategory ?: "Категория",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium
                )
                DropdownMenu(
                    expanded = state.isCategoryMenuExpanded,
                    onDismissRequest = onDismissCategoryMenu,
                    modifier = Modifier.background(MaterialTheme.colorScheme.surface)
                ) {
                    state.categoryMenuItemsList.map { category ->
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
        }

        LazyColumn (modifier = Modifier.padding(horizontal = 8.dp)){
            items(state.filteredMaterials) { uiModel ->
                MaterialProgressCard(
                    uiModel = uiModel,
                    onClick = { materialId ->
                        navController.navigate("material_details/$materialId")
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewScreen() {
    ProgrammingMaterialsTheme {
        UserProgressScreenContent(
            state = UserProgressScreenState(
                materialProgressList = listOf(
                    MaterialProgressUiModel(1, "Material 1", "Category 1", "Started", null),
                    MaterialProgressUiModel(2, "Material 2", "Category 2", "Started", null),
                )
            ),
            navController = rememberNavController(),
            onStatusMenuButtonClick = {},
            onDismissStatusMenu = {},
            onCategoryMenuButtonClick = {},
            onDismissCategoryMenu = {},
            onCategorySelected = {},
            onStatusSelected = {}
        )
    }
}