package com.example.programmingmaterials.view.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.programmingmaterials.view.composable.cards.AddFeedbackDialog
import com.example.programmingmaterials.view.composable.cards.ReviewCard
import com.example.programmingmaterials.model.MaterialDetailsScreenState
import com.example.programmingmaterials.ui.theme.ProgrammingMaterialsTheme
import com.example.programmingmaterials.viewmodel.MaterialDetailsViewModel

@Composable
fun MaterialDetailsScreen(
    materialId: Int,
    viewModel: MaterialDetailsViewModel = hiltViewModel(),
    navController: NavController
) {
    LaunchedEffect(Unit) {
        viewModel.loadReviews()
    }
    ProgrammingMaterialsTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { paddingValues ->
            MaterialDetailsScreenContent(
                state = viewModel.state.value,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                navController = navController,
                onStartButtonClick = { viewModel.handleStartButtonClick() },
                onSecondButtonClick = { viewModel.handleSecondButtonClick() },
                onAddReviewClick = { viewModel.openAddFeedbackDialog() }
            )
            if (viewModel.state.value.showAddFeedbackDialog) {
                AddFeedbackDialog(
                    onDismiss = { viewModel.closeAddFeedbackDialog() },
                    onConfirm = { text, rating ->
                        viewModel.addReview(text, rating)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaterialDetailsScreenContent(
    state: MaterialDetailsScreenState,
    modifier: Modifier,
    onStartButtonClick: () -> Unit,
    onSecondButtonClick: () -> Unit,
    navController: NavController,
    onAddReviewClick: () -> Unit
) {
    ProgrammingMaterialsTheme {
        Column(
            modifier = modifier
                .padding(16.dp)
                .navigationBarsPadding()
        ) {
            TopAppBar(
                title = {
                    Text(
                        text = state.materialName,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = state.categoryName,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                    modifier = Modifier.weight(0.5f),
                    overflow = TextOverflow.Visible,
                    maxLines = 2
                )
                Text(
                    text = state.authorName,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                    modifier = Modifier.weight(0.5f),
                    overflow = TextOverflow.Visible,
                    maxLines = 2
                )
            }

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 16.dp)
            ) {
                item {
                    Text(
                        text = state.materialText,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Spacer(Modifier.size(16.dp))
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Отзывы",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                        IconButton(
                            onClick = { onAddReviewClick() },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Добавить отзыв",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }

                items(state.reviews) { review ->
                    ReviewCard(
                        review = review,
                        usageScreen = "DetailsMaterial",
                        onDeleteButtonClick = {}
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                Button(
                    onClick = { onStartButtonClick() },
                    modifier = Modifier
                        .weight(0.8f)
                        .height(48.dp),
                    enabled = state.status != "Завершено",
                    colors = ButtonDefaults.buttonColors(
                        containerColor = when (state.status) {
                            "Завершено" -> MaterialTheme.colorScheme.surfaceVariant
                            else -> MaterialTheme.colorScheme.primary
                        },
                        contentColor = when (state.status) {
                            "Завершено" -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                            else -> MaterialTheme.colorScheme.onPrimary
                        }
                    )
                ) {
                    Text(
                        text = when (state.status) {
                            "В процессе" -> "Завершить"
                            "Отложено" -> "Возобновить"
                            "Завершено" -> "Завершено"
                            else -> "Начать"
                        },
                        style = MaterialTheme.typography.labelLarge
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                if (state.status != "Завершено") {
                    Button(
                        onClick = { onSecondButtonClick() },
                        modifier = Modifier
                            .weight(0.2f)
                            .height(48.dp),
                        enabled = state.status == "В процессе" || state.status == "Не начато",
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (state.status == "В процессе" || state.status == "Не начато")
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.surfaceVariant,
                            contentColor = if (state.status == "В процессе" || state.status == "Не начато")
                                MaterialTheme.colorScheme.onPrimary
                            else
                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                        )
                    ) {
                        Icon(
                            imageVector = if (state.status == "В процессе")
                                Icons.Default.Add
                            else
                                Icons.Default.DateRange,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MaterialDetailsScreenContentPreview() {
    val fakeState = MaterialDetailsScreenState(
        materialName = "Пример материала",
        categoryName = "Программирование",
        authorName = "Иван Иванов",
        materialText = "Это пример текста материала для превью",
        status = "В процессе",
        reviews = listOf(),
        showAddFeedbackDialog = false
    )

    ProgrammingMaterialsTheme {
        MaterialDetailsScreenContent(
            state = fakeState,
            modifier = Modifier.fillMaxSize(),
            onStartButtonClick = {},
            onSecondButtonClick = {},
            navController = rememberNavController(),
            onAddReviewClick = { }
        )
    }
}
