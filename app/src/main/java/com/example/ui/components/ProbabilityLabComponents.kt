package com.example.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BgDark
import com.example.ui.theme.BorderDark
import com.example.ui.theme.CardDark
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.MintPrimary
import com.example.ui.theme.PurpleAccent
import com.example.ui.theme.SurfaceDark
import com.example.ui.theme.SurfaceVariantDark
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.util.PersianUtils

object ProbabilityMath {
    fun gcd(a: Int, b: Int): Int {
        var x = kotlin.math.abs(a)
        var y = kotlin.math.abs(b)
        while (y != 0) {
            val t = y
            y = x % y
            x = t
        }
        return if (x == 0) 1 else x
    }

    fun areFractionsEqual(num1: Int, den1: Int, num2: Int, den2: Int): Boolean {
        if (den1 <= 0 || den2 <= 0) return false
        return num1 * den2 == num2 * den1
    }

    fun isSimplified(num: Int, den: Int): Boolean {
        if (den <= 0) return false
        if (num == 0) return den == 1
        return gcd(num, den) == 1
    }
}

@Composable
fun LtrMathText(
    text: String,
    color: Color = MintPrimary,
    fontSize: Int = 15,
    fontWeight: FontWeight = FontWeight.Bold,
    modifier: Modifier = Modifier
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        Text(
            text = text,
            color = color,
            fontSize = fontSize.sp,
            fontWeight = fontWeight,
            textAlign = TextAlign.Center,
            modifier = modifier
        )
    }
}

/**
 * Interactive Fraction Builder: lets the student specify n(E) (numerator) and n(S) (denominator)
 * to construct P(E) = n(E) / n(S).
 */
@Composable
fun FractionBuilder(
    eventSymbol: String,
    numerator: Int,
    denominator: Int,
    maxDenominator: Int = 12,
    enabled: Boolean = true,
    accentColor: Color = CyanAccent,
    onNumeratorChange: (Int) -> Unit,
    onDenominatorChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    GlassCard(
        shape = RoundedCornerShape(16.dp),
        accentTint = accentColor,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "ساختار کسر احتمال را تنظیم کنید:",
                style = MaterialTheme.typography.labelMedium,
                color = TextSecondary
            )
            Spacer(modifier = Modifier.height(10.dp))

            // Keep formula structure strictly LTR: P(E) = [ num / den ]
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "P($eventSymbol) =",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = accentColor
                    )

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Numerator stepper
                        StepperRow(
                            value = numerator,
                            min = 0,
                            max = maxDenominator,
                            enabled = enabled,
                            accentColor = accentColor,
                            label = "صورت n($eventSymbol)",
                            tagPrefix = "num",
                            onValueChange = onNumeratorChange
                        )

                        Spacer(modifier = Modifier.height(6.dp))
                        HorizontalDivider(
                            modifier = Modifier.width(140.dp),
                            thickness = 2.dp,
                            color = TextPrimary
                        )
                        Spacer(modifier = Modifier.height(6.dp))

                        // Denominator stepper
                        StepperRow(
                            value = denominator,
                            min = 1,
                            max = maxDenominator,
                            enabled = enabled,
                            accentColor = PurpleAccent,
                            label = "مخرج n(S)",
                            tagPrefix = "den",
                            onValueChange = onDenominatorChange
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            val pct = if (denominator > 0) ((numerator.toFloat() / denominator) * 100).toInt() else 0
            Text(
                text = "مقدار کسر فعلی: ${PersianUtils.toPersian(numerator)} از ${PersianUtils.toPersian(denominator)} (معادل ${PersianUtils.toPersian(pct)}٪)",
                style = MaterialTheme.typography.labelSmall,
                color = TextMuted
            )
        }
    }
}

@Composable
private fun StepperRow(
    value: Int,
    min: Int,
    max: Int,
    enabled: Boolean,
    accentColor: Color,
    label: String,
    tagPrefix: String,
    onValueChange: (Int) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        IconButton(
            onClick = { if (value > min) onValueChange(value - 1) },
            enabled = enabled && value > min,
            modifier = Modifier
                .minimumInteractiveComponentSize()
                .size(44.dp)
                .clip(CircleShape)
                .background(SurfaceDark)
                .border(1.dp, BorderDark, CircleShape)
                .testTag("${tagPrefix}_minus")
        ) {
            Icon(
                imageVector = Icons.Rounded.Remove,
                contentDescription = "کاهش $label",
                tint = if (enabled && value > min) TextPrimary else TextMuted,
                modifier = Modifier.size(18.dp)
            )
        }

        Box(
            modifier = Modifier
                .width(46.dp)
                .height(40.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(CardDark)
                .border(1.dp, accentColor.copy(alpha = 0.7f), RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = PersianUtils.toPersian(value),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = accentColor
            )
        }

        IconButton(
            onClick = { if (value < max) onValueChange(value + 1) },
            enabled = enabled && value < max,
            modifier = Modifier
                .minimumInteractiveComponentSize()
                .size(44.dp)
                .clip(CircleShape)
                .background(SurfaceDark)
                .border(1.dp, BorderDark, CircleShape)
                .testTag("${tagPrefix}_plus")
        ) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = "افزایش $label",
                tint = if (enabled && value < max) TextPrimary else TextMuted,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

/**
 * Live probability ratio bar chart on the [0 .. 1] axis.
 * Updates smoothly as the student adds/removes elements from the event set.
 */
@Composable
fun ProbabilityScaleChart(
    eventCount: Int,
    totalCount: Int,
    eventLabel: String = "E",
    accentColor: Color = CyanAccent,
    modifier: Modifier = Modifier
) {
    val ratio = if (totalCount > 0) (eventCount.toFloat() / totalCount).coerceIn(0f, 1f) else 0f
    val animatedRatio by animateFloatAsState(
        targetValue = ratio,
        animationSpec = tween(durationMillis = 300),
        label = "prob_bar"
    )

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = SurfaceDark,
        border = androidx.compose.foundation.BorderStroke(1.dp, BorderDark),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "نمودار نسبت احتمال روی محور [۰ تا ۱]",
                    style = MaterialTheme.typography.labelMedium,
                    color = TextSecondary
                )
                LtrMathText(
                    text = "n($eventLabel) / n(S) = ${PersianUtils.toPersian(eventCount)} / ${PersianUtils.toPersian(totalCount)}",
                    color = accentColor,
                    fontSize = 13
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(22.dp)
            ) {
                // Background track
                drawRoundRect(
                    color = SurfaceVariantDark,
                    topLeft = Offset.Zero,
                    size = size,
                    cornerRadius = CornerRadius(11.dp.toPx(), 11.dp.toPx())
                )
                // Active probability bar
                if (animatedRatio > 0f) {
                    drawRoundRect(
                        color = accentColor,
                        topLeft = Offset.Zero,
                        size = Size(size.width * animatedRatio, size.height),
                        cornerRadius = CornerRadius(11.dp.toPx(), 11.dp.toPx())
                    )
                }
                // Midpoint 0.5 tick mark
                val midX = size.width / 2f
                drawLine(
                    color = TextMuted.copy(alpha = 0.6f),
                    start = Offset(midX, 0f),
                    end = Offset(midX, size.height),
                    strokeWidth = 2.dp.toPx()
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("۰ (غیرممکن)", style = MaterialTheme.typography.labelSmall, color = TextMuted)
                    Text("۱/۲ (پنجاه‌‌پنجاه)", style = MaterialTheme.typography.labelSmall, color = TextMuted)
                    Text("۱ (حتمی)", style = MaterialTheme.typography.labelSmall, color = TextMuted)
                }
            }
        }
    }
}

/**
 * Interactive Venn Diagram for Sample Space S = {1..9} with Set A (Multiples of 3) and Set B (Even numbers).
 * Highlights the elements currently selected by the student.
 */
@Composable
fun SampleSpaceVennDiagram(
    selectedElements: Set<Int>,
    modifier: Modifier = Modifier
) {
    // S = {1..9}
    // A (Multiples of 3) = {3, 6, 9} -> only A: {3, 9}
    // B (Even) = {2, 4, 6, 8} -> only B: {2, 4, 8}
    // A ∩ B = {6}
    // Outside = {1, 5, 7}
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = SurfaceDark,
        border = androidx.compose.foundation.BorderStroke(1.dp, BorderDark),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "نمودار ون فضای نمونه S",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                LtrMathText(
                    text = "A={۳،۶،۹}  |  B={۲،۴،۶،۸}",
                    color = TextSecondary,
                    fontSize = 12
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(175.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(175.dp)
                ) {
                    val w = size.width
                    val h = size.height

                    // Sample Space S outer rectangle
                    drawRoundRect(
                        color = SurfaceVariantDark.copy(alpha = 0.5f),
                        topLeft = Offset(4.dp.toPx(), 4.dp.toPx()),
                        size = Size(w - 8.dp.toPx(), h - 8.dp.toPx()),
                        cornerRadius = CornerRadius(12.dp.toPx(), 12.dp.toPx())
                    )
                    drawRoundRect(
                        color = BorderDark,
                        topLeft = Offset(4.dp.toPx(), 4.dp.toPx()),
                        size = Size(w - 8.dp.toPx(), h - 8.dp.toPx()),
                        cornerRadius = CornerRadius(12.dp.toPx(), 12.dp.toPx()),
                        style = Stroke(width = 1.5.dp.toPx())
                    )

                    val radius = minOf(w * 0.25f, h * 0.37f)
                    val centerY = h * 0.52f
                    val centerA = Offset(w * 0.36f, centerY)
                    val centerB = Offset(w * 0.64f, centerY)

                    // Circle A
                    drawCircle(
                        color = MintPrimary.copy(alpha = 0.14f),
                        radius = radius,
                        center = centerA
                    )
                    drawCircle(
                        color = MintPrimary.copy(alpha = 0.8f),
                        radius = radius,
                        center = centerA,
                        style = Stroke(width = 2.dp.toPx())
                    )

                    // Circle B
                    drawCircle(
                        color = PurpleAccent.copy(alpha = 0.14f),
                        radius = radius,
                        center = centerB
                    )
                    drawCircle(
                        color = PurpleAccent.copy(alpha = 0.8f),
                        radius = radius,
                        center = centerB,
                        style = Stroke(width = 2.dp.toPx())
                    )
                }

                // Region Labels and Element Chips (LTR layout to match circle positions)
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(175.dp)
                            .padding(horizontal = 14.dp, vertical = 10.dp)
                    ) {
                        // S label top-left
                        Text(
                            text = "S",
                            color = TextMuted,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            modifier = Modifier.align(Alignment.TopStart)
                        )

                        // A & B titles top
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.TopCenter),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Text("A (مضرب ۳)", color = MintPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            Text("B (زوج)", color = PurpleAccent, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }

                        // Numbers positioned inside Venn regions
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.Center)
                                .padding(top = 6.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Only A: {3, 9}
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                VennNumberTag(3, selectedElements.contains(3), MintPrimary)
                                VennNumberTag(9, selectedElements.contains(9), MintPrimary)
                            }

                            // Intersection A ∩ B: {6}
                            VennNumberTag(6, selectedElements.contains(6), GoldAccent)

                            // Only B: {2, 4, 8}
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(3.dp)
                            ) {
                                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                    VennNumberTag(2, selectedElements.contains(2), PurpleAccent)
                                    VennNumberTag(4, selectedElements.contains(4), PurpleAccent)
                                }
                                VennNumberTag(8, selectedElements.contains(8), PurpleAccent)
                            }
                        }

                        // Outside A ∪ B: {1, 5, 7}
                        Row(
                            modifier = Modifier.align(Alignment.BottomStart),
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("خارج:", color = TextMuted, fontSize = 10.sp)
                            VennNumberTag(1, selectedElements.contains(1), TextMuted)
                            VennNumberTag(5, selectedElements.contains(5), TextMuted)
                            VennNumberTag(7, selectedElements.contains(7), TextMuted)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun VennNumberTag(
    num: Int,
    isHighlighted: Boolean,
    accentColor: Color
) {
    Box(
        modifier = Modifier
            .size(26.dp)
            .clip(CircleShape)
            .background(if (isHighlighted) accentColor else SurfaceVariantDark)
            .border(1.dp, if (isHighlighted) Color.White else accentColor.copy(alpha = 0.5f), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = PersianUtils.toPersian(num),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = if (isHighlighted) BgDark else TextPrimary
        )
    }
}
