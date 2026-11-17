package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = MintPrimary,
    onPrimary = ObsidianInk,
    primaryContainer = MintMuted,
    onPrimaryContainer = PureWhite,
    secondary = PurpleAccent,
    onSecondary = ObsidianInk,
    secondaryContainer = GraphiteSlate,
    onSecondaryContainer = PureWhite,
    tertiary = GoldAccent,
    onTertiary = ObsidianInk,
    background = BgDark,
    onBackground = TextPrimary,
    surface = SurfaceDark,
    onSurface = TextPrimary,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = TextSecondary,
    outline = BorderDark,
    error = CoralRed,
    onError = PureWhite
)

@Composable
fun MathCraftTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = AppTypography,
        content = content
    )
}
