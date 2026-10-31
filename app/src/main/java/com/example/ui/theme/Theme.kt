package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val HorologyDarkColorScheme = darkColorScheme(
    primary = ChronometerGold,
    onPrimary = Color(0xFF090D16),
    primaryContainer = ChronometerGoldSoft,
    onPrimaryContainer = ChronometerGoldLight,
    secondary = MeridianCyan,
    onSecondary = Color(0xFF061523),
    secondaryContainer = MeridianCyanSoft,
    onSecondaryContainer = MeridianCyan,
    tertiary = LiveEmerald,
    onTertiary = Color(0xFF042017),
    tertiaryContainer = LiveEmeraldSoft,
    onTertiaryContainer = LiveEmerald,
    background = MidnightNavy,
    onBackground = TextPrimaryIvory,
    surface = DeepObsidianBlue,
    onSurface = TextPrimaryIvory,
    surfaceVariant = SurfaceSlateBlue,
    onSurfaceVariant = TextSecondarySilver,
    surfaceContainerHigh = SurfaceElevatedBlue,
    outline = CardBorderSubtle,
    outlineVariant = TextMutedSlate
)

@Composable
fun WorldTimeLockTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = HorologyDarkColorScheme,
        typography = Typography,
        content = content
    )
}
