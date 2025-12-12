// app/src/main/java/com/worldmates/messenger/ui/theme/DesignSystem.kt
package com.worldmates.messenger.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat

// Цветовая палитра мессенджера
object MessengerColors {
    // Основной бренд-цвет (современный индиго)
    val Primary = Color(0xFF6366F1)
    val PrimaryLight = Color(0xFFA5B4FC)
    val PrimaryDark = Color(0xFF4F46E5)
    
    // Вторичный цвет (розовый акцент)
    val Secondary = Color(0xFFEC4899)
    val SecondaryLight = Color(0xFFF9A8D4)
    val SecondaryDark = Color(0xFFDB2777)
    
    // Третичный цвет (фиолетовый для групп)
    val Tertiary = Color(0xFF8B5CF6)
    
    // Фоны
    val BackgroundLight = Color(0xFFF8FAFC)
    val SurfaceLight = Color(0xFFFFFFFF)
    val SurfaceVariantLight = Color(0xFFF1F5F9)
    
    // Текст светлой темы
    val OnPrimaryLight = Color(0xFFFFFFFF)
    val OnSurfaceLight = Color(0xFF1E293B)
    val OnSurfaceVariantLight = Color(0xFF64748B)
    val OnBackgroundLight = Color(0xFF0F172A)
    
    // Темная тема
    val BackgroundDark = Color(0xFF0F172A)
    val SurfaceDark = Color(0xFF1E293B)
    val SurfaceVariantDark = Color(0xFF334155)
    
    // Текст темной темы
    val OnPrimaryDark = Color(0xFF000000)
    val OnSurfaceDark = Color(0xFFE2E8F0)
    val OnSurfaceVariantDark = Color(0xFF94A3B8)
    val OnBackgroundDark = Color(0xFFF1F5F9)
    
    // Статусные цвета
    val Success = Color(0xFF10B981)
    val Warning = Color(0xFFF59E0B)
    val Error = Color(0xFFEF4444)
    val Info = Color(0xFF3B82F6)
    
    // Градиенты
    val GradientStart = Color(0xFF6366F1)
    val GradientMiddle = Color(0xFF8B5CF6)
    val GradientEnd = Color(0xFFEC4899)
    
    // Специфичные для мессенджера
    val MessageBubbleSent = Color(0xFF6366F1)
    val MessageBubbleReceived = Color(0xFFF1F5F9)
    val MessageBubbleGroup = Color(0xFFFEF3C7)
    
    // Статус онлайн
    val OnlineGreen = Color(0xFF10B981)
    val AwayYellow = Color(0xFFF59E0B)
    val BusyRed = Color(0xFFEF4444)
    val InvisibleGray = Color(0xFF94A3B8)
    
    // Input и поля
    val InputBackground = Color(0xFFF8FAFC)
    val InputBorder = Color(0xFFE2E8F0)
    
    // Навигация
    val NavigationBar = Color(0xFFFFFFFF)
    val NavigationBarDark = Color(0xFF1E293B)
    
    // Дополнительные
    val TypingIndicator = Color(0xFF6366F1)
    val UnreadBadge = Color(0xFFEF4444)
    val PinnedChat = Color(0xFFF59E0B)
    val VerifiedBadge = Color(0xFF3B82F6)
}

// Формы (Shapes)
object MessengerShapes {
    val Small = RoundedCornerShape(4.dp)
    val Medium = RoundedCornerShape(8.dp)
    val Large = RoundedCornerShape(16.dp)
    val ExtraLarge = RoundedCornerShape(24.dp)
    val Circular = RoundedCornerShape(50)
    
    // Специфичные формы
    val MessageBubbleSent = RoundedCornerShape(
        topStart = 16.dp,
        topEnd = 4.dp,
        bottomStart = 16.dp,
        bottomEnd = 16.dp
    )
    
    val MessageBubbleReceived = RoundedCornerShape(
        topStart = 4.dp,
        topEnd = 16.dp,
        bottomStart = 16.dp,
        bottomEnd = 16.dp
    )
    
    val Avatar = RoundedCornerShape(50)
    val Card = RoundedCornerShape(12.dp)
    val Button = RoundedCornerShape(10.dp)
}

// Типографика мессенджера
object MessengerTypography {
    // Заголовки
    val displayLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp
    )
    val displayMedium = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 45.sp,
        lineHeight = 52.sp
    )
    
    // Заголовки экранов
    val headlineLarge = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 32.sp,
        lineHeight = 40.sp
    )
    val headlineMedium = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        lineHeight = 36.sp
    )
    val headlineSmall = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 32.sp
    )
    
    // Заголовки разделов
    val titleLarge = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        lineHeight = 28.sp
    )
    val titleMedium = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    )
    val titleSmall = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    )
    
    // Основной текст
    val bodyLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    val bodyMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    )
    val bodySmall = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp
    )
    
    // Кнопки и метки
    val labelLarge = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    )
    val labelMedium = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    val labelSmall = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    
    // Специфичные для мессенджера
    val messageText = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.2.sp
    )
    
    val messageTime = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp,
        lineHeight = 13.sp,
        letterSpacing = 0.3.sp
    )
    
    val chatName = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 20.sp
    )
    
    val chatPreview = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.2.sp
    )
}

@Composable
fun MessengerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> darkColorScheme(
            primary = MessengerColors.Primary,
            secondary = MessengerColors.Secondary,
            tertiary = MessengerColors.Tertiary,
            background = MessengerColors.BackgroundDark,
            surface = MessengerColors.SurfaceDark,
            surfaceVariant = MessengerColors.SurfaceVariantDark,
            onPrimary = MessengerColors.OnPrimaryDark,
            onSecondary = Color.Black,
            onTertiary = Color.Black,
            onBackground = MessengerColors.OnBackgroundDark,
            onSurface = MessengerColors.OnSurfaceDark,
            onSurfaceVariant = MessengerColors.OnSurfaceVariantDark,
            outline = Color(0xFF475569),
            outlineVariant = Color(0xFF334155),
            scrim = Color(0x99000000),
            inverseSurface = Color(0xFFF1F5F9),
            inverseOnSurface = Color(0xFF0F172A),
            inversePrimary = MessengerColors.PrimaryLight,
            primaryContainer = MessengerColors.PrimaryDark,
            secondaryContainer = MessengerColors.SecondaryDark,
            tertiaryContainer = MessengerColors.Tertiary,
            onPrimaryContainer = Color.White,
            onSecondaryContainer = Color.White,
            onTertiaryContainer = Color.White,
            error = MessengerColors.Error,
            errorContainer = Color(0xFFFECACA),
            onError = Color.White,
            onErrorContainer = Color(0xFF7F1D1D)
        )
        else -> lightColorScheme(
            primary = MessengerColors.Primary,
            secondary = MessengerColors.Secondary,
            tertiary = MessengerColors.Tertiary,
            background = MessengerColors.BackgroundLight,
            surface = MessengerColors.SurfaceLight,
            surfaceVariant = MessengerColors.SurfaceVariantLight,
            onPrimary = MessengerColors.OnPrimaryLight,
            onSecondary = Color.White,
            onTertiary = Color.White,
            onBackground = MessengerColors.OnBackgroundLight,
            onSurface = MessengerColors.OnSurfaceLight,
            onSurfaceVariant = MessengerColors.OnSurfaceVariantLight,
            outline = Color(0xFFE2E8F0),
            outlineVariant = Color(0xFFF1F5F9),
            scrim = Color(0x99000000),
            inverseSurface = Color(0xFF1E293B),
            inverseOnSurface = Color(0xFFF1F5F9),
            inversePrimary = MessengerColors.PrimaryLight,
            primaryContainer = MessengerColors.PrimaryLight,
            secondaryContainer = MessengerColors.SecondaryLight,
            tertiaryContainer = MessengerColors.Tertiary.copy(alpha = 0.2f),
            onPrimaryContainer = MessengerColors.PrimaryDark,
            onSecondaryContainer = MessengerColors.SecondaryDark,
            onTertiaryContainer = MessengerColors.Tertiary,
            error = MessengerColors.Error,
            errorContainer = Color(0xFFFEE2E2),
            onError = Color.White,
            onErrorContainer = Color(0xFF7F1D1D)
        )
    }
    
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            window.navigationBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = !darkTheme
        }
    }
    
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(
            displayLarge = MessengerTypography.displayLarge,
            displayMedium = MessengerTypography.displayMedium,
            headlineLarge = MessengerTypography.headlineLarge,
            headlineMedium = MessengerTypography.headlineMedium,
            headlineSmall = MessengerTypography.headlineSmall,
            titleLarge = MessengerTypography.titleLarge,
            titleMedium = MessengerTypography.titleMedium,
            titleSmall = MessengerTypography.titleSmall,
            bodyLarge = MessengerTypography.bodyLarge,
            bodyMedium = MessengerTypography.bodyMedium,
            bodySmall = MessengerTypography.bodySmall,
            labelLarge = MessengerTypography.labelLarge,
            labelMedium = MessengerTypography.labelMedium,
            labelSmall = MessengerTypography.labelSmall
        ),
        shapes = Shapes(
            extraSmall = MessengerShapes.Small,
            small = MessengerShapes.Medium,
            medium = MessengerShapes.Large,
            large = MessengerShapes.ExtraLarge,
            extraLarge = MessengerShapes.Circular
        ),
        content = content
    )
}