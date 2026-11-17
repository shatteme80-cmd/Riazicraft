package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Quiz
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.FormulaBox
import com.example.ui.components.MathCraftTopBar
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.MintPrimary
import com.example.ui.theme.ObsidianInk
import com.example.ui.theme.PureWhite
import com.example.ui.theme.SurfaceDark
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.WarmTaupe

@Composable
fun Lesson3Screen(
    currentStars: Int,
    currentXp: Int,
    onBack: () -> Unit,
    onCompleteLesson: (stars: Int, xpEarned: Int) -> Unit,
    onNextLesson: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }

    var selectedTab by remember { mutableIntStateOf(0) } // 0: درسنامه جزوه, 1: تمرین ۴ گزینه‌ای

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Transparent)
    ) {
        MathCraftTopBar(
            title = "درس سوم: مجموعه‌ها و احتمال",
            stars = currentStars,
            xp = currentXp,
            onBackClick = onBack
        )

        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = SurfaceDark.copy(alpha = 0.78f),
            contentColor = TextPrimary,
            indicator = { tabPositions ->
                SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                    color = WarmTaupe
                )
            }
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                modifier = Modifier.testTag("lesson3_theory_tab"),
                text = {
                    Text(
                        text = "درسنامه و نکات جزوه",
                        fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Normal,
                        color = if (selectedTab == 0) PureWhite else TextSecondary
                    )
                }
            )

            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                modifier = Modifier.testTag("lesson3_lab_tab"),
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Rounded.Quiz,
                            contentDescription = null,
                            tint = if (selectedTab == 1) GoldAccent else TextSecondary,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "تمرین ۴ گزینه‌ای جزوه",
                            fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Normal,
                            color = if (selectedTab == 1) GoldAccent else TextSecondary
                        )
                    }
                }
            )
        }

        if (selectedTab == 0) {
            Lesson3TheoryView(onGoToGame = { selectedTab = 1 })
        } else {
            Lesson3BookletQuizView(
                onCompleteAll = { stars, xp ->
                    onCompleteLesson(stars, xp)
                },
                onNextLesson = onNextLesson
            )
        }
    }
}

@Composable
fun Lesson3TheoryView(
    onGoToGame: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .widthIn(max = 680.dp),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. فرمول احتمال و پیشامد غیرممکن
        item {
            ConceptCard(
                title = "۱. فرمول احتمال و نکات کلیدی",
                icon = "📐"
            ) {
                Text(
                    text = "برای به دست آوردن احتمال هر پیشامد، کافی است تعداد حالت‌های مطلوب را بر تعداد همهٔ حالت‌های ممکن تقسیم کنیم:",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextPrimary,
                    lineHeight = 22.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                FormulaBox(
                    formula = "P(A) = n(A) / n(S)     |     ۰ ≤ P(A) ≤ ۱",
                    label = "تعداد حالت‌های مطلوب ÷ تعداد همه حالت‌های ممکن"
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "• احتمال وقوع هر پیشامد عددی بین صفر تا ۱ است.\n• اگر احتمال یک پیشامد صفر باشد، «پیشامد غیرممکن» نام دارد (مثل آمدن عدد ۷ در پرتاب یک تاس).",
                    style = MaterialTheme.typography.bodySmall,
                    color = MintPrimary,
                    lineHeight = 21.sp
                )
            }
        }

        // 2. محاسبه تعداد کل حالت‌ها n(S)
        item {
            ConceptCard(
                title = "۲. روش پیدا کردن تعداد کل حالت‌ها n(S)",
                icon = "🎲"
            ) {
                Text(
                    text = "قبل از نوشتن کسر احتمال، باید بدانیم کل حالت‌های آزمایش چند تاست:",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextPrimary,
                    lineHeight = 22.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                FormulaBox(
                    formula = "n(S) = ۶   |   n(S) = ۶² = ۳۶   |   n(S) = ۶ × ۲ = ۱۲   |   n(S) = ۲³ = ۸",
                    label = "به ترتیب: یک تاس (۶)، دو تاس (۳۶)، تاس و سکه (۱۲)، خانواده ۳ فرزندی (۸)"
                )
            }
        }

        // 3. فرمول تعداد عضوهای دنباله عددی
        item {
            ConceptCard(
                title = "۳. فرمول تعداد عضوها (برای اعداد دو رقمی و مضرب‌ها)",
                icon = "🔢"
            ) {
                Text(
                    text = "وقتی می‌خواهیم تعداد اعداد بین دو عدد (مثلاً تمام اعداد دو رقمی از ۱۰ تا ۹۹) را بدون شمردن پیدا کنیم:\nتعداد عضوها = ((عدد آخر − عدد اول) ÷ فاصله اعداد) + ۱",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextPrimary,
                    lineHeight = 22.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                FormulaBox(
                    formula = "n(S) = (۹۹ − ۱۰) ÷ ۱ + ۱ = ۹۰\nn(A) = (۹۵ − ۱۰) ÷ ۵ + ۱ = ۱۸",
                    label = "تعداد کل اعداد دو رقمی (۹۰) و مضرب‌های ۵ دو رقمی (۱۸)"
                )
            }
        }

        // 4. نمونه حل‌شده گام‌به‌گام دو تاس
        item {
            ConceptCard(
                title = "۴. نمونه حل‌شده به سبک جزوه (پرتاب دو تاس)",
                icon = "✍️"
            ) {
                Text(
                    text = "مثال: در پرتاب دو تاس، احتمال اینکه مجموع دو عدد ۶ شود چقدر است؟\nکل حالت‌ها ۳۶ تاست (n(S) = ۶² = ۳۶) و حالت‌های مطلوب ۵ تا هستند:",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    lineHeight = 22.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                FormulaBox(
                    formula = "B = { (۱،۵)، (۵،۱)، (۲،۴)، (۴،۲)، (۳،۳) }  ⇒  n(B) = ۵\nP(B) = ۵ / ۳۶",
                    label = "نوشتن زوج‌مرتب‌های مطلوب و تقسیم بر ۳۶"
                )
            }
        }

        // Action button
        item {
            Spacer(modifier = Modifier.height(6.dp))
            Button(
                onClick = onGoToGame,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("go_to_lesson3_game_btn"),
                colors = ButtonDefaults.buttonColors(containerColor = PureWhite),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(Icons.Rounded.Quiz, contentDescription = null, tint = ObsidianInk)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "شروع ۸ سؤال ۴ گزینه‌ای جزوه با پاسخ تشریحی",
                        color = ObsidianInk,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
