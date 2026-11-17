package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
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
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.FormulaBox
import com.example.ui.components.GlassCard
import com.example.ui.theme.BgDark
import com.example.ui.theme.ChampagneTaupe
import com.example.ui.theme.CoralRed
import com.example.ui.theme.DeepMocha
import com.example.ui.theme.ErrorBg
import com.example.ui.theme.GlassRimBottom
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GraphiteSlate
import com.example.ui.theme.ObsidianInk
import com.example.ui.theme.PureWhite
import com.example.ui.theme.SuccessBg
import com.example.ui.theme.SuccessSage
import com.example.ui.theme.SurfaceDark
import com.example.ui.theme.SurfaceVariantDark
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.WarmTaupe
import com.example.util.PersianUtils

data class BookletQuizQuestion(
    val id: Int,
    val sourceTag: String,
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val stepFormula: String,
    val bookletExplanation: String
)

@Composable
fun Lesson3BookletQuizView(
    onCompleteAll: (stars: Int, xp: Int) -> Unit,
    onNextLesson: () -> Unit = {}
) {
    val questions = remember {
        listOf(
            BookletQuizQuestion(
                id = 1,
                sourceTag = "مثال جزوه • پرتاب یک تاس",
                question = "در پرتاب یک تاس سالم، احتمال آمدن «عدد اول» چقدر است؟",
                options = listOf(
                    "۳ / ۶ = ۱ / ۲",
                    "۴ / ۶ = ۲ / ۳",
                    "۲ / ۶ = ۱ / ۳",
                    "۱ / ۶"
                ),
                correctIndex = 0,
                stepFormula = "S = { ۱، ۲، ۳، ۴، ۵، ۶ }  ⇒  n(S) = ۶\nA = { ۲، ۳، ۵ }  ⇒  n(A) = ۳\nP(A) = ۳ / ۶ = ۱ / ۲",
                bookletExplanation = "روی تاس ۶ عدد داریم؛ پس تعداد کل حالت‌ها n(S) = ۶ است. اعداد اول کمتر از ۷ فقط ۲، ۳ و ۵ هستند (توجه کنید عدد ۱ اول نیست!). پس ۳ حالت مطلوب داریم و احتمال برابر ۳/۶ یعنی ۱/۲ می‌شود."
            ),
            BookletQuizQuestion(
                id = 2,
                sourceTag = "نمونه سؤال ردیف ۱۱ • کیسه مهره‌ها",
                question = "در یک کیسه ۳ مهره قرمز، ۲ مهره آبی و ۲ مهره سفید است. یک مهره به تصادف بیرون می‌آوریم. احتمال اینکه مهره «آبی نباشد» چقدر است؟",
                options = listOf(
                    "۲ / ۷",
                    "۵ / ۷",
                    "۳ / ۷",
                    "۴ / ۷"
                ),
                correctIndex = 1,
                stepFormula = "n(S) = ۳ + ۲ + ۲ = ۷\nتعداد غیرآبی (قرمز و سفید): n(B) = ۳ + ۲ = ۵\nP(B) = ۵ / ۷",
                bookletExplanation = "ابتدا تعداد کل مهره‌ها را حساب می‌کنیم: ۳ + ۲ + ۲ = ۷. چون گفته مهره «آبی نباشد»، مهره‌های قرمز و سفید قابل قبول هستند (۳ + ۲ = ۵). پس احتمال برابر ۵/۷ است."
            ),
            BookletQuizQuestion(
                id = 3,
                sourceTag = "مثال جزوه • تعداد کل حالت‌ها n(S)",
                question = "تعداد کل حالت‌های ممکن در پرتاب هم‌زمان «یک تاس و یک سکه» چند است؟",
                options = listOf(
                    "۸",
                    "۶",
                    "۱۲",
                    "۳۶"
                ),
                correctIndex = 2,
                stepFormula = "حالت‌های تاس = ۶   ،   حالت‌های سکه = ۲\nn(S) = ۶ × ۲ = ۱۲",
                bookletExplanation = "در پرتاب هم‌زمان یک تاس (۶ حالت) و یک سکه (۲ حالت رو یا پشت)، تعداد کل حالت‌های فضای نمونه از ضرب آن‌ها به دست می‌آید: ۶ × ۲ = ۱۲."
            ),
            BookletQuizQuestion(
                id = 4,
                sourceTag = "نمونه سؤال ردیف ۱۲ (الف) • دو تاس",
                question = "دو تاس را هم‌زمان پرتاب می‌کنیم. احتمال اینکه «تاس اول عدد زوج» و «تاس دوم مضرب ۵» بیاید، چند است؟",
                options = listOf(
                    "۳ / ۳۶ = ۱ / ۱۲",
                    "۶ / ۳۶ = ۱ / ۶",
                    "۴ / ۳۶ = ۱ / ۹",
                    "۲ / ۳۶ = ۱ / ۱۸"
                ),
                correctIndex = 0,
                stepFormula = "n(S) = ۶² = ۳۶\nA = { (۲،۵)، (۴،۵)، (۶،۵) }  ⇒  n(A) = ۳\nP(A) = ۳ / ۳۶ = ۱ / ۱۲",
                bookletExplanation = "تعداد کل حالت‌های دو تاس برابر ۶ × ۶ = ۳۶ است. برای تاس اول عددهای زوج (۲، ۴، ۶) و برای تاس دوم فقط مضرب ۵ (یعنی ۵) مجاز است. پس فقط ۳ زوج مرتب داریم و احتمال ۳/۳۶ یا ۱/۱۲ می‌شود."
            ),
            BookletQuizQuestion(
                id = 5,
                sourceTag = "نمونه سؤال ردیف ۱۲ (ج) • دو تاس",
                question = "دو تاس را هم‌زمان پرتاب می‌کنیم. احتمال اینکه «دو عدد مثل هم نباشند»، چند است؟",
                options = listOf(
                    "۶ / ۳۶ = ۱ / ۶",
                    "۵ / ۳۶",
                    "۳۰ / ۳۶ = ۵ / ۶",
                    "۳۵ / ۳۶"
                ),
                correctIndex = 2,
                stepFormula = "حالت‌های مثل هم: C = { (۱،۱)، (۲،۲)، (۳،۳)، (۴،۴)، (۵،۵)، (۶،۶) }\nn(C) = ۶  ⇒  احتمال مثل هم نبودن = ۱ − ۶/۳۶ = ۳۰/۳۶ = ۵/۶",
                bookletExplanation = "از ۳۶ حالت کل، ۶ حالت جفت (مثل هم) داریم: (۱،۱) تا (۶،۶). بنابراین ۳۰ حالت باقی‌مانده مثل هم نیستند و احتمال برابر ۳۰/۳۶ یا ۵/۶ است."
            ),
            BookletQuizQuestion(
                id = 6,
                sourceTag = "مثال جزوه • اعداد دو رقمی",
                question = "از بین «اعداد دو رقمی»، عددی را تصادفاً انتخاب می‌کنیم. احتمال اینکه عدد انتخاب‌شده «مضرب ۵» باشد، چند است؟",
                options = listOf(
                    "۱۹ / ۹۰",
                    "۱۸ / ۹۰ = ۱ / ۵",
                    "۱۸ / ۸۹",
                    "۱ / ۹"
                ),
                correctIndex = 1,
                stepFormula = "n(S) = (۹۹ − ۱۰) ÷ ۱ + ۱ = ۹۰\nA = { ۱۰، ۱۵، ۲۰، ...، ۹۵ }  ⇒  n(A) = (۹۵ − ۱۰) ÷ ۵ + ۱ = ۱۸\nP(A) = ۱۸ / ۹۰ = ۱ / ۵",
                bookletExplanation = "با فرمول تعداد عضوهای جزوه «(عدد آخر − عدد اول) ÷ فاصله + ۱»، تعداد کل اعداد دو رقمی از ۱۰ تا ۹۹ برابر ۹۰ و تعداد مضرب‌های ۵ از ۱۰ تا ۹۵ برابر ۱۸ است؛ پس احتمال ۱۸/۹۰ = ۱/۵ می‌شود."
            ),
            BookletQuizQuestion(
                id = 7,
                sourceTag = "نمونه سؤال ردیف ۱۲ (ب) • دو تاس",
                question = "دو تاس را هم‌زمان پرتاب می‌کنیم. احتمال اینکه «مجموع هر دو تاس ۵ شود»، چقدر است؟",
                options = listOf(
                    "۵ / ۳۶",
                    "۴ / ۳۶ = ۱ / ۹",
                    "۳ / ۳۶ = ۱ / ۱۲",
                    "۶ / ۳۶ = ۱ / ۶"
                ),
                correctIndex = 1,
                stepFormula = "n(S) = ۶² = ۳۶\nB = { (۱،۴)، (۴،۱)، (۲،۳)، (۳،۲) }  ⇒  n(B) = ۴\nP(B) = ۴ / ۳۶ = ۱ / ۹",
                bookletExplanation = "از میان ۳۶ حالت پرتاب دو تاس، زوج‌مرتب‌هایی که مجموعشان ۵ می‌شود عبارتند از (۱،۴)، (۴،۱)، (۲،۳) و (۳،۲). بنابراین ۴ حالت مطلوب داریم و احتمال ۴/۳۶ = ۱/۹ است."
            ),
            BookletQuizQuestion(
                id = 8,
                sourceTag = "نمونه سؤال ردیف ۱۱ (ج) • کیسه مهره‌ها",
                question = "در یک کیسه ۳ مهره قرمز، ۲ مهره آبی و ۲ مهره سفید است. یک مهره به تصادف بیرون می‌آوریم. احتمال اینکه مهره «آبی یا سفید» باشد، چقدر است؟",
                options = listOf(
                    "۲ / ۷",
                    "۳ / ۷",
                    "۴ / ۷",
                    "۵ / ۷"
                ),
                correctIndex = 2,
                stepFormula = "n(S) = ۳ + ۲ + ۲ = ۷\nمهره‌های آبی یا سفید: n(C) = ۲ + ۲ = ۴\nP(C) = ۴ / ۷",
                bookletExplanation = "تعداد کل مهره‌ها ۷ عدد است. چون مهره می‌تواند آبی (۲ عدد) یا سفید (۲ عدد) باشد، تعداد حالت‌های مطلوب برابر ۲ + ۲ = ۴ است؛ پس احتمال برابر ۴/۷ می‌شود."
            )
        )
    }

    var currentIndex by remember { mutableIntStateOf(0) }
    var selectedOption by remember { mutableStateOf<Int?>(null) }
    var isChecked by remember { mutableStateOf(false) }
    var correctCount by remember { mutableIntStateOf(0) }
    var isCompleted by remember { mutableStateOf(false) }
    var earnedStars by remember { mutableIntStateOf(3) }

    val currentQ = questions[currentIndex]

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .widthIn(max = 680.dp),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (!isCompleted) {
            // Header Progress Strip
            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "سؤال ${PersianUtils.toPersian(currentIndex + 1)} از ${PersianUtils.toPersian(questions.size)}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = ChampagneTaupe
                        )
                        Text(
                            text = "پاسخ درست: ${PersianUtils.toPersian(correctCount)} ⭐",
                            style = MaterialTheme.typography.labelLarge,
                            color = GoldAccent
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        for (i in questions.indices) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(6.dp)
                                    .clip(RoundedCornerShape(3.dp))
                                    .background(
                                        when {
                                            i < currentIndex -> SuccessSage
                                            i == currentIndex -> WarmTaupe
                                            else -> GraphiteSlate.copy(alpha = 0.65f)
                                        }
                                    )
                            )
                        }
                    }
                }
            }

            // Question Card
            item {
                GlassCard(
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        // Source Pill (Graphite & Warm Taupe frosted pill)
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = GraphiteSlate.copy(alpha = 0.65f),
                            border = BorderStroke(0.75.dp, WarmTaupe.copy(alpha = 0.65f))
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Rounded.MenuBook,
                                    contentDescription = null,
                                    tint = PureWhite,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = currentQ.sourceTag,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = PureWhite,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = currentQ.question,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary,
                            lineHeight = 25.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // 4 Options
                        currentQ.options.forEachIndexed { idx, optText ->
                            val isSelected = selectedOption == idx
                            val isCorrectOpt = idx == currentQ.correctIndex

                            val borderColor = when {
                                !isChecked && isSelected -> PureWhite
                                !isChecked -> GlassRimBottom
                                isCorrectOpt -> SuccessSage
                                isSelected && !isCorrectOpt -> CoralRed
                                else -> GlassRimBottom
                            }
                            val bgColor = when {
                                !isChecked && isSelected -> WarmTaupe.copy(alpha = 0.28f)
                                !isChecked -> GraphiteSlate.copy(alpha = 0.28f)
                                isCorrectOpt -> SuccessBg
                                isSelected && !isCorrectOpt -> ErrorBg
                                else -> SurfaceVariantDark.copy(alpha = 0.6f)
                            }
                            val labelColor = when {
                                !isChecked && isSelected -> PureWhite
                                !isChecked -> TextPrimary
                                isCorrectOpt -> SuccessSage
                                isSelected && !isCorrectOpt -> CoralRed
                                else -> TextSecondary
                            }

                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = bgColor,
                                border = BorderStroke(1.2.dp, borderColor),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .clickable(enabled = !isChecked) { selectedOption = idx }
                                    .testTag("lesson3_q${currentQ.id}_opt_$idx")
                            ) {
                                Row(
                                    modifier = Modifier
                                        .minimumInteractiveComponentSize()
                                        .padding(horizontal = 14.dp, vertical = 12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(28.dp)
                                            .clip(CircleShape)
                                            .background(
                                                if (isChecked && isCorrectOpt) SuccessSage
                                                else if (isChecked && isSelected) CoralRed
                                                else if (isSelected) PureWhite
                                                else SurfaceDark
                                            )
                                            .border(1.dp, borderColor, CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = PersianUtils.toPersian(idx + 1),
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (isSelected || (isChecked && isCorrectOpt)) BgDark else TextSecondary
                                        )
                                    }

                                    Spacer(modifier = Modifier.width(12.dp))

                                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                                        Text(
                                            text = optText,
                                            style = MaterialTheme.typography.bodyLarge,
                                            fontWeight = if (isSelected || (isChecked && isCorrectOpt)) FontWeight.Bold else FontWeight.Medium,
                                            color = labelColor,
                                            modifier = Modifier.weight(1f)
                                        )
                                    }

                                    if (isChecked && isCorrectOpt) {
                                        Icon(
                                            imageVector = Icons.Rounded.CheckCircle,
                                            contentDescription = "پاسخ صحیح",
                                            tint = SuccessSage,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    } else if (isChecked && isSelected && !isCorrectOpt) {
                                        Icon(
                                            imageVector = Icons.Rounded.Close,
                                            contentDescription = "پاسخ نادرست",
                                            tint = CoralRed,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        if (!isChecked) {
                            Button(
                                onClick = {
                                    if (selectedOption != null) {
                                        isChecked = true
                                        if (selectedOption == currentQ.correctIndex) {
                                            correctCount++
                                        }
                                    }
                                },
                                enabled = selectedOption != null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("lesson3_check_btn"),
                                colors = ButtonDefaults.buttonColors(containerColor = PureWhite),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("بررسی پاسخ", color = ObsidianInk, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            // Booklet Step-by-Step Solution Card (shown after checking)
            item {
                AnimatedVisibility(visible = isChecked, enter = fadeIn()) {
                    val isCorrect = selectedOption == currentQ.correctIndex
                    val statusColor = if (isCorrect) SuccessSage else CoralRed
                    Surface(
                        shape = RoundedCornerShape(18.dp),
                        color = if (isCorrect) SuccessBg else ErrorBg,
                        border = BorderStroke(1.2.dp, statusColor.copy(alpha = 0.7f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = if (isCorrect) Icons.Rounded.CheckCircle else Icons.Rounded.Close,
                                    contentDescription = null,
                                    tint = statusColor,
                                    modifier = Modifier.size(22.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = if (isCorrect) "آفرین! پاسخ کاملاً درست است" else "پاسخ اشتباه بود؛ راه‌حل جزوه را ببین:",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = statusColor
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            FormulaBox(
                                formula = currentQ.stepFormula,
                                label = "پاسخ تشریحی مطابق جزوه"
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = currentQ.bookletExplanation,
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextPrimary,
                                lineHeight = 22.sp
                            )

                            Spacer(modifier = Modifier.height(14.dp))

                            Button(
                                onClick = {
                                    if (currentIndex < questions.size - 1) {
                                        currentIndex++
                                        selectedOption = null
                                        isChecked = false
                                    } else {
                                        val finalStars = when {
                                            correctCount >= 6 -> 3
                                            correctCount >= 4 -> 2
                                            else -> 1
                                        }
                                        earnedStars = finalStars
                                        isCompleted = true
                                        onCompleteAll(finalStars, 90)
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("lesson3_next_q_btn"),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isCorrect) DeepMocha else GraphiteSlate
                                ),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    text = if (currentIndex < questions.size - 1) "سؤال بعدی" else "ثبت امتیاز و پایان درس سوم",
                                    color = PureWhite,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        } else {
            // Completion GlassCard
            item {
                GlassCard(
                    shape = RoundedCornerShape(22.dp),
                    accentTint = WarmTaupe,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Verified,
                            contentDescription = null,
                            tint = PureWhite,
                            modifier = Modifier.size(44.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "تمرین‌های درس سوم تکمیل شد",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = PureWhite,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "پاسخ صحیح: ${PersianUtils.toPersian(correctCount)} از ${PersianUtils.toPersian(questions.size)} سؤال جزوه\nاکنون برای ورود به «آزمون پایانی جامع فصل اول» آماده هستید!",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondary,
                            textAlign = TextAlign.Center,
                            lineHeight = 22.sp
                        )
                        Spacer(modifier = Modifier.height(14.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            repeat(earnedStars) {
                                Icon(
                                    imageVector = Icons.Rounded.Star,
                                    contentDescription = null,
                                    tint = GoldAccent,
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "+۹۰ امتیاز تجربه (XP) کسب شد!",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = GoldAccent
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Button(
                            onClick = onNextLesson,
                            colors = ButtonDefaults.buttonColors(containerColor = GoldAccent),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("lesson3_to_quiz_btn")
                        ) {
                            Text("ورود به آزمون پایانی جامع فصل اول", color = BgDark, fontWeight = FontWeight.Bold)
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Button(
                            onClick = {
                                isCompleted = false
                                currentIndex = 0
                                selectedOption = null
                                isChecked = false
                                correctCount = 0
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = SurfaceVariantDark),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("lesson3_retry_btn")
                        ) {
                            Text("تکرار سؤال‌های جزوه", color = TextPrimary)
                        }
                    }
                }
            }
        }
    }
}
