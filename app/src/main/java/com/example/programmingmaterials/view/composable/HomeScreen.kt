package com.example.programmingmaterials.view.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.programmingmaterials.view.composable.cards.MaterialProgressCard2
import com.example.programmingmaterials.model.HomeScreenState
import com.example.programmingmaterials.navigation.Routes
import com.example.programmingmaterials.ui.theme.ProgrammingMaterialsTheme
import com.example.programmingmaterials.viewmodel.HomeScreenViewModel

@Composable
fun HomeScreen(navController: NavController) {
    ProgrammingMaterialsTheme {
        Scaffold() {
            it
            val viewModel = hiltViewModel<HomeScreenViewModel>()
            val screenState = viewModel.screenState

            HomeScreenContent(
                state = screenState.value,
                onMaterialClick = { materialId ->
                    navController.navigate(Routes.MaterialDetails.createRoute(materialId))
                },
                onSearchQueryChange = { query ->
                    viewModel.filterMaterials(query)
                }
            )
        }
    }
}

@Composable
fun HomeScreenContent(
    state: HomeScreenState,
    onMaterialClick: (Int) -> Unit,
    onSearchQueryChange: (String) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column {
            TextField(
                value = state.searchQuery,
                onValueChange = { query ->
                    onSearchQueryChange(query)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clip(MaterialTheme.shapes.medium),
                placeholder = {
                    Text(
                        "Поиск...",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            Text(
                text = "Начатые материалы",
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(8.dp)
            ) {
                items(state.filteredStartedMaterialsList) {
                    MaterialProgressCard2(
                        uiModel = it,
                        cardColor = MaterialTheme.colorScheme.surfaceVariant,
                        onClick = { onMaterialClick(it.id) }
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .clip(MaterialTheme.shapes.large)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(8.dp)
                .fillMaxSize()
        ) {
            Text(
                text = "Рекомендации",
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(8.dp)
            ) {
                items(state.filteredNewMaterialsList) {
                    MaterialProgressCard2(
                        uiModel = it,
                        cardColor = MaterialTheme.colorScheme.surface,
                        onClick = { onMaterialClick(it.id) }
                    )
                }
            }
        }
    }
}
@Composable
@Preview(showBackground = true)
fun MainScreenPreview() {
    ProgrammingMaterialsTheme {
        HomeScreenContent(HomeScreenState(),{}) {
        }
    }
}