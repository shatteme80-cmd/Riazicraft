package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.MenuBook
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.DataObject
import androidx.compose.material.icons.rounded.EmojiEvents
import androidx.compose.material.icons.rounded.Functions
import androidx.compose.material.icons.rounded.Layers
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.Science
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppScreen
import com.example.data.UserProgress
import com.example.ui.components.GlassCard
import com.example.ui.components.MathCraftTopBar
import com.example.ui.theme.BorderDark
import com.example.ui.theme.ChampagneTaupe
import com.example.ui.theme.CoralRed
import com.example.ui.theme.GlassRimBottom
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GraphiteSlate
import com.example.ui.theme.MintPrimary
import com.example.ui.theme.ObsidianInk
import com.example.ui.theme.PureWhite
import com.example.ui.theme.PurpleAccent
import com.example.ui.theme.SurfaceDark
import com.example.ui.theme.SurfaceVariantDark
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.WarmAlabaster
import com.example.ui.theme.WarmTaupe
import com.example.util.PersianUtils

@Composable
fun HomeScreen(
    progress: UserProgress,
    onNavigate: (AppScreen) -> Unit,
    onResetProgress: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showResetDialog by remember { mutableStateOf(false) }

    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            title = {
                Text(
                    text = "شروع مجدد فصل؟",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            },
            text = {
                Text(
                    text = "آیا مطمئن هستید که می‌خواهید تمام پیشرفت و ستاره‌های این فصل بازنشانی شوند؟",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        onResetProgress()
                        showResetDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = CoralRed)
                ) {
                    Text("بله، بازنشانی شود", color = PureWhite)
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetDialog = false }) {
                    Text("انصراف", color = TextSecondary)
                }
            },
            containerColor = SurfaceDark
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Transparent)
    ) {
        MathCraftTopBar(
            title = "ریاضیکرفت",
            stars = progress.totalStars,
            xp = progress.xp
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 680.dp),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            // Hero Glass Card (Obsidian Ink + Graphite Slate + Warm Taupe + Pure White CTA)
            item {
                GlassCard(
                    shape = RoundedCornerShape(22.dp),
                    accentTint = WarmTaupe,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(GraphiteSlate.copy(alpha = 0.72f))
                                    .border(1.dp, WarmTaupe.copy(alpha = 0.75f), RoundedCornerShape(14.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Functions,
                                    contentDescription = null,
                                    tint = PureWhite,
                                    modifier = Modifier.size(26.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "سلام دانشمند نهم!",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = PureWhite
                                )
                                Text(
                                    text = "یادگیری تعاملی ریاضی نهم — فصل مجموعه‌ها",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = WarmAlabaster
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(18.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "فصل اول: مجموعه‌ها و احتمال",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = PureWhite
                            )
                            Text(
                                text = "${PersianUtils.toPersian((progress.progressPercent * 100).toInt())}٪",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold,
                                color = ChampagneTaupe
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        LinearProgressIndicator(
                            progress = { progress.progressPercent },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp)),
                            color = WarmTaupe,
                            trackColor = GraphiteSlate.copy(alpha = 0.65f)
                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        val nextScreen = when {
                            !progress.isLesson1Completed -> AppScreen.Lesson1
                            !progress.isLesson2Completed -> AppScreen.Lesson2()
                            !progress.isLesson3Completed -> AppScreen.Lesson3
                            else -> AppScreen.Quiz
                        }

                        Button(
                            onClick = { onNavigate(nextScreen) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("continue_learning_button"),
                            colors = ButtonDefaults.buttonColors(containerColor = PureWhite),
                            border = BorderStroke(1.dp, WarmTaupe.copy(alpha = 0.45f)),
                            shape = RoundedCornerShape(14.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.padding(vertical = 2.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.PlayArrow,
                                    contentDescription = null,
                                    tint = ObsidianInk
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (progress.progressPercent >= 1f) "مرور درس‌ها یا آزمون پایانی" else "ادامه مسیر یادگیری",
                                    color = ObsidianInk,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                            }
                        }
                    }
                }
            }

            // Learning Path Section Title
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "نقشه مراحل فصل",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = "۴ مرحله بازی و یادگیری",
                        style = MaterialTheme.typography.labelMedium,
                        color = TextMuted
                    )
                }
            }

            // Node 1: Lesson 1
            item {
                LearningPathNode(
                    stepNumber = 1,
                    title = "درس اول: آشنایی با مجموعه‌ها و نمادها",
                    subtitle = "مفهوم مجموعه، آکولاد، عضویت (∈، ∉)، تهی (∅) و برابری",
                    icon = Icons.Rounded.DataObject,
                    isCompleted = progress.isLesson1Completed,
                    isUnlocked = true,
                    starsEarned = progress.starsLesson1,
                    maxStars = 3,
                    accentColor = MintPrimary,
                    onClick = { onNavigate(AppScreen.Lesson1) }
                )
            }

            // Node 2: Lesson 2
            item {
                LearningPathNode(
                    stepNumber = 2,
                    title = "درس دوم: اجتماع، اشتراک و تفاضل",
                    subtitle = "بازی کاسه U شکل اجتماع، دستگاه تفکیک اشتراک و تفاضل",
                    icon = Icons.Rounded.Layers,
                    isCompleted = progress.isLesson2Completed,
                    isUnlocked = progress.isLesson1Completed,
                    starsEarned = progress.starsLesson2,
                    maxStars = 3,
                    accentColor = PurpleAccent,
                    onClick = { onNavigate(AppScreen.Lesson2()) }
                )
            }

            // Node 3: Lesson 3
            item {
                LearningPathNode(
                    stepNumber = 3,
                    title = "درس سوم: مجموعه‌ها و احتمال",
                    subtitle = "درسنامه احتمال مطابق جزوه، دو تاس، مهره‌ها و ۸ سؤال ۴ گزینه‌ای",
                    icon = Icons.Rounded.Science,
                    isCompleted = progress.isLesson3Completed,
                    isUnlocked = progress.isLesson2Completed,
                    starsEarned = progress.starsLesson3,
                    maxStars = 3,
                    accentColor = WarmTaupe,
                    onClick = { onNavigate(AppScreen.Lesson3) }
                )
            }

            // Node 4: Final Exam
            item {
                LearningPathNode(
                    stepNumber = 4,
                    title = "آزمون پایانی جامع فصل اول",
                    subtitle = if (progress.isQuizUnlocked) {
                        if (progress.isQuizCompleted) "نمره شما: ${PersianUtils.toPersian(progress.quizScore)} از ۱۰۰"
                        else "۵ سؤال محاسباتی و مفهومی از کل فصل"
                    } else "برای باز شدن، ۳ درس قبل را کامل کنید",
                    icon = Icons.Rounded.EmojiEvents,
                    isCompleted = progress.isQuizCompleted,
                    isUnlocked = progress.isQuizUnlocked,
                    starsEarned = progress.starsQuiz,
                    maxStars = 3,
                    accentColor = GoldAccent,
                    onClick = {
                        if (progress.isQuizUnlocked) {
                            onNavigate(AppScreen.Quiz)
                        }
                    }
                )
            }

            // Champion Master Badge (when quiz completed)
            if (progress.isQuizCompleted) {
                item {
                    GlassCard(
                        shape = RoundedCornerShape(18.dp),
                        accentTint = GoldAccent,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(46.dp)
                                    .clip(CircleShape)
                                    .background(GoldAccent.copy(alpha = 0.18f))
                                    .border(1.dp, GoldAccent.copy(alpha = 0.45f), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.EmojiEvents,
                                    contentDescription = null,
                                    tint = GoldAccent,
                                    modifier = Modifier.size(26.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(14.dp))
                            Column {
                                Text(
                                    text = "مدال استادی فصل مجموعه‌ها",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = GoldAccent
                                )
                                Text(
                                    text = "شما با موفقیت آزمون پایانی را پشت سر گذاشتید! نمره: ${PersianUtils.toPersian(progress.quizScore)} از ۱۰۰",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextPrimary
                                )
                            }
                        }
                    }
                }
            }

            // Quick Math Symbols Guide
            item {
                QuickMathSymbolsGuide()
            }

            // Future Chapters (Upcoming)
            item {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "فصل‌های بعدی کتاب ریاضی نهم (به‌زودی)",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextMuted
                )
            }

            item {
                UpcomingChapterCard(
                    chapterTitle = "فصل دوم: عددهای حقیقی",
                    topics = "عددهای گویا و گنگ، قدر مطلق، بازه‌ها"
                )
            }

            item {
                UpcomingChapterCard(
                    chapterTitle = "فصل سوم: استدلال و اثبات در هندسه",
                    topics = "استدلال منطقی، هم‌نهشتی مثلث‌ها، تشابه"
                )
            }

            // Reset Option
            item {
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedButton(
                    onClick = { showResetDialog = true },
                    modifier = Modifier.fillMaxWidth(),
                    border = BorderStroke(1.dp, BorderDark),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = TextMuted),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Refresh,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "شروع مجدد و بازنشانی امتیازات",
                        style = MaterialTheme.typography.labelMedium
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun LearningPathNode(
    stepNumber: Int,
    title: String,
    subtitle: String,
    icon: ImageVector,
    isCompleted: Boolean,
    isUnlocked: Boolean,
    starsEarned: Int,
    maxStars: Int,
    accentColor: Color,
    onClick: () -> Unit
) {
    val cardModifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(18.dp))
        .clickable(enabled = isUnlocked, onClick = onClick)
        .testTag("path_node_$stepNumber")

    if (isUnlocked) {
        GlassCard(
            shape = RoundedCornerShape(18.dp),
            accentTint = if (isCompleted) accentColor else null,
            modifier = cardModifier
        ) {
            LearningPathNodeContent(
                stepNumber = stepNumber,
                title = title,
                subtitle = subtitle,
                icon = icon,
                isCompleted = isCompleted,
                isUnlocked = true,
                starsEarned = starsEarned,
                maxStars = maxStars,
                accentColor = accentColor
            )
        }
    } else {
        Surface(
            shape = RoundedCornerShape(18.dp),
            color = SurfaceDark.copy(alpha = 0.55f),
            border = BorderStroke(1.dp, GlassRimBottom),
            modifier = cardModifier
        ) {
            LearningPathNodeContent(
                stepNumber = stepNumber,
                title = title,
                subtitle = subtitle,
                icon = icon,
                isCompleted = false,
                isUnlocked = false,
                starsEarned = starsEarned,
                maxStars = maxStars,
                accentColor = accentColor
            )
        }
    }
}

@Composable
private fun LearningPathNodeContent(
    stepNumber: Int,
    title: String,
    subtitle: String,
    icon: ImageVector,
    isCompleted: Boolean,
    isUnlocked: Boolean,
    starsEarned: Int,
    maxStars: Int,
    accentColor: Color
) {
    Row(
        modifier = Modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(
                    if (isCompleted) accentColor.copy(alpha = 0.18f)
                    else if (isUnlocked) PureWhite.copy(alpha = 0.08f)
                    else SurfaceDark
                )
                .border(
                    1.dp,
                    if (isCompleted) accentColor.copy(alpha = 0.7f)
                    else if (isUnlocked) accentColor.copy(alpha = 0.35f)
                    else Color.Transparent,
                    RoundedCornerShape(14.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            if (isCompleted) {
                Icon(
                    imageVector = Icons.Rounded.Check,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(24.dp)
                )
            } else if (!isUnlocked) {
                Icon(
                    imageVector = Icons.Rounded.Lock,
                    contentDescription = "قفل",
                    tint = TextMuted,
                    modifier = Modifier.size(20.dp)
                )
            } else {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "مرحله ${PersianUtils.toPersian(stepNumber)}",
                    style = MaterialTheme.typography.labelSmall,
                    color = if (isUnlocked) accentColor else TextMuted,
                    fontWeight = FontWeight.Bold
                )

                if (isUnlocked) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        for (i in 1..maxStars) {
                            Icon(
                                imageVector = Icons.Rounded.Star,
                                contentDescription = null,
                                tint = if (i <= starsEarned) GoldAccent else TextMuted.copy(alpha = 0.35f),
                                modifier = Modifier.size(15.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = if (isUnlocked) TextPrimary else TextMuted
            )

            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = if (isUnlocked) TextSecondary else TextMuted,
                lineHeight = 18.sp
            )
        }
    }
}

@Composable
fun UpcomingChapterCard(
    chapterTitle: String,
    topics: String
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = SurfaceDark.copy(alpha = 0.45f),
        border = BorderStroke(1.dp, GlassRimBottom),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(SurfaceVariantDark),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.Lock,
                    contentDescription = null,
                    tint = TextMuted,
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = chapterTitle,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = TextSecondary
                )
                Text(
                    text = topics,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextMuted
                )
            }
        }
    }
}

@Composable
fun QuickMathSymbolsGuide() {
    var isExpanded by remember { mutableStateOf(false) }

    GlassCard(
        shape = RoundedCornerShape(18.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .clickable { isExpanded = !isExpanded }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(9.dp))
                            .background(MintPrimary.copy(alpha = 0.14f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.MenuBook,
                            contentDescription = null,
                            tint = MintPrimary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "راهنمای سریع نمادهای فصل اول",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                }

                Text(
                    text = if (isExpanded) "بستن ▲" else "مشاهده ▼",
                    style = MaterialTheme.typography.labelSmall,
                    color = MintPrimary,
                    fontWeight = FontWeight.Bold
                )
            }

            if (isExpanded) {
                Spacer(modifier = Modifier.height(14.dp))

                val symbols = listOf(
                    Triple("∈", "عضو است", "۲ ∈ {۱، ۲}"),
                    Triple("∉", "عضو نیست", "۳ ∉ {۱، ۲}"),
                    Triple("⊆", "زیرمجموعه است", "{۱} ⊆ {۱، ۲}"),
                    Triple("⊈", "زیرمجموعه نیست", "{۳} ⊈ {۱، ۲}"),
                    Triple("∅ یا { }", "مجموعه تهی", "فاقد عضو"),
                    Triple("=", "مجموعه‌های برابر", "{۱، ۲} = {۲، ۱}"),
                    Triple("∪", "اجتماع مجموعه‌ها", "تمام اعضا در A یا B"),
                    Triple("∩", "اشتراک مجموعه‌ها", "اعضای مشترک A و B"),
                    Triple("−", "تفاضل مجموعه‌ها", "اعضایی از A که در B نیستند"),
                    Triple("S", "فضای نمونه", "تمام حالت‌های ممکن"),
                    Triple("E", "پیشامد تصادفی", "زیرمجموعه‌ای از S"),
                    Triple("P(E)", "احتمال وقوع", "n(E) / n(S)")
                )

                symbols.chunked(2).forEach { rowPair ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        rowPair.forEach { (sym, title, eg) ->
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = GraphiteSlate.copy(alpha = 0.42f),
                                border = BorderStroke(0.75.dp, GlassRimBottom),
                                modifier = Modifier.weight(1f)
                            ) {
                                Column(modifier = Modifier.padding(8.dp)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                                            Text(
                                                text = sym,
                                                fontWeight = FontWeight.Bold,
                                                color = MintPrimary,
                                                fontSize = 16.sp
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = title,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = TextPrimary,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(2.dp))
                                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                                        Text(
                                            text = eg,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = TextSecondary
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
