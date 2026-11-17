package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Bolt
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BgDark
import com.example.ui.theme.CardDark
import com.example.ui.theme.ChampagneTaupe
import com.example.ui.theme.CoralRed
import com.example.ui.theme.DeepMocha
import com.example.ui.theme.ErrorActionTone
import com.example.ui.theme.ErrorBg
import com.example.ui.theme.GlassBase
import com.example.ui.theme.GlassRimBottom
import com.example.ui.theme.GlassRimTop
import com.example.ui.theme.GlassSurfaceBottom
import com.example.ui.theme.GlassSurfaceTop
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GraphiteSlate
import com.example.ui.theme.MintPrimary
import com.example.ui.theme.ObsidianInk
import com.example.ui.theme.PureWhite
import com.example.ui.theme.SuccessBg
import com.example.ui.theme.SuccessSage
import com.example.ui.theme.SurfaceDark
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.WarmAlabaster
import com.example.ui.theme.WarmTaupe
import com.example.util.PersianUtils

/**
 * iOS-inspired dark frosted glass surface with subtle vertical sheen and specular rim highlight.
 */
@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(20.dp),
    accentTint: Color? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    val fillBrush = if (accentTint != null) {
        Brush.verticalGradient(
            listOf(
                accentTint.copy(alpha = 0.28f),
                GraphiteSlate.copy(alpha = 0.24f)
            )
        )
    } else {
        Brush.verticalGradient(
            listOf(
                GlassSurfaceTop,
                GlassSurfaceBottom
            )
        )
    }

    val borderBrush = Brush.verticalGradient(
        listOf(
            accentTint?.copy(alpha = 0.62f) ?: GlassRimTop,
            GlassRimBottom
        )
    )

    Box(
        modifier = modifier
            .clip(shape)
            .background(GlassBase)
            .background(fillBrush)
            .border(width = 1.dp, brush = borderBrush, shape = shape)
    ) {
        Column(content = content)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MathCraftTopBar(
    title: String,
    stars: Int,
    xp: Int,
    onBackClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(ObsidianInk.copy(alpha = 0.88f))
    ) {
        TopAppBar(
            modifier = Modifier.testTag("mathcraft_top_bar"),
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent,
                titleContentColor = PureWhite,
                navigationIconContentColor = PureWhite
            ),
            title = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = PureWhite
                )
            },
            navigationIcon = {
                if (onBackClick != null) {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.testTag("top_bar_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "بازگشت"
                        )
                    }
                }
            },
            actions = {
                // Warm Taupe Smoked Glass XP Pill
                TopBarGlassPill(
                    icon = Icons.Rounded.Bolt,
                    iconTint = PureWhite,
                    pillTint = WarmTaupe,
                    iconContentDescription = "امتیاز",
                    text = "${PersianUtils.toPersian(xp)} XP",
                    textColor = PureWhite,
                    modifier = Modifier.padding(end = 6.dp)
                )

                // Brushed Graphite Glass Stars Pill
                TopBarGlassPill(
                    icon = Icons.Rounded.Star,
                    iconTint = GoldAccent,
                    pillTint = GraphiteSlate,
                    iconContentDescription = "ستاره‌ها",
                    text = PersianUtils.toPersian(stars),
                    textColor = WarmAlabaster,
                    modifier = Modifier.padding(end = 12.dp)
                )
            }
        )
        HorizontalDivider(thickness = 0.75.dp, color = GraphiteSlate)
    }
}

@Composable
private fun TopBarGlassPill(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: Color,
    pillTint: Color,
    iconContentDescription: String,
    text: String,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    val pillShape = RoundedCornerShape(50)
    Box(
        modifier = modifier
            .clip(pillShape)
            .background(
                Brush.verticalGradient(
                    listOf(
                        pillTint.copy(alpha = 0.42f),
                        ObsidianInk.copy(alpha = 0.72f)
                    )
                )
            )
            .border(
                width = 1.dp,
                brush = Brush.verticalGradient(
                    listOf(
                        PureWhite.copy(alpha = 0.28f),
                        GraphiteSlate
                    )
                ),
                shape = pillShape
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 9.dp, vertical = 5.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .background(iconTint.copy(alpha = 0.18f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = iconContentDescription,
                    tint = iconTint,
                    modifier = Modifier.size(13.dp)
                )
            }
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = textColor
            )
        }
    }
}

@Composable
fun MathBead(
    number: Int,
    isSelected: Boolean = false,
    color: Color = MintPrimary,
    size: Dp = 48.dp,
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val bgModifier = if (onClick != null) {
        modifier
            .size(size)
            .clip(CircleShape)
            .clickable(onClick = onClick)
    } else {
        modifier
            .size(size)
            .clip(CircleShape)
    }

    Box(
        modifier = bgModifier
            .background(
                Brush.radialGradient(
                    colors = if (isSelected) {
                        listOf(color.copy(alpha = 0.92f), color.copy(alpha = 0.65f))
                    } else {
                        listOf(PureWhite.copy(alpha = 0.10f), CardDark)
                    }
                )
            )
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) GoldAccent else color.copy(alpha = 0.45f),
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = PersianUtils.toPersian(number),
            color = if (isSelected) BgDark else TextPrimary,
            fontWeight = FontWeight.Bold,
            fontSize = if (size > 44.dp) 18.sp else 14.sp
        )
    }
}

@Composable
fun FormulaBox(
    formula: String,
    modifier: Modifier = Modifier,
    label: String? = null
) {
    GlassCard(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        accentTint = MintPrimary
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (label != null) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelMedium,
                    color = TextSecondary,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                Text(
                    text = formula,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MintPrimary,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun FeedbackCard(
    isSuccess: Boolean,
    message: String,
    explanation: String,
    onContinue: () -> Unit,
    buttonText: String = "ادامه",
    visible: Boolean = true,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        val statusColor = if (isSuccess) SuccessSage else CoralRed
        Surface(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(16.dp),
            color = if (isSuccess) SuccessBg else ErrorBg,
            border = BorderStroke(1.2.dp, statusColor.copy(alpha = 0.75f))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (isSuccess) Icons.Rounded.CheckCircle else Icons.Rounded.Close,
                        contentDescription = null,
                        tint = statusColor,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = message,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = statusColor
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = explanation,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextPrimary,
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(14.dp))

                Button(
                    onClick = onContinue,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("feedback_continue_btn"),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSuccess) DeepMocha else ErrorActionTone
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = buttonText,
                        color = PureWhite,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun LessonSectionTitle(
    icon: String,
    title: String,
    subtitle: String? = null,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth().padding(vertical = 6.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = icon,
                fontSize = 22.sp
            )
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
        }
        if (subtitle != null) {
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = TextMuted,
                modifier = Modifier.padding(start = 32.dp, top = 2.dp)
            )
        }
    }
}
