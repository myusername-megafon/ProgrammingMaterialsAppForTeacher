package com.example.programmingmaterials.view.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.programmingmaterials.model.AddMaterialScreenState
import com.example.programmingmaterials.ui.theme.ProgrammingMaterialsTheme
import com.example.programmingmaterials.view.composable.cards.ReviewCard
import com.example.programmingmaterials.viewmodel.AddMaterialScreenViewModel

@Composable
fun AddMaterialScreen() {
    ProgrammingMaterialsTheme {
        Scaffold {
            it
            val viewModel = hiltViewModel<AddMaterialScreenViewModel>()
            AddMaterialScreenContent(
                state = viewModel.state.value,
                onNameMaterialTextEdit = { newValue -> viewModel.onNameMaterialTextEdit(newValue) },
                onExpandTypeMenu = { viewModel.onExpandTypeMenu() },
                onDismissTypeMenu = { viewModel.onDismissTypeMenu() },
                onTypeSelected = { type -> viewModel.onTypeSelected(type) },
                onMaterialTextEdit = { newValue -> viewModel.onMaterialTextEdit(newValue) },
                onCategorySelected = { category -> viewModel.onCategorySelected(category) },
                onDismissCategoryMenu = { viewModel.onDismissCategoryMenu() },
                onExpandCategoryMenu = { viewModel.onExpandCategoryMenu() },
                onCreateButtonClick = { viewModel.onCreateMaterialButtonClick() }
            )
        }
    }
}


@Composable
fun AddMaterialScreenContent(
    state: AddMaterialScreenState,
    onNameMaterialTextEdit: (String) -> Unit,
    onDismissTypeMenu: () -> Unit,
    onExpandTypeMenu: () -> Unit,
    onTypeSelected: (String) -> Unit,
    onMaterialTextEdit: (String) -> Unit,
    onCategorySelected: (String) -> Unit,
    onDismissCategoryMenu: () -> Unit,
    onExpandCategoryMenu: () -> Unit,
    onCreateButtonClick: () -> Unit
) {
    if (state.isLoading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = Modifier
                .padding(bottom = 100.dp, start = 16.dp, end = 16.dp, top = 16.dp)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            OutlinedTextField(
                value = state.nameMaterialText,
                onValueChange = { onNameMaterialTextEdit(it) },
                placeholder = { Text(text = "Введите имя материала...") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.size(8.dp))

            OutlinedTextField(
                value = state.materialText,
                onValueChange = { onMaterialTextEdit(it) },
                placeholder = { Text(text = "Введите содержание материала...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )

            Row {
                Button(
                    onClick = onExpandTypeMenu,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .weight(0.45f)
                ) {
                    Text(text = state.selectedType, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    DropdownMenu(
                        expanded = state.isExpandedTypeMenu,
                        onDismissRequest = onDismissTypeMenu,
                    )
                    {
                        state.typesOfMaterials.forEach {
                            DropdownMenuItem(
                                text =
                                {
                                    Text(
                                        text = it,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                },
                                onClick = { onTypeSelected(it) }
                            )
                        }
                    }
                }


                Button(
                    onClick = onExpandCategoryMenu,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier
                        .padding(start = 8.dp, top = 8.dp, bottom = 8.dp)
                        .weight(0.6f)
                ) {
                    Text(
                        text = state.selectedCategory,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    DropdownMenu(
                        expanded = state.isExpandedCategoryMenu,
                        onDismissRequest = onDismissCategoryMenu,
                    )
                    {
                        state.categories.forEach {
                            DropdownMenuItem(
                                text =
                                {
                                    Text(
                                        text = it.name,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                },
                                onClick = { onCategorySelected(it.name) }
                            )
                        }
                    }
                }
            }

            Button(onClick = onCreateButtonClick, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Создать")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddMaterialScreenPreview() {
    AddMaterialScreenContent(
        AddMaterialScreenState(), {}, {}, {}, {}, {}, {}, {}, {}, {})
}