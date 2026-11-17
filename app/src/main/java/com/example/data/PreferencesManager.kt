package com.example.data

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("mathcraft_progress_prefs", Context.MODE_PRIVATE)

    fun loadProgress(): UserProgress {
        return UserProgress(
            starsLesson1 = prefs.getInt("stars_l1", 0),
            starsLesson2 = prefs.getInt("stars_l2", 0),
            starsLesson3 = prefs.getInt("stars_l3", 0),
            starsQuiz = prefs.getInt("stars_quiz", 0),
            xp = prefs.getInt("xp", 0),
            isLesson1Completed = prefs.getBoolean("l1_done", false),
            isLesson2Completed = prefs.getBoolean("l2_done", false),
            isLesson3Completed = prefs.getBoolean("l3_done", false),
            isQuizCompleted = prefs.getBoolean("quiz_done", false),
            quizScore = prefs.getInt("quiz_score", 0),
            quizAnsweredCount = prefs.getInt("quiz_answered_count", 0)
        )
    }

    fun saveProgress(progress: UserProgress) {
        prefs.edit()
            .putInt("stars_l1", progress.starsLesson1)
            .putInt("stars_l2", progress.starsLesson2)
            .putInt("stars_l3", progress.starsLesson3)
            .putInt("stars_quiz", progress.starsQuiz)
            .putInt("xp", progress.xp)
            .putBoolean("l1_done", progress.isLesson1Completed)
            .putBoolean("l2_done", progress.isLesson2Completed)
            .putBoolean("l3_done", progress.isLesson3Completed)
            .putBoolean("quiz_done", progress.isQuizCompleted)
            .putInt("quiz_score", progress.quizScore)
            .putInt("quiz_answered_count", progress.quizAnsweredCount)
            .apply()
    }

    fun resetProgress(): UserProgress {
        prefs.edit().clear().apply()
        return UserProgress()
    }
}
