package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.automirrored.rounded.HelpOutline
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Extension
import androidx.compose.material.icons.rounded.Lightbulb
import androidx.compose.material.icons.rounded.WarningAmber
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.FeedbackCard
import com.example.ui.components.FormulaBox
import com.example.ui.components.GlassCard
import com.example.ui.components.MathCraftTopBar
import com.example.ui.theme.BgDark
import com.example.ui.theme.BorderDark
import com.example.ui.theme.CardDark
import com.example.ui.theme.CoralRed
import com.example.ui.theme.ErrorBg
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.MintPrimary
import com.example.ui.theme.MintSecondary
import com.example.ui.theme.PurpleAccent
import com.example.ui.theme.SurfaceDark
import com.example.ui.theme.SurfaceVariantDark
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.util.PersianUtils

@Composable
fun Lesson1Screen(
    currentStars: Int,
    currentXp: Int,
    onBack: () -> Unit,
    onCompleteLesson: (stars: Int, xpEarned: Int) -> Unit,
    onNextLesson: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }

    var selectedTab by remember { mutableIntStateOf(0) } // 0: درسنامه, 1: بازی و چالش‌ها

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Transparent)
    ) {
        MathCraftTopBar(
            title = "درس اول: آشنایی با مجموعه‌ها",
            stars = currentStars,
            xp = currentXp,
            onBackClick = onBack
        )

        // Sub Navigation Tabs
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = SurfaceDark.copy(alpha = 0.78f),
            contentColor = TextPrimary,
            indicator = { tabPositions ->
                SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                    color = MintPrimary
                )
            }
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = {
                    Text(
                        text = "درسنامه و مفاهیم",
                        fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Normal,
                        color = if (selectedTab == 0) MintPrimary else TextSecondary
                    )
                }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Rounded.Extension,
                            contentDescription = null,
                            tint = if (selectedTab == 1) PurpleAccent else TextSecondary,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "بازی و چالش تعاملی",
                            fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Normal,
                            color = if (selectedTab == 1) PurpleAccent else TextSecondary
                        )
                    }
                }
            )
        }

        if (selectedTab == 0) {
            Lesson1TheoryView(
                onGoToGame = { selectedTab = 1 }
            )
        } else {
            Lesson1GameView(
                onCompleteAll = { stars, xp ->
                    onCompleteLesson(stars, xp)
                },
                onNextLesson = onNextLesson
            )
        }
    }
}

@Composable
fun Lesson1TheoryView(
    onGoToGame: () -> Unit
) {
    // Interactive Example State (A = {1, 2, 3} & B = {3, 1, 2})
    var exampleAnswer by remember { mutableStateOf<Boolean?>(null) }
    var showExampleFeedback by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .widthIn(max = 680.dp),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Concept 1: Definition of Set
        item {
            ConceptCard(
                title = "۱. مجموعه چیست؟",
                icon = "📦"
            ) {
                Text(
                    text = "در ریاضیات نهم، «مجموعه» به دسته‌ای از اشیا یا اعداد گفته می‌شود که کاملاً «مشخص» و «دو به دو متمایز (غیرتکراری)» باشند.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextPrimary,
                    lineHeight = 24.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "مثال روزمره: «فصل‌های سال» یک مجموعه مشخص چهار عضوی است: {بهار، تابستان، پاییز، زمستان}.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MintPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "اما عبارت «سه فیلم خوب امسال» یک مجموعه ریاضی نیست؛ زیرا سلیقه‌ای است و اعضای آن برای همه دقیقاً مشخص نیستند!",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
            }
        }

        // Concept 2: Notation and Symbols
        item {
            ConceptCard(
                title = "۲. نمایش با آکولاد و نمادهای ریاضی",
                icon = "✨"
            ) {
                Text(
                    text = "مجموعه‌ها را با حروف بزرگ انگلیسی مثل A, B, C و اعضای آن‌ها را داخل دو آکولاد { } قرار می‌دهیم:",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(8.dp))
                FormulaBox(formula = "A = { ۲، ۴، ۶، ۸ }", label = "مجموعه اعداد زوج یک‌رقمی مثبت")
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    SymbolPill(symbol = "∈", meaning = "عضو بودن", example = "۲ ∈ A", modifier = Modifier.weight(1f))
                    SymbolPill(symbol = "∉", meaning = "عضو نبودن", example = "۳ ∉ A", modifier = Modifier.weight(1f))
                }
            }
        }

        // Concept 3: Empty Set & Common Mistake
        item {
            ConceptCard(
                title = "۳. مجموعه تهی (خالی)",
                icon = "📭"
            ) {
                Text(
                    text = "مجموعه‌ای که هیچ عضوی نداشته باشد را «مجموعه تهی» می‌نامند و با نماد ∅ یا { } نشان داده می‌شود.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(8.dp))
                FormulaBox(formula = "∅  یا  { }")
                Spacer(modifier = Modifier.height(10.dp))

                // Common Mistake Alert
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = ErrorBg,
                    border = androidx.compose.foundation.BorderStroke(1.dp, CoralRed.copy(alpha = 0.6f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.WarningAmber,
                            contentDescription = null,
                            tint = CoralRed,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = "اشتباه رایج دانش‌آموزان:",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = CoralRed
                            )
                            Text(
                                text = "نماد {∅} تهی نیست! بلکه یک مجموعه تک عضوی است که عضو آن نماد ∅ است.",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextPrimary
                            )
                        }
                    }
                }
            }
        }

        // Concept 4: Subset & Equal Sets
        item {
            ConceptCard(
                title = "۴. زیرمجموعه (⊆) و مجموعه‌های برابر (=)",
                icon = "⚖️"
            ) {
                Text(
                    text = "اگر هر عضو مجموعه A در مجموعه B نیز وجود داشته باشد، می‌گوییم A زیرمجموعه B است (A ⊆ B).",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(8.dp))
                FormulaBox(formula = "A = { ۱، ۲ }  ⊆  B = { ۱، ۲، ۳ }")
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "دو نکته طلایی در برابری مجموعه‌ها:\n۱) ترتیب نوشتن اعضا اهمیتی ندارد.\n۲) تکرار یک عضو، عضو جدیدی ایجاد نمی‌کند ({۱، ۲، ۱} همان {۱، ۲} است).",
                    style = MaterialTheme.typography.bodySmall,
                    color = MintPrimary,
                    lineHeight = 22.sp
                )
            }
        }

        // Interactive Exercise from textbook
        item {
            GlassCard(
                shape = RoundedCornerShape(18.dp),
                accentTint = PurpleAccent,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.HelpOutline,
                            contentDescription = null,
                            tint = PurpleAccent,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "تمرین تعاملی کتاب درسی",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = PurpleAccent
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "دو مجموعه زیر را در نظر بگیرید:\nA = { ۱، ۲، ۳ }\nB = { ۳، ۱، ۲ }",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary,
                        lineHeight = 22.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "آیا دو مجموعه A و B با یکدیگر برابرند (A = B)؟",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = {
                                exampleAnswer = true
                                showExampleFeedback = true
                            },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("lesson1_exercise_yes"),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (exampleAnswer == true) MintPrimary else SurfaceVariantDark
                            )
                        ) {
                            Text(
                                text = "بله، برابرند",
                                color = if (exampleAnswer == true) BgDark else TextPrimary,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Button(
                            onClick = {
                                exampleAnswer = false
                                showExampleFeedback = true
                            },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("lesson1_exercise_no"),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (exampleAnswer == false) CoralRed else SurfaceVariantDark
                            )
                        ) {
                            Text(
                                text = "خیر، برابر نیستند",
                                color = if (exampleAnswer == false) BgDark else TextPrimary,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    if (showExampleFeedback) {
                        Spacer(modifier = Modifier.height(12.dp))
                        val isCorrect = exampleAnswer == true
                        FeedbackCard(
                            isSuccess = isCorrect,
                            message = if (isCorrect) "کاملاً درسته! احسنت!" else "دقت کن دانشمند عزیز!",
                            explanation = "در مجموعه‌ها ترتیب نوشتن اعضا اصلاً مهم نیست. چون تمام اعضای A (شامل ۱، ۲، ۳) دقیقاً در B هم هستند، بنابراین دو مجموعه کاملاً برابرند: A = B.",
                            onContinue = { showExampleFeedback = false },
                            buttonText = "متوجه شدم"
                        )
                    }
                }
            }
        }

        // Action: Go to Interactive Game
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onGoToGame,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("go_to_lesson1_game_btn"),
                colors = ButtonDefaults.buttonColors(containerColor = PurpleAccent),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Extension,
                        contentDescription = null,
                        tint = BgDark
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "ورود به بازی و چالش‌های درس اول",
                        color = BgDark,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun Lesson1GameView(
    onCompleteAll: (stars: Int, xp: Int) -> Unit,
    onNextLesson: () -> Unit = {}
) {
    // 4 mini game stages:
    // Stage 0: شکارچی اعضای A = {2, 4, 6, 8}
    // Stage 1: تشخیص نماد عضو یا غیرعضو
    // Stage 2: تشخیص مجموعه تهی
    // Stage 3: تشخیص زیرمجموعه و برابری
    var currentStage by remember { mutableIntStateOf(0) }
    var gameStars by remember { mutableIntStateOf(0) }
    var gameCompleted by remember { mutableStateOf(false) }

    // Stage 0 State: Filter numbers for A = {2, 4, 6, 8}
    val candidateNumbers = remember { listOf(2, 3, 4, 5, 6, 7, 8, 9) }
    val correctSet = remember { setOf(2, 4, 6, 8) }
    var selectedNumbers by remember { mutableStateOf(setOf<Int>()) }
    var stage0Checked by remember { mutableStateOf(false) }
    var stage0Success by remember { mutableStateOf(false) }

    // Stage 1 State: Symbol Challenge
    // Example: ۵ ... A
    var stage1SelectedSymbol by remember { mutableStateOf<String?>(null) }
    var stage1Checked by remember { mutableStateOf(false) }

    // Stage 2 State: Empty Set
    var stage2SelectedChoice by remember { mutableStateOf<Int?>(null) }
    var stage2Checked by remember { mutableStateOf(false) }

    // Stage 3 State: Subset Challenge
    var stage3SelectedChoice by remember { mutableStateOf<Int?>(null) }
    var stage3Checked by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .widthIn(max = 680.dp),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (!gameCompleted) {
            // Stage Indicator
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "چالش ${PersianUtils.toPersian(currentStage + 1)} از ۴",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MintPrimary
                    )
                    Text(
                        text = "ستاره‌های این بخش: ${PersianUtils.toPersian(gameStars)} ⭐",
                        style = MaterialTheme.typography.labelLarge,
                        color = GoldAccent
                    )
                }
            }

            when (currentStage) {
                0 -> {
                    // Stage 0: Hunter Game
                    item {
                        Surface(
                            shape = RoundedCornerShape(18.dp),
                            color = CardDark,
                            border = androidx.compose.foundation.BorderStroke(1.dp, BorderDark),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(18.dp)) {
                                Text(
                                    text = "🎯 بازی شکارچی اعضای مجموعه",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = MintPrimary
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "مجموعه اعداد زوج یک‌رقمی مثبت:\nA = { ۲، ۴، ۶، ۸ }",
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "از مهره‌های زیر، تنها اعدادی را انتخاب کنید که متعلق به مجموعه A هستند و بقیه را کنار بگذارید:",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = TextSecondary
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                // Number Beads Grid
                                val rows = candidateNumbers.chunked(4)
                                rows.forEach { rowNumbers ->
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceEvenly
                                    ) {
                                        rowNumbers.forEach { num ->
                                            val isSelected = selectedNumbers.contains(num)
                                            Box(
                                                modifier = Modifier
                                                    .size(54.dp)
                                                    .clip(CircleShape)
                                                    .background(
                                                        if (isSelected) MintPrimary else SurfaceVariantDark
                                                    )
                                                    .border(
                                                        1.5.dp,
                                                        if (isSelected) GoldAccent else BorderDark,
                                                        CircleShape
                                                    )
                                                    .clickable {
                                                        if (!stage0Checked) {
                                                            selectedNumbers = if (isSelected) {
                                                                selectedNumbers - num
                                                            } else {
                                                                selectedNumbers + num
                                                            }
                                                        }
                                                    }
                                                    .testTag("bead_$num"),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = PersianUtils.toPersian(num),
                                                    fontSize = 20.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = if (isSelected) BgDark else TextPrimary
                                                )
                                            }
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(10.dp))
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                if (!stage0Checked) {
                                    Button(
                                        onClick = {
                                            stage0Checked = true
                                            stage0Success = (selectedNumbers == correctSet)
                                            if (stage0Success) {
                                                gameStars++
                                            }
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .testTag("check_stage0_btn"),
                                        colors = ButtonDefaults.buttonColors(containerColor = MintPrimary),
                                        shape = RoundedCornerShape(10.dp)
                                    ) {
                                        Text("بررسی پاسخ مهره‌ها", color = BgDark, fontWeight = FontWeight.Bold)
                                    }
                                } else {
                                    FeedbackCard(
                                        isSuccess = stage0Success,
                                        message = if (stage0Success) "آفرین! تمام اعضای درست را شکار کردی!" else "دوباره بررسی کن!",
                                        explanation = if (stage0Success)
                                            "دقیقاً اعداد ۲، ۴، ۶ و ۸ متعلق به این مجموعه‌اند و اعداد فرد مثل ۳ و ۵ و ۷ و ۹ عضو آن نیستند."
                                        else
                                            "اعداد ۲، ۴، ۶ و ۸ زوج هستند و باید انتخاب شوند. اعداد فرد نباید انتخاب شوند.",
                                        onContinue = {
                                            if (stage0Success) {
                                                currentStage = 1
                                            } else {
                                                stage0Checked = false
                                                selectedNumbers = emptySet()
                                            }
                                        },
                                        buttonText = if (stage0Success) "مرحله بعد" else "تلاش مجدد"
                                    )
                                }
                            }
                        }
                    }
                }

                1 -> {
                    // Stage 1: Member or Not
                    item {
                        Surface(
                            shape = RoundedCornerShape(18.dp),
                            color = CardDark,
                            border = androidx.compose.foundation.BorderStroke(1.dp, BorderDark),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(18.dp)) {
                                Text(
                                    text = "🔍 تشخیص نماد عضویت",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = PurpleAccent
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "اگر مجموعه B = { ۱، ۳، ۵، ۷ } باشد:\nکدام نماد باید در جای خالی قرار گیرد؟",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = TextSecondary
                                )

                                Spacer(modifier = Modifier.height(14.dp))
                                FormulaBox(
                                    formula = if (stage1SelectedSymbol != null) "۵  $stage1SelectedSymbol  B" else "۵   [ ? ]   B",
                                    label = "رابطه ریاضی مورد نظر"
                                )
                                Spacer(modifier = Modifier.height(14.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    Button(
                                        onClick = {
                                            if (!stage1Checked) {
                                                stage1SelectedSymbol = "∈"
                                                stage1Checked = true
                                                gameStars++
                                            }
                                        },
                                        modifier = Modifier.weight(1f),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = if (stage1SelectedSymbol == "∈") MintPrimary else SurfaceVariantDark
                                        )
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.Center
                                        ) {
                                            androidx.compose.runtime.CompositionLocalProvider(
                                                androidx.compose.ui.platform.LocalLayoutDirection provides androidx.compose.ui.unit.LayoutDirection.Ltr
                                            ) {
                                                Text("∈", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = if (stage1SelectedSymbol == "∈") BgDark else MintPrimary)
                                            }
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text("(عضو است)", fontSize = 14.sp, color = if (stage1SelectedSymbol == "∈") BgDark else TextPrimary)
                                        }
                                    }

                                    Button(
                                        onClick = {
                                            if (!stage1Checked) {
                                                stage1SelectedSymbol = "∉"
                                                stage1Checked = true
                                            }
                                        },
                                        modifier = Modifier.weight(1f),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = if (stage1SelectedSymbol == "∉") CoralRed else SurfaceVariantDark
                                        )
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.Center
                                        ) {
                                            androidx.compose.runtime.CompositionLocalProvider(
                                                androidx.compose.ui.platform.LocalLayoutDirection provides androidx.compose.ui.unit.LayoutDirection.Ltr
                                            ) {
                                                Text("∉", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = if (stage1SelectedSymbol == "∉") BgDark else CoralRed)
                                            }
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text("(عضو نیست)", fontSize = 14.sp, color = if (stage1SelectedSymbol == "∉") BgDark else TextPrimary)
                                        }
                                    }
                                }

                                if (stage1Checked) {
                                    val isSuccess = stage1SelectedSymbol == "∈"
                                    Spacer(modifier = Modifier.height(12.dp))
                                    FeedbackCard(
                                        isSuccess = isSuccess,
                                        message = if (isSuccess) "پاسخ صحیح است!" else "اشتباه شد!",
                                        explanation = "عدد ۵ به وضوح داخل آکولادهای مجموعه B فهرست شده است؛ بنابراین ۵ ∈ B صحیح است.",
                                        onContinue = {
                                            if (isSuccess) {
                                                currentStage = 2
                                            } else {
                                                stage1Checked = false
                                                stage1SelectedSymbol = null
                                            }
                                        },
                                        buttonText = if (isSuccess) "مرحله بعد" else "تلاش دوباره"
                                    )
                                }
                            }
                        }
                    }
                }

                2 -> {
                    // Stage 2: Empty Set
                    item {
                        Surface(
                            shape = RoundedCornerShape(18.dp),
                            color = CardDark,
                            border = androidx.compose.foundation.BorderStroke(1.dp, BorderDark),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(18.dp)) {
                                Text(
                                    text = "📭 کدام گزینه یک «مجموعه تهی» است؟",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = MintPrimary
                                )
                                Spacer(modifier = Modifier.height(12.dp))

                                val options = listOf(
                                    "مجموعه اعداد طبیعی کوچکتر از ۱",
                                    "مجموعه اعداد اول زوج",
                                    "مجموعه { ۰ }",
                                    "مجموعه { ∅ }"
                                )

                                options.forEachIndexed { index, option ->
                                    val isSelected = stage2SelectedChoice == index
                                    Surface(
                                        shape = RoundedCornerShape(10.dp),
                                        color = if (isSelected) MintPrimary.copy(alpha = 0.2f) else SurfaceVariantDark,
                                        border = androidx.compose.foundation.BorderStroke(
                                            1.dp,
                                            if (isSelected) MintPrimary else BorderDark
                                        ),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp)
                                            .clickable {
                                                if (!stage2Checked) stage2SelectedChoice = index
                                            }
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(12.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = "${PersianUtils.toPersian(index + 1)})",
                                                fontWeight = FontWeight.Bold,
                                                color = MintPrimary
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(
                                                text = option,
                                                style = MaterialTheme.typography.bodyMedium,
                                                color = TextPrimary
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                if (!stage2Checked) {
                                    Button(
                                        onClick = {
                                            if (stage2SelectedChoice != null) {
                                                stage2Checked = true
                                                if (stage2SelectedChoice == 0) gameStars++
                                            }
                                        },
                                        enabled = stage2SelectedChoice != null,
                                        modifier = Modifier.fillMaxWidth(),
                                        colors = ButtonDefaults.buttonColors(containerColor = MintPrimary)
                                    ) {
                                        Text("ثبت پاسخ", color = BgDark, fontWeight = FontWeight.Bold)
                                    }
                                } else {
                                    val isSuccess = stage2SelectedChoice == 0
                                    FeedbackCard(
                                        isSuccess = isSuccess,
                                        message = if (isSuccess) "عالی بود!" else "پاسخ نادرست است",
                                        explanation = if (isSuccess)
                                            "کوچکترین عدد طبیعی عدد ۱ است، پس هیچ عدد طبیعی کوچکتر از ۱ وجود ندارد و این مجموعه تهی (∅) است."
                                        else
                                            "اعداد طبیعی از ۱ شروع می‌شوند، پس عددی کوچکتر از ۱ وجود ندارد و گزینه ۱ تهی است. گزینه ۲ شامل عدد ۲ است و گزینه‌های ۳ و ۴ هر کدام یک عضو دارند.",
                                        onContinue = {
                                            if (isSuccess) {
                                                currentStage = 3
                                            } else {
                                                stage2Checked = false
                                                stage2SelectedChoice = null
                                            }
                                        },
                                        buttonText = if (isSuccess) "مرحله بعد" else "تلاش مجدد"
                                    )
                                }
                            }
                        }
                    }
                }

                3 -> {
                    // Stage 3: Subset and Equality
                    item {
                        Surface(
                            shape = RoundedCornerShape(18.dp),
                            color = CardDark,
                            border = androidx.compose.foundation.BorderStroke(1.dp, BorderDark),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(18.dp)) {
                                Text(
                                    text = "⚖️ برابری و زیرمجموعه",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = PurpleAccent
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "کدام عبارت زیر در مورد مجموعه‌ها صحیح است؟",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = TextSecondary
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                val options = listOf(
                                    "{ ۱، ۲ } = { ۲، ۱، ۲ }",
                                    "{ ۱، ۲ } ⊆ { ۲، ۳ }",
                                    "∅ ∉ تمام مجموعه‌ها",
                                    "{ ۱، ۲ } = { ۱، ۲، ۳ }"
                                )

                                options.forEachIndexed { index, option ->
                                    val isSelected = stage3SelectedChoice == index
                                    Surface(
                                        shape = RoundedCornerShape(10.dp),
                                        color = if (isSelected) PurpleAccent.copy(alpha = 0.2f) else SurfaceVariantDark,
                                        border = androidx.compose.foundation.BorderStroke(
                                            1.dp,
                                            if (isSelected) PurpleAccent else BorderDark
                                        ),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp)
                                            .clickable {
                                                if (!stage3Checked) stage3SelectedChoice = index
                                            }
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(12.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = "${PersianUtils.toPersian(index + 1)})",
                                                fontWeight = FontWeight.Bold,
                                                color = PurpleAccent
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            androidx.compose.runtime.CompositionLocalProvider(
                                                androidx.compose.ui.platform.LocalLayoutDirection provides androidx.compose.ui.unit.LayoutDirection.Ltr
                                            ) {
                                                Text(
                                                    text = option,
                                                    style = MaterialTheme.typography.bodyMedium,
                                                    color = TextPrimary
                                                )
                                            }
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                if (!stage3Checked) {
                                    Button(
                                        onClick = {
                                            if (stage3SelectedChoice != null) {
                                                stage3Checked = true
                                                if (stage3SelectedChoice == 0) gameStars++
                                            }
                                        },
                                        enabled = stage3SelectedChoice != null,
                                        modifier = Modifier.fillMaxWidth(),
                                        colors = ButtonDefaults.buttonColors(containerColor = PurpleAccent)
                                    ) {
                                        Text("ثبت پاسخ نهایی", color = BgDark, fontWeight = FontWeight.Bold)
                                    }
                                } else {
                                    val isSuccess = stage3SelectedChoice == 0
                                    FeedbackCard(
                                        isSuccess = isSuccess,
                                        message = if (isSuccess) "فوق‌العاده بود! تسلط کامل داری!" else "دقت بیشتر لازمه!",
                                        explanation = "تکرار عضو ۲ در سمت راست عضو جدیدی نمی‌سازد و ترتیب هم ملاک نیست؛ پس {۱، ۲} دقیقاً برابر با {۲، ۱، ۲} است.",
                                        onContinue = {
                                            gameCompleted = true
                                            val finalStars = minOf(3, maxOf(1, gameStars))
                                            onCompleteAll(finalStars, 75)
                                        },
                                        buttonText = "پایان درس اول و دریافت پاداش"
                                    )
                                }
                            }
                        }
                    }
                }
            }
        } else {
            // Completion Screen
            item {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = CardDark,
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, MintPrimary),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "🎉 تبریک! درس اول کامل شد!",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MintPrimary
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "شما با موفقیت مفاهیم مجموعه، نمادهای عضویت و برابری را یاد گرفتید.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondary,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val awardedStars = minOf(3, maxOf(1, gameStars))
                            repeat(awardedStars) {
                                Text(text = "⭐", fontSize = 32.sp)
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "+۷۵ امتیاز تجربه (XP) کسب کردید!",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = PurpleAccent
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Button(
                            onClick = onNextLesson,
                            colors = ButtonDefaults.buttonColors(containerColor = MintPrimary),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("ورود به درس دوم: اجتماع و اشتراک", color = BgDark, fontWeight = FontWeight.Bold)
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Button(
                            onClick = {
                                // Reset for replay if desired
                                currentStage = 0
                                gameStars = 0
                                gameCompleted = false
                                stage0Checked = false
                                stage1Checked = false
                                stage2Checked = false
                                stage3Checked = false
                                selectedNumbers = emptySet()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = SurfaceVariantDark),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("تکرار بازی این درس", color = TextPrimary)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ConceptCard(
    title: String,
    icon: String,
    content: @Composable () -> Unit
) {
    GlassCard(
        shape = RoundedCornerShape(18.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = icon, fontSize = 18.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MintPrimary
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            content()
        }
    }
}

@Composable
fun SymbolPill(
    symbol: String,
    meaning: String,
    example: String,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = SurfaceVariantDark,
        border = androidx.compose.foundation.BorderStroke(1.dp, BorderDark),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            androidx.compose.runtime.CompositionLocalProvider(
                androidx.compose.ui.platform.LocalLayoutDirection provides androidx.compose.ui.unit.LayoutDirection.Ltr
            ) {
                Text(
                    text = symbol,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = MintPrimary
                )
            }
            Text(
                text = meaning,
                style = MaterialTheme.typography.labelSmall,
                color = TextSecondary
            )
            Spacer(modifier = Modifier.height(4.dp))
            androidx.compose.runtime.CompositionLocalProvider(
                androidx.compose.ui.platform.LocalLayoutDirection provides androidx.compose.ui.unit.LayoutDirection.Ltr
            ) {
                Text(
                    text = example,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            }
        }
    }
}
