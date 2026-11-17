package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.EmojiEvents
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.QuizQuestion
import com.example.ui.components.GlassCard
import com.example.ui.components.MathCraftTopBar
import com.example.ui.theme.BgDark
import com.example.ui.theme.BorderDark
import com.example.ui.theme.CardDark
import com.example.ui.theme.CoralRed
import com.example.ui.theme.ErrorBg
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.PureWhite
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
fun FinalQuizScreen(
    currentStars: Int,
    currentXp: Int,
    onBack: () -> Unit,
    onCompleteQuiz: (score: Int, correctCount: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }

    // 5 Comprehensive Exam Questions
    val questions = remember {
        listOf(
            QuizQuestion(
                id = 1,
                topic = "۱. تشخیص عضو یا غیرعضو",
                question = "اگر A = { ۲، ۳، {۴} } باشد، کدام گزینه نادرست است؟",
                options = listOf(
                    "۲ ∈ A",
                    "{۴} ∈ A",
                    "۴ ∈ A",
                    "۵ ∉ A"
                ),
                correctIndex = 2,
                explanation = "عضو مجموعه A خودِ «{۴}» است نه عدد ۴. عدد ۴ به تنهایی عضو A نیست، بنابراین ۴ ∈ A نادرست است."
            ),
            QuizQuestion(
                id = 2,
                topic = "۲. برابری مجموعه‌ها و زیرمجموعه",
                question = "کدام رابطه زیر همواره درست است؟",
                options = listOf(
                    "{ ۲، ۱ } = { ۱، ۲، ۱ }",
                    "∅ ⊆ همه مجموعه‌ها جز خودش",
                    "{ ۱، ۲ } ⊆ { ۱، ۳ }",
                    "{ ∅ } = ∅"
                ),
                correctIndex = 0,
                explanation = "در مجموعه‌ها تکرار اعضا عضو جدید نمی‌سازد و ترتیب نوشتن مهم نیست؛ بنابراین {۲، ۱} کاملاً برابر با {۱، ۲، ۱} است."
            ),
            QuizQuestion(
                id = 3,
                topic = "۳. محاسبه اجتماع یا اشتراک",
                question = "اگر A = { ۱، ۲، ۳ } و B = { ۳، ۴، ۵ } باشد، حاصل A ∩ B کدام است؟",
                options = listOf(
                    "{ ۱، ۲، ۳، ۴، ۵ }",
                    "{ ۳ }",
                    "∅",
                    "{ ۱، ۲، ۴، ۵ }"
                ),
                correctIndex = 1,
                explanation = "اشتراک (∩) یعنی اعضایی که در هر دو مجموعه مشترک هستند. تنها عدد ۳ هم در A و هم در B وجود دارد."
            ),
            QuizQuestion(
                id = 4,
                topic = "۴. محاسبه تفاضل مجموعه‌ها",
                question = "اگر A = { ۱، ۲، ۳، ۴ } و B = { ۳، ۴، ۵ } باشد، حاصل A − B کدام است؟",
                options = listOf(
                    "{ ۵ }",
                    "{ ۱، ۲ }",
                    "{ ۱، ۲، ۵ }",
                    "{ ۳، ۴ }"
                ),
                correctIndex = 1,
                explanation = "تفاضل A − B یعنی اعضایی از A که در B نباشند. اعداد ۳ و ۴ در B هم هستند پس حذف می‌شوند و {۱، ۲} باقی می‌ماند."
            ),
            QuizQuestion(
                id = 5,
                topic = "۵. احتمال یک پیشامد ساده",
                question = "در پرتاب یک تاس سالم، احتمال آمدن «عدد کمتر از ۳» چقدر است؟",
                options = listOf(
                    "۲ / ۶ = ۱ / ۳",
                    "۳ / ۶ = ۱ / ۲",
                    "۱ / ۶",
                    "۴ / ۶ = ۲ / ۳"
                ),
                correctIndex = 0,
                explanation = "فضای نمونه ۶ حالت دارد: S = {۱، ۲، ۳، ۴، ۵، ۶}. اعداد کمتر از ۳ شامل {۱، ۲} (۲ عضو) هستند، بنابراین احتمال ۲/۶ = ۱/۳ است."
            )
        )
    }

    var currentQuestionIndex by remember { mutableIntStateOf(0) }
    val userAnswers = remember { mutableStateMapOf<Int, Int>() } // questionId -> selectedIndex
    var isSubmitted by remember { mutableStateOf(false) }
    var isReviewMode by remember { mutableStateOf(false) }

    val currentQ = questions[currentQuestionIndex]
    val selectedOptionForCurrent = userAnswers[currentQ.id]

    // Calculation of results
    val correctCount = questions.count { userAnswers[it.id] == it.correctIndex }
    val wrongCount = questions.size - correctCount
    val totalScore = correctCount * 20
    val percentage = (correctCount * 100) / questions.size

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Transparent)
    ) {
        MathCraftTopBar(
            title = if (!isSubmitted) "آزمون پایانی فصل اول" else "کارنامه نهایی آزمون",
            stars = currentStars,
            xp = currentXp,
            onBackClick = onBack
        )

        if (!isSubmitted) {
            // Live Quiz Stepper View
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .widthIn(max = 680.dp),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header & Progress Indicator
                item {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "سؤال ${PersianUtils.toPersian(currentQuestionIndex + 1)} از ۵",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = GoldAccent
                            )
                            Text(
                                text = "ارزش: ۲۰ نمره",
                                style = MaterialTheme.typography.labelMedium,
                                color = TextMuted
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        LinearProgressIndicator(
                            progress = { (currentQuestionIndex + 1) / 5f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp)),
                            color = GoldAccent,
                            trackColor = SurfaceVariantDark
                        )
                    }
                }

                // Question Card
                item {
                    GlassCard(
                        shape = RoundedCornerShape(18.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(18.dp)) {
                            Text(
                                text = currentQ.topic,
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = MintPrimary
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = currentQ.question,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary,
                                lineHeight = 26.sp
                            )

                            Spacer(modifier = Modifier.height(18.dp))

                            // Options List
                            currentQ.options.forEachIndexed { optIndex, optionText ->
                                val isSelected = selectedOptionForCurrent == optIndex
                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = if (isSelected) GoldAccent.copy(alpha = 0.2f) else SurfaceVariantDark,
                                    border = androidx.compose.foundation.BorderStroke(
                                        1.5.dp,
                                        if (isSelected) GoldAccent else BorderDark
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 5.dp)
                                        .clickable {
                                            userAnswers[currentQ.id] = optIndex
                                        }
                                        .testTag("quiz_option_${currentQ.id}_$optIndex")
                                ) {
                                    Row(
                                        modifier = Modifier.padding(14.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(28.dp)
                                                .clip(CircleShape)
                                                .background(if (isSelected) GoldAccent else SurfaceDark)
                                                .border(1.dp, if (isSelected) GoldAccent else BorderDark, CircleShape),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = PersianUtils.toPersian(optIndex + 1),
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = if (isSelected) BgDark else TextSecondary
                                            )
                                        }

                                        Spacer(modifier = Modifier.width(12.dp))

                                        androidx.compose.runtime.CompositionLocalProvider(
                                            androidx.compose.ui.platform.LocalLayoutDirection provides androidx.compose.ui.unit.LayoutDirection.Ltr
                                        ) {
                                            Text(
                                                text = optionText,
                                                style = MaterialTheme.typography.bodyMedium,
                                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                                color = if (isSelected) TextPrimary else TextSecondary
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // Next / Prev / Finish Navigation Buttons
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        if (currentQuestionIndex > 0) {
                            OutlinedButton(
                                onClick = { currentQuestionIndex-- },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text("سؤال قبلی", color = TextSecondary)
                            }
                        }

                        if (currentQuestionIndex < questions.size - 1) {
                            Button(
                                onClick = { currentQuestionIndex++ },
                                enabled = selectedOptionForCurrent != null,
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = GoldAccent),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text("سؤال بعدی", color = BgDark, fontWeight = FontWeight.Bold)
                            }
                        } else {
                            // Last Question: Submit Exam
                            Button(
                                onClick = {
                                    isSubmitted = true
                                    onCompleteQuiz(totalScore, correctCount)
                                },
                                enabled = userAnswers.size == questions.size,
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = MintPrimary),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text("ثبت نهایی و مشاهده کارنامه", color = BgDark, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        } else {
            // Report Card & Review View
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .widthIn(max = 680.dp),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Score Summary Card
                item {
                    GlassCard(
                        shape = RoundedCornerShape(20.dp),
                        accentTint = if (totalScore >= 60) SuccessSage else CoralRed,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(22.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.EmojiEvents,
                                contentDescription = null,
                                tint = if (totalScore >= 60) GoldAccent else TextMuted,
                                modifier = Modifier.size(54.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = if (totalScore >= 80) "فوق‌العاده بود! تسلط طلایی!" else if (totalScore >= 60) "آفرین! قبولی با موفقیت" else "نیاز به مرور درسنامه",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = if (totalScore >= 60) SuccessSage else CoralRed
                            )

                            Spacer(modifier = Modifier.height(14.dp))

                            // Big Score Display
                            Row(
                                verticalAlignment = Alignment.Bottom,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = PersianUtils.toPersian(totalScore),
                                    fontSize = 48.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                                Text(
                                    text = " / ۱۰۰",
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = TextSecondary,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "درصد پاسخ‌های درست: ${PersianUtils.toPersian(percentage)}٪",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                color = GoldAccent
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            // Correct vs Wrong Stats Row
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = SuccessBg,
                                    border = BorderStroke(1.dp, SuccessSage.copy(alpha = 0.4f)),
                                    modifier = Modifier.padding(4.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(Icons.Rounded.CheckCircle, contentDescription = null, tint = SuccessSage, modifier = Modifier.size(18.dp))
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = "پاسخ‌های درست: ${PersianUtils.toPersian(correctCount)}",
                                            style = MaterialTheme.typography.bodySmall,
                                            fontWeight = FontWeight.Bold,
                                            color = SuccessSage
                                        )
                                    }
                                }

                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = ErrorBg,
                                    border = BorderStroke(1.dp, CoralRed.copy(alpha = 0.4f)),
                                    modifier = Modifier.padding(4.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(Icons.Rounded.Close, contentDescription = null, tint = CoralRed, modifier = Modifier.size(18.dp))
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = "پاسخ‌های نادرست: ${PersianUtils.toPersian(wrongCount)}",
                                            style = MaterialTheme.typography.bodySmall,
                                            fontWeight = FontWeight.Bold,
                                            color = CoralRed
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // Action Buttons: Retake / Review mistakes
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = {
                                isReviewMode = !isReviewMode
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isReviewMode) PurpleAccent else SurfaceVariantDark
                            )
                        ) {
                            Text(
                                text = if (isReviewMode) "بستن مرور پاسخ‌ها" else "مرور پاسخ‌ها و توضیحات",
                                color = if (isReviewMode) BgDark else TextPrimary,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Button(
                            onClick = {
                                userAnswers.clear()
                                currentQuestionIndex = 0
                                isSubmitted = false
                                isReviewMode = false
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = MintSecondary)
                        ) {
                            Icon(Icons.Rounded.Refresh, contentDescription = null, tint = PureWhite, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("تلاش دوباره", color = PureWhite, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedButton(
                        onClick = onBack,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("بازگشت به نقشه مراحل فصل", color = TextPrimary, fontWeight = FontWeight.Bold)
                    }
                }

                // Detailed Review of all 5 Questions
                if (isReviewMode) {
                    item {
                        Text(
                            text = "مرور تشریحی تمام سؤالات آزمون:",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                    }

                    itemsIndexed(questions) { index, q ->
                        val userChoice = userAnswers[q.id]
                        val isUserCorrect = userChoice == q.correctIndex

                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = CardDark,
                            border = BorderStroke(
                                1.dp,
                                if (isUserCorrect) SuccessSage.copy(alpha = 0.5f) else CoralRed.copy(alpha = 0.5f)
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "سؤال ${PersianUtils.toPersian(index + 1)}: ${q.topic}",
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isUserCorrect) SuccessSage else CoralRed
                                    )
                                    Text(
                                        text = if (isUserCorrect) "۲۰ نمره کامل ✓" else "۰ نمره ✗",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isUserCorrect) SuccessSage else CoralRed
                                    )
                                }

                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = q.question,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                // Options Review
                                q.options.forEachIndexed { optIndex, optText ->
                                    val isAnswerSelectedByUser = userChoice == optIndex
                                    val isCorrectOption = optIndex == q.correctIndex

                                    val bgColor = when {
                                        isCorrectOption -> SuccessSage.copy(alpha = 0.15f)
                                        isAnswerSelectedByUser && !isCorrectOption -> CoralRed.copy(alpha = 0.15f)
                                        else -> SurfaceVariantDark
                                    }

                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = bgColor,
                                        border = BorderStroke(
                                            1.dp,
                                            if (isCorrectOption) SuccessSage else if (isAnswerSelectedByUser) CoralRed else BorderDark
                                        ),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 3.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(10.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = "${PersianUtils.toPersian(optIndex + 1)})",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = if (isCorrectOption) SuccessSage else if (isAnswerSelectedByUser) CoralRed else TextSecondary,
                                                fontWeight = FontWeight.Bold,
                                                modifier = Modifier.padding(end = 6.dp)
                                            )
                                            androidx.compose.runtime.CompositionLocalProvider(
                                                androidx.compose.ui.platform.LocalLayoutDirection provides androidx.compose.ui.unit.LayoutDirection.Ltr
                                            ) {
                                                Text(
                                                    text = optText,
                                                    style = MaterialTheme.typography.bodySmall,
                                                    color = if (isCorrectOption) SuccessSage else if (isAnswerSelectedByUser) CoralRed else TextSecondary,
                                                    fontWeight = if (isCorrectOption || isAnswerSelectedByUser) FontWeight.Bold else FontWeight.Normal
                                                )
                                            }
                                            Spacer(modifier = Modifier.weight(1f))
                                            if (isCorrectOption) {
                                                Text("(پاسخ صحیح)", style = MaterialTheme.typography.labelSmall, color = SuccessSage)
                                            } else if (isAnswerSelectedByUser) {
                                                Text("(انتخاب شما)", style = MaterialTheme.typography.labelSmall, color = CoralRed)
                                            }
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                // Educational Explanation
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = SurfaceDark,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(10.dp)) {
                                        Text(
                                            text = "توضیح آموزشی:",
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = GoldAccent
                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = q.explanation,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = TextSecondary,
                                            lineHeight = 20.sp
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
