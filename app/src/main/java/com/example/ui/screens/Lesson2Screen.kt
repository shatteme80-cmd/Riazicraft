package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.FilterAlt
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.FeedbackCard
import com.example.ui.components.FormulaBox
import com.example.ui.components.MathBead
import com.example.ui.components.MathCraftTopBar
import com.example.ui.theme.BgDark
import com.example.ui.theme.BorderDark
import com.example.ui.theme.CardDark
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.ErrorBg
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.MintPrimary
import com.example.ui.theme.MintSecondary
import com.example.ui.theme.PurpleAccent
import com.example.ui.theme.SuccessBg
import com.example.ui.theme.SuccessSage
import com.example.ui.theme.SurfaceDark
import com.example.ui.theme.SurfaceVariantDark
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.util.PersianUtils

@Composable
fun Lesson2Screen(
    currentStars: Int,
    currentXp: Int,
    initialTab: Int = 0,
    onBack: () -> Unit,
    onCompleteLesson: (stars: Int, xpEarned: Int) -> Unit,
    onNextLesson: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }

    var selectedSection by remember { mutableIntStateOf(initialTab) } // 0: اجتماع, 1: اشتراک, 2: تفاضل
    var section1Done by remember { mutableStateOf(false) }
    var section2Done by remember { mutableStateOf(false) }
    var section3Done by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Transparent)
    ) {
        MathCraftTopBar(
            title = "درس دوم: عملگرهای مجموعه‌ها",
            stars = currentStars,
            xp = currentXp,
            onBackClick = onBack
        )

        ScrollableTabRow(
            selectedTabIndex = selectedSection,
            containerColor = SurfaceDark.copy(alpha = 0.78f),
            contentColor = TextPrimary,
            edgePadding = 16.dp,
            indicator = { tabPositions ->
                SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedSection]),
                    color = PurpleAccent
                )
            }
        ) {
            Tab(
                selected = selectedSection == 0,
                onClick = { selectedSection = 0 },
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "۱. اجتماع (∪)",
                            fontWeight = if (selectedSection == 0) FontWeight.Bold else FontWeight.Normal,
                            color = if (selectedSection == 0) MintPrimary else TextSecondary
                        )
                        if (section1Done) {
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(Icons.Rounded.Check, contentDescription = null, tint = MintPrimary, modifier = Modifier.size(16.dp))
                        }
                    }
                }
            )

            Tab(
                selected = selectedSection == 1,
                onClick = { selectedSection = 1 },
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "۲. اشتراک (∩)",
                            fontWeight = if (selectedSection == 1) FontWeight.Bold else FontWeight.Normal,
                            color = if (selectedSection == 1) PurpleAccent else TextSecondary
                        )
                        if (section2Done) {
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(Icons.Rounded.Check, contentDescription = null, tint = PurpleAccent, modifier = Modifier.size(16.dp))
                        }
                    }
                }
            )

            Tab(
                selected = selectedSection == 2,
                onClick = { selectedSection = 2 },
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "۳. تفاضل (−)",
                            fontWeight = if (selectedSection == 2) FontWeight.Bold else FontWeight.Normal,
                            color = if (selectedSection == 2) CyanAccent else TextSecondary
                        )
                        if (section3Done) {
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(Icons.Rounded.Check, contentDescription = null, tint = CyanAccent, modifier = Modifier.size(16.dp))
                        }
                    }
                }
            )
        }

        when (selectedSection) {
            0 -> UnionSectionView(
                onSectionComplete = {
                    section1Done = true
                    selectedSection = 1
                }
            )
            1 -> IntersectionSectionView(
                onSectionComplete = {
                    section2Done = true
                    selectedSection = 2
                }
            )
            2 -> DifferenceSectionView(
                onSectionComplete = {
                    section3Done = true
                    val stars = if (section1Done && section2Done) 3 else 2
                    onCompleteLesson(stars, 90)
                },
                onNextLesson = onNextLesson
            )
        }
    }
}

// -------------------------------------------------------------
// SECTION 1: UNION (اجتماع) WITH U-SHAPED BOWL GAME
// -------------------------------------------------------------
@Composable
fun UnionSectionView(
    onSectionComplete: () -> Unit
) {
    val setA = remember { listOf(1, 2, 3) }
    val setB = remember { listOf(3, 4, 5) }

    var bowlElements by remember { mutableStateOf(setOf<Int>()) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .widthIn(max = 680.dp),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = CardDark,
                border = androidx.compose.foundation.BorderStroke(1.dp, BorderDark),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "مفهوم اجتماع دو مجموعه (A ∪ B)",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MintPrimary
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "اجتماع دو مجموعه A و B شامل تمام اعضایی است که در A یا در B یا در هر دوی آن‌ها قرار دارند.\nعلامت آن ∪ (شبیه یک کاسه بزرگ) است.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextPrimary,
                        lineHeight = 22.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    FormulaBox(formula = "A ∪ B = { ۱، ۲، ۳، ۴، ۵ }", label = "A = {۱، ۲، ۳}  و  B = {۳، ۴، ۵}")
                }
            }
        }

        // The Interactive U-Shaped Bowl Game
        item {
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = CardDark,
                border = androidx.compose.foundation.BorderStroke(1.5.dp, MintPrimary.copy(alpha = 0.6f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "🥣 بازی تعاملی کاسه U شکل اجتماع",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MintPrimary
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "روی مهره‌ها ضربه بزنید تا وارد کاسه شوند (ضربه مجدد برای حذف):",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Source Set A
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "مجموعه A:",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = MintPrimary
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            setA.forEach { num ->
                                val inBowl = bowlElements.contains(num)
                                MathBead(
                                    number = num,
                                    isSelected = inBowl,
                                    color = MintPrimary,
                                    size = 46.dp,
                                    onClick = {
                                        bowlElements = if (inBowl) bowlElements - num else bowlElements + num
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Source Set B
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "مجموعه B:",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = PurpleAccent
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            setB.forEach { num ->
                                val inBowl = bowlElements.contains(num)
                                MathBead(
                                    number = num,
                                    isSelected = inBowl,
                                    color = PurpleAccent,
                                    size = 46.dp,
                                    onClick = {
                                        bowlElements = if (inBowl) bowlElements - num else bowlElements + num
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // Quick Actions
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = {
                                bowlElements = (setA + setB).toSet()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = SurfaceVariantDark)
                        ) {
                            Icon(Icons.Rounded.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("ریختن همه در کاسه", fontSize = 13.sp)
                        }

                        Button(
                            onClick = {
                                bowlElements = emptySet()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = SurfaceVariantDark)
                        ) {
                            Icon(Icons.Rounded.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("خالی کردن کاسه", fontSize = 13.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // U-Shaped Visual Bowl
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.92f)
                            .height(140.dp)
                            .clip(
                                RoundedCornerShape(
                                    topStart = 0.dp,
                                    topEnd = 0.dp,
                                    bottomStart = 70.dp,
                                    bottomEnd = 70.dp
                                )
                            )
                            .background(SurfaceVariantDark)
                            .border(
                                width = 3.dp,
                                color = MintPrimary,
                                shape = RoundedCornerShape(
                                    topStart = 0.dp,
                                    topEnd = 0.dp,
                                    bottomStart = 70.dp,
                                    bottomEnd = 70.dp
                                )
                            )
                            .padding(14.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        if (bowlElements.isEmpty()) {
                            Text(
                                text = "کاسه اجتماع ∪ خالی است\nمهره‌ها را اضافه کنید",
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextMuted,
                                textAlign = TextAlign.Center
                            )
                        } else {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                bowlElements.sorted().forEach { num ->
                                    val isShared = setA.contains(num) && setB.contains(num)
                                    MathBead(
                                        number = num,
                                        isSelected = true,
                                        color = if (isShared) GoldAccent else if (setA.contains(num)) MintPrimary else PurpleAccent,
                                        size = 44.dp
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Result Formula
                    val formulaResult = PersianUtils.formatSet(bowlElements, name = "A ∪ B")
                    FormulaBox(
                        formula = formulaResult,
                        label = "مجموعه حاصل در کاسه اجتماع"
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Key Math Note
                    if (bowlElements.contains(3)) {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = SuccessBg,
                            border = androidx.compose.foundation.BorderStroke(1.dp, SuccessSage.copy(alpha = 0.5f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Rounded.Info, contentDescription = null, tint = GoldAccent, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "نکته کلیدی: عدد ۳ در هر دو مجموعه بود، اما در کاسه فقط یک بار ظاهر شد؛ زیرا در مجموعه تکرار عضو مجاز نیست!",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextPrimary
                                )
                            }
                        }
                    }

                    val isFullUnion = bowlElements == (setA + setB).toSet()

                    if (isFullUnion) {
                        Spacer(modifier = Modifier.height(14.dp))
                        Button(
                            onClick = onSectionComplete,
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = MintPrimary)
                        ) {
                            Text("تکمیل بخش اجتماع و رفتن به اشتراک (∩)", color = BgDark, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// SECTION 2: INTERSECTION (اشتراک) WITH SORTING MACHINE & EMPTY SET CASE
// -------------------------------------------------------------
@Composable
fun IntersectionSectionView(
    onSectionComplete: () -> Unit
) {
    // Mode: 0 = Normal intersection {3, 4}, 1 = Disjoint sets / Empty set case {1, 2} & {3, 4} -> ∅
    var intersectionMode by remember { mutableIntStateOf(0) }

    val setA = if (intersectionMode == 0) listOf(1, 2, 3, 4) else listOf(1, 2)
    val setB = if (intersectionMode == 0) listOf(3, 4, 5, 6) else listOf(3, 4)
    val correctIntersection = (setA.toSet() intersect setB.toSet())

    var userGuessedShared by remember { mutableStateOf(setOf<Int>()) }
    var userGuessedEmpty by remember { mutableStateOf(false) }
    var isMachineRunning by remember { mutableStateOf(false) }
    var machineFinished by remember { mutableStateOf(false) }

    val rotationAngle by animateFloatAsState(
        targetValue = if (isMachineRunning) 360f else 0f,
        animationSpec = tween(durationMillis = 800, easing = LinearEasing),
        label = "machine_gear"
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .widthIn(max = 680.dp),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = CardDark,
                border = androidx.compose.foundation.BorderStroke(1.dp, BorderDark),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "مفهوم اشتراک دو مجموعه (A ∩ B)",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = PurpleAccent
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "اشتراک دو مجموعه، شامل اعضایی است که همزمان «هم در A و هم در B» وجود دارند.\nاگر هیچ عضو مشترکی نباشد، اشتراک آن‌ها «مجموعه تهی (∅)» است.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    FormulaBox(formula = "A ∩ B = { ۳، ۴ }", label = "A = {۱، ۲، ۳، ۴}  و  B = {۳، ۴، ۵، ۶}")
                }
            }
        }

        // Sorting Machine Game
        item {
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = CardDark,
                border = androidx.compose.foundation.BorderStroke(1.5.dp, PurpleAccent.copy(alpha = 0.6f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "⚙️ دستگاه فانتزی جداسازی اشتراک",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = PurpleAccent
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Mode Selector for Testing Both Normal and Empty Set Intersection
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(SurfaceVariantDark)
                            .padding(4.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = {
                                intersectionMode = 0
                                userGuessedShared = emptySet()
                                userGuessedEmpty = false
                                machineFinished = false
                                isMachineRunning = false
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (intersectionMode == 0) PurpleAccent else Color.Transparent
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("حالت ۱: اشتراک معمولی", fontSize = 12.sp, color = if (intersectionMode == 0) BgDark else TextSecondary, fontWeight = FontWeight.Bold)
                        }

                        Button(
                            onClick = {
                                intersectionMode = 1
                                userGuessedShared = emptySet()
                                userGuessedEmpty = false
                                machineFinished = false
                                isMachineRunning = false
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (intersectionMode == 1) PurpleAccent else Color.Transparent
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("حالت ۲: اشتراک تهی (∅)", fontSize = 12.sp, color = if (intersectionMode == 1) BgDark else TextSecondary, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Input Tubes A & B
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        // Tube A
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("ورودی A", style = MaterialTheme.typography.labelSmall, color = MintPrimary)
                            Spacer(modifier = Modifier.height(4.dp))
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = SurfaceVariantDark,
                                border = androidx.compose.foundation.BorderStroke(1.dp, MintPrimary.copy(alpha = 0.4f)),
                                modifier = Modifier.padding(4.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(8.dp),
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    setA.forEach { num ->
                                        MathBead(number = num, isSelected = false, color = MintPrimary, size = 36.dp)
                                    }
                                }
                            }
                        }

                        // Tube B
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("ورودی B", style = MaterialTheme.typography.labelSmall, color = PurpleAccent)
                            Spacer(modifier = Modifier.height(4.dp))
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = SurfaceVariantDark,
                                border = androidx.compose.foundation.BorderStroke(1.dp, PurpleAccent.copy(alpha = 0.4f)),
                                modifier = Modifier.padding(4.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(8.dp),
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    setB.forEach { num ->
                                        MathBead(number = num, isSelected = false, color = PurpleAccent, size = 36.dp)
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Guessing Row
                    Text(
                        text = "پیش‌بینی شما از اعضای مشترک خروجی:",
                        style = MaterialTheme.typography.labelMedium,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val allNumbers = (setA + setB).distinct().sorted()
                        allNumbers.forEach { num ->
                            val isGuessed = userGuessedShared.contains(num)
                            MathBead(
                                number = num,
                                isSelected = isGuessed,
                                color = PurpleAccent,
                                size = 44.dp,
                                onClick = {
                                    if (!machineFinished) {
                                        userGuessedEmpty = false
                                        userGuessedShared = if (isGuessed) userGuessedShared - num else userGuessedShared + num
                                    }
                                }
                            )
                        }

                        // Empty Set Guess Button
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = if (userGuessedEmpty) GoldAccent.copy(alpha = 0.2f) else SurfaceVariantDark,
                            border = androidx.compose.foundation.BorderStroke(1.dp, if (userGuessedEmpty) GoldAccent else BorderDark),
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .clickable {
                                    if (!machineFinished) {
                                        userGuessedEmpty = !userGuessedEmpty
                                        if (userGuessedEmpty) userGuessedShared = emptySet()
                                    }
                                }
                        ) {
                            Text(
                                text = "∅ تهی",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = if (userGuessedEmpty) GoldAccent else TextSecondary,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 12.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // The Machine Processing Core
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = SurfaceDark,
                        border = androidx.compose.foundation.BorderStroke(2.dp, if (isMachineRunning) GoldAccent else PurpleAccent),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.FilterAlt,
                                contentDescription = null,
                                tint = if (isMachineRunning) GoldAccent else PurpleAccent,
                                modifier = Modifier
                                    .size(40.dp)
                                    .rotate(rotationAngle)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = if (isMachineRunning) "دستگاه در حال غربالگری اعضا..." else "اتاقک پردازش اشتراک ∩",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Bold,
                                color = if (isMachineRunning) GoldAccent else PurpleAccent
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Run Button
                    val canRun = userGuessedShared.isNotEmpty() || userGuessedEmpty
                    if (!machineFinished) {
                        Button(
                            onClick = {
                                isMachineRunning = true
                                machineFinished = true
                            },
                            enabled = canRun,
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = PurpleAccent)
                        ) {
                            Icon(Icons.Rounded.PlayArrow, contentDescription = null, tint = BgDark)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("روشن کردن دستگاه جداساز", color = BgDark, fontWeight = FontWeight.Bold)
                        }
                    } else {
                        // Machine Output Results
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "خروجی دستگاه (اشتراک دو مجموعه):",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = MintPrimary
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            if (correctIntersection.isEmpty()) {
                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = ErrorBg,
                                    border = androidx.compose.foundation.BorderStroke(1.5.dp, GoldAccent),
                                    modifier = Modifier.padding(8.dp)
                                ) {
                                    Text(
                                        text = "∅ (مجموعه تهی)",
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = GoldAccent,
                                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 10.dp)
                                    )
                                }
                            } else {
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    correctIntersection.forEach { num ->
                                        MathBead(number = num, isSelected = true, color = MintPrimary, size = 48.dp)
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))
                            FormulaBox(
                                formula = if (correctIntersection.isEmpty()) "A ∩ B = ∅" else "A ∩ B = { ۳، ۴ }"
                            )

                            val isGuessCorrect = if (correctIntersection.isEmpty()) {
                                userGuessedEmpty
                            } else {
                                userGuessedShared == correctIntersection
                            }

                            Spacer(modifier = Modifier.height(10.dp))
                            FeedbackCard(
                                isSuccess = isGuessCorrect,
                                message = if (isGuessCorrect) "حدس شما کاملاً دقیق بود!" else "نتایج دستگاه را بررسی کنید",
                                explanation = if (correctIntersection.isEmpty())
                                    "مجموعه A = {۱، ۲} و مجموعه B = {۳، ۴} هیچ عضو مشترکی ندارند. بنابراین دستگاه هیچ عضوی را از خروجی خارج نکرد و حاصل مجموعه تهی (∅) است."
                                else
                                    "تنها اعداد ۳ و ۴ در هر دو مجموعه A و B مشترک هستند و از غربال دستگاه خارج شدند.",
                                onContinue = onSectionComplete,
                                buttonText = "رفتن به بخش تفاضل (−)"
                            )
                        }
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// SECTION 3: DIFFERENCE (تفاضل) WITH DIRECTIONAL FILTER
// -------------------------------------------------------------
@Composable
fun DifferenceSectionView(
    onSectionComplete: () -> Unit,
    onNextLesson: () -> Unit = {}
) {
    val setA = remember { listOf(1, 2, 3, 4) }
    val setB = remember { listOf(3, 4, 5) }

    // Toggle direction: true = A - B, false = B - A
    var isADiffB by remember { mutableStateOf(true) }
    var filterExecuted by remember { mutableStateOf(false) }

    val resultElements = if (isADiffB) listOf(1, 2) else listOf(5)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .widthIn(max = 680.dp),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = CardDark,
                border = androidx.compose.foundation.BorderStroke(1.dp, BorderDark),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "مفهوم تفاضل دو مجموعه (A − B)",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = CyanAccent
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "تفاضل A − B یعنی «اعضایی از A که در B نباشند». به عبارتی اعضای مشترک از مجموعه اول حذف می‌شوند.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "هشدار مهم: تفاضل مجموعه‌ها خاصیت جابجایی ندارد! معمولاً A − B ≠ B − A است.",
                        style = MaterialTheme.typography.bodySmall,
                        color = GoldAccent
                    )
                }
            }
        }

        // Difference Machine
        item {
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = CardDark,
                border = androidx.compose.foundation.BorderStroke(1.5.dp, CyanAccent.copy(alpha = 0.6f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "🔬 دستگاه فیلتر تفاضل مجموعه‌ها",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = CyanAccent
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Direction Switcher
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(SurfaceVariantDark)
                            .padding(4.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = {
                                isADiffB = true
                                filterExecuted = false
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isADiffB) CyanAccent else Color.Transparent
                            ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("محاسبه A − B", color = if (isADiffB) BgDark else TextSecondary, fontWeight = FontWeight.Bold)
                        }

                        Button(
                            onClick = {
                                isADiffB = false
                                filterExecuted = false
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (!isADiffB) CyanAccent else Color.Transparent
                            ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("محاسبه B − A", color = if (!isADiffB) BgDark else TextSecondary, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Display Input Sets
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        androidx.compose.runtime.CompositionLocalProvider(
                            androidx.compose.ui.platform.LocalLayoutDirection provides androidx.compose.ui.unit.LayoutDirection.Ltr
                        ) {
                            Text(text = "A = { ۱، ۲، ۳، ۴ }", color = MintPrimary, fontWeight = FontWeight.Bold)
                        }
                        androidx.compose.runtime.CompositionLocalProvider(
                            androidx.compose.ui.platform.LocalLayoutDirection provides androidx.compose.ui.unit.LayoutDirection.Ltr
                        ) {
                            Text(text = "B = { ۳، ۴، ۵ }", color = PurpleAccent, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    if (!filterExecuted) {
                        Button(
                            onClick = { filterExecuted = true },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = CyanAccent)
                        ) {
                            Icon(Icons.Rounded.FilterAlt, contentDescription = null, tint = BgDark)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (isADiffB) "عبور A از فیلتر B" else "عبور B از فیلتر A",
                                color = BgDark,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    } else {
                        // Filter Result
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = if (isADiffB) "خروجی A − B (اعضای A منهای مشترکات با B):" else "خروجی B − A (اعضای B منهای مشترکات با A):",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                resultElements.forEach { num ->
                                    MathBead(
                                        number = num,
                                        isSelected = true,
                                        color = if (isADiffB) CyanAccent else PurpleAccent,
                                        size = 48.dp
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            FormulaBox(
                                formula = if (isADiffB) "A − B = { ۱، ۲ }" else "B − A = { ۵ }",
                                label = "نتیجه تفاضل"
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            FeedbackCard(
                                isSuccess = true,
                                message = "تحلیل دقیق ریاضی:",
                                explanation = if (isADiffB)
                                    "اعداد ۳ و ۴ در B هم بودند و فیلتر شدند. فقط ۱ و ۲ در A ماندند، بنابراین A − B = {۱، ۲}."
                                else
                                    "اعداد ۳ و ۴ در A هم بودند و فیلتر شدند. فقط ۵ در B باقی ماند، بنابراین B − A = {۵}. مشاهده می‌کنید که حاصل کاملاً متفاوت است!",
                                onContinue = {
                                    onSectionComplete()
                                },
                                buttonText = "ثبت پاداش و پایان درس دوم"
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Button(
                                onClick = onNextLesson,
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(containerColor = MintPrimary)
                            ) {
                                Text("ورود به درس سوم: مجموعه‌ها و احتمال", color = BgDark, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}
