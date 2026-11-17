package com.example.data

sealed class AppScreen {
    data object Home : AppScreen()
    data object Lesson1 : AppScreen()
    data class Lesson2(val initialTab: Int = 0) : AppScreen()
    data object Lesson3 : AppScreen()
    data object Quiz : AppScreen()
}

data class UserProgress(
    val starsLesson1: Int = 0,
    val starsLesson2: Int = 0,
    val starsLesson3: Int = 0,
    val starsQuiz: Int = 0,
    val xp: Int = 0,
    val isLesson1Completed: Boolean = false,
    val isLesson2Completed: Boolean = false,
    val isLesson3Completed: Boolean = false,
    val isQuizCompleted: Boolean = false,
    val quizScore: Int = 0,
    val quizAnsweredCount: Int = 0
) {
    val totalStars: Int
        get() = starsLesson1 + starsLesson2 + starsLesson3 + starsQuiz

    val progressPercent: Float
        get() {
            var count = 0
            if (isLesson1Completed) count++
            if (isLesson2Completed) count++
            if (isLesson3Completed) count++
            if (isQuizCompleted) count++
            return count / 4f
        }

    val isQuizUnlocked: Boolean
        get() = isLesson1Completed && isLesson2Completed && isLesson3Completed
}

data class QuizQuestion(
    val id: Int,
    val topic: String,
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String
)
