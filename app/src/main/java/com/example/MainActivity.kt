package com.example

import android.graphics.Color as AndroidColor
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import com.example.data.AppScreen
import com.example.ui.MainViewModel
import com.example.ui.screens.FinalQuizScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.Lesson1Screen
import com.example.ui.screens.Lesson2Screen
import com.example.ui.screens.Lesson3Screen
import com.example.ui.theme.AmbientGraphiteGlow
import com.example.ui.theme.AmbientTaupeGlow
import com.example.ui.theme.BgDark
import com.example.ui.theme.MathCraftTheme

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(AndroidColor.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.dark(AndroidColor.TRANSPARENT)
        )
        setContent {
            MathCraftTheme {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    MathCraftApp(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun MathCraftApp(viewModel: MainViewModel) {
    val currentScreen by viewModel.screen.collectAsState()
    val progress by viewModel.progress.collectAsState()

    Scaffold(
        containerColor = BgDark,
        modifier = Modifier
            .fillMaxSize()
            .background(BgDark)
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            BgDark,
                            AmbientTaupeGlow.copy(alpha = 0.24f),
                            AmbientGraphiteGlow.copy(alpha = 0.32f),
                            BgDark
                        )
                    )
                )
                .padding(innerPadding)
        ) {
            when (val screen = currentScreen) {
                is AppScreen.Home -> {
                    HomeScreen(
                        progress = progress,
                        onNavigate = { newScreen -> viewModel.navigateTo(newScreen) },
                        onResetProgress = { viewModel.resetProgress() }
                    )
                }

                is AppScreen.Lesson1 -> {
                    Lesson1Screen(
                        currentStars = progress.totalStars,
                        currentXp = progress.xp,
                        onBack = { viewModel.navigateBack() },
                        onCompleteLesson = { stars, xp ->
                            viewModel.completeLesson1(stars, xp)
                        },
                        onNextLesson = {
                            viewModel.navigateTo(AppScreen.Lesson2())
                        }
                    )
                }

                is AppScreen.Lesson2 -> {
                    Lesson2Screen(
                        currentStars = progress.totalStars,
                        currentXp = progress.xp,
                        initialTab = screen.initialTab,
                        onBack = { viewModel.navigateBack() },
                        onCompleteLesson = { stars, xp ->
                            viewModel.completeLesson2(stars, xp)
                        },
                        onNextLesson = {
                            viewModel.navigateTo(AppScreen.Lesson3)
                        }
                    )
                }

                is AppScreen.Lesson3 -> {
                    Lesson3Screen(
                        currentStars = progress.totalStars,
                        currentXp = progress.xp,
                        onBack = { viewModel.navigateBack() },
                        onCompleteLesson = { stars, xp ->
                            viewModel.completeLesson3(stars, xp)
                        },
                        onNextLesson = {
                            viewModel.navigateTo(AppScreen.Quiz)
                        }
                    )
                }

                is AppScreen.Quiz -> {
                    FinalQuizScreen(
                        currentStars = progress.totalStars,
                        currentXp = progress.xp,
                        onBack = { viewModel.navigateBack() },
                        onCompleteQuiz = { score, correctCount ->
                            viewModel.completeQuiz(score, correctCount)
                        }
                    )
                }
            }
        }
    }
}
