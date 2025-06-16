package com.example.programmingmaterials.view.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
            if (screenState.value.isLoading) {
                HomeScreenContent(
                    state = screenState.value,
                    onMaterialClick = { materialId ->
                        navController.navigate(Routes.MaterialDetails.createRoute(materialId))
                    },
                    onSearchQueryChange = { query ->
                        viewModel.filterMaterials(query)
                    }
                )
            } else CircularProgressIndicator()
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
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
    ) {
        Text(
            text = "Главная",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Icon(
            imageVector = Icons.Default.Face,
            contentDescription = null,
            modifier = Modifier
                .size(116.dp)
                .padding(8.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Text(
            text = state.authorName,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Text(
            text = state.authorMail,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Text(
            text = "Создано материалов:" + state.createdMaterials,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(vertical = 8.dp)
        )


        Column(
            modifier = Modifier
                .clip(MaterialTheme.shapes.large)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(8.dp)
                .fillMaxSize()
        ) {
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

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(8.dp)
            ) {
                items(state.filteredNewMaterialsList) {
                    MaterialProgressCard2(
                        uiModel = it,
                        cardColor = MaterialTheme.colorScheme.surfaceVariant,
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
        HomeScreenContent(HomeScreenState(), {}) {
        }
    }
}