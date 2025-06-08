package com.example.programmingmaterials.view.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import com.example.programmingmaterials.model.FeedbacksScreenState
import com.example.programmingmaterials.ui.theme.ProgrammingMaterialsTheme
import com.example.programmingmaterials.view.composable.cards.ReviewCard
import com.example.programmingmaterials.viewmodel.FeedbacksScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedbacksScreen(navController: NavController) {
    ProgrammingMaterialsTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            "Мои Отзывы",
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
                val viewModel = hiltViewModel<FeedbacksScreenViewModel>()
                FeedbacksScreenContent(
                    state = viewModel.state.value,
                    navController = navController,
                    onClickDeleteButton = { viewModel.onClickDeleteButton(it) }
                )
            }
        }
    }
}

@Composable
fun FeedbacksScreenContent(
    state: FeedbacksScreenState,
    navController: NavController,
    onClickDeleteButton: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LazyColumn(
            modifier = Modifier.padding(16.dp)
        ) {
            items(state.feedbacksList) {
                ReviewCard(
                    review = it,
                    usageScreen = "FeedbacksScreen",
                    onDeleteButtonClick = onClickDeleteButton
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FeedbacksScreenPreview() {
    ProgrammingMaterialsTheme {
        FeedbacksScreen(rememberNavController())
    }
}