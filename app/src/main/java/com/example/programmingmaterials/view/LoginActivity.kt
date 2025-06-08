package com.example.programmingmaterials.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.programmingmaterials.view.composable.LoginScreen
import com.example.programmingmaterials.ui.theme.ProgrammingMaterialsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProgrammingMaterialsTheme {
                LoginScreen()
            }
        }
    }

}
