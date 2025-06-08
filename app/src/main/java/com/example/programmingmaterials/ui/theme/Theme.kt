package com.example.programmingmaterials.ui.theme
// Theme.kt
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.programmingmaterials.R

// Цветовая палитра
val PrimaryBlue = Color(0xFF4361EE)
val SecondaryPurple = Color(0xFF7209B7)
val AccentPink = Color(0xFFF72585)
val DarkBackground = Color(0xFF1A1A2E)
val LightBackground = Color(0xFFF8F9FA)
val CardGradientStart = Color(0xFF3A0CA3)
val CardGradientEnd = Color(0xFF4CC9F0)

// Шрифты
val AppFontFamily = FontFamily(
    Font(R.font.roboto_regular, FontWeight.Normal),
    Font(R.font.roboto_medium, FontWeight.Medium),
    Font(R.font.roboto_bold, FontWeight.Bold)
)

// Текстовая тема
val AppTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp
    ),
    titleLarge = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        color = PrimaryBlue
    ),
    bodyLarge = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    labelSmall = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        color = Color.Gray
    )
)

@Composable
fun ProgrammingMaterialsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        darkColorScheme(
            primary = PrimaryBlue,
            secondary = SecondaryPurple,
            background = DarkBackground,
            surface = Color(0xFF16213E),
            onPrimary = Color.White,
            onBackground = Color.White
        )
    } else {
        lightColorScheme(
            primary = PrimaryBlue,
            secondary = SecondaryPurple,
            background = LightBackground,
            surface = Color.White,
            onPrimary = Color.White,
            onBackground = Color.Black
        )
    }

    MaterialTheme(
        colorScheme = colors,
        typography = AppTypography,
        shapes = Shapes(
            small = RoundedCornerShape(8.dp),
            medium = RoundedCornerShape(16.dp),
            large = RoundedCornerShape(24.dp)
        ),
        content = content
    )
}