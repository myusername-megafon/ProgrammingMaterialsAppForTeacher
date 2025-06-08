package com.example.programmingmaterials.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.programmingmaterials.view.composable.HomeScreen
import com.example.programmingmaterials.view.composable.MaterialDetailsScreen
import com.example.programmingmaterials.view.composable.UserProfileScreen
import com.example.programmingmaterials.view.composable.UserProgressScreen
import com.example.programmingmaterials.navigation.Routes
import com.example.programmingmaterials.ui.theme.ProgrammingMaterialsTheme
import com.example.programmingmaterials.view.composable.FeedbacksScreen
import com.example.programmingmaterials.view.composable.TestScreen
import com.example.programmingmaterials.viewmodel.MainActivityViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProgrammingMaterialsTheme {
                val navController = rememberNavController()
                val viewModel: MainActivityViewModel = hiltViewModel()
                Scaffold(
                    bottomBar = {
                        if (viewModel.state.value.showBottomBar) BottomMenu(
                            navController,
                            viewModel
                        )
                    },
                    modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars)
                ) {
                    it
                    NavHost(navController, startDestination = Routes.Home) {
                        composable<Routes.Home> {
                            HomeScreen(navController)
                            LaunchedEffect(Unit) {
                                viewModel.setBottomBarVisibility(true)
                            }
                        }
                        composable<Routes.UserProgress> {
                            UserProgressScreen(navController)
                            LaunchedEffect(Unit) {
                                viewModel.setBottomBarVisibility(false)
                            }
                        }
                        composable<Routes.UserProfile> {
                            UserProfileScreen(navController)
                            LaunchedEffect(Unit) {
                                viewModel.setBottomBarVisibility(true)
                            }
                        }
                        composable<Routes.UserFeedbacks> {
                            FeedbacksScreen(navController)
                            LaunchedEffect(Unit) {
                                viewModel.setBottomBarVisibility(false)
                            }
                        }
                        composable<Routes.Tests> {
                            TestScreen(navController)
                            LaunchedEffect(Unit) {
                                viewModel.setBottomBarVisibility(false)
                            }
                        }
                        composable(
                            route = Routes.MaterialDetails.route,
                            arguments = listOf(navArgument("materialId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val materialId = backStackEntry.arguments?.getInt("materialId") ?: 0
                            MaterialDetailsScreen(materialId = materialId,navController = navController)
                            LaunchedEffect(Unit) {
                                viewModel.setBottomBarVisibility(false)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomMenu(navController: NavController, viewModel: MainActivityViewModel) {
    NavigationBar(modifier = Modifier) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, null) },
            label = { Text("Главная") },
            selected = viewModel.state.value.enabledScreen == Routes.Home,
            onClick = {
                navController.navigate(Routes.Home)
                viewModel.navigateTo(Routes.Home)
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.AccountCircle, null) },
            label = { Text("Профиль") },
            selected = viewModel.state.value.enabledScreen == Routes.UserProfile,
            onClick = {
                navController.navigate(Routes.UserProfile)
                viewModel.navigateTo(Routes.UserProfile)
            }
        )
    }
}