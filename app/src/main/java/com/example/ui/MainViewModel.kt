package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.data.AppScreen
import com.example.data.PreferencesManager
import com.example.data.UserProgress
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val prefsManager = PreferencesManager(application.applicationContext)

    private val _screen = MutableStateFlow<AppScreen>(AppScreen.Home)
    val screen: StateFlow<AppScreen> = _screen.asStateFlow()

    private val _progress = MutableStateFlow(prefsManager.loadProgress())
    val progress: StateFlow<UserProgress> = _progress.asStateFlow()

    fun navigateTo(newScreen: AppScreen) {
        _screen.value = newScreen
    }

    fun navigateBack(): Boolean {
        return if (_screen.value !is AppScreen.Home) {
            _screen.value = AppScreen.Home
            true
        } else {
            false
        }
    }

    fun completeLesson1(stars: Int, xpEarned: Int) {
        _progress.update { current ->
            val updated = current.copy(
                isLesson1Completed = true,
                starsLesson1 = maxOf(current.starsLesson1, stars),
                xp = current.xp + xpEarned
            )
            prefsManager.saveProgress(updated)
            updated
        }
    }

    fun completeLesson2(stars: Int, xpEarned: Int) {
        _progress.update { current ->
            val updated = current.copy(
                isLesson2Completed = true,
                starsLesson2 = maxOf(current.starsLesson2, stars),
                xp = current.xp + xpEarned
            )
            prefsManager.saveProgress(updated)
            updated
        }
    }

    fun completeLesson3(stars: Int, xpEarned: Int) {
        _progress.update { current ->
            val updated = current.copy(
                isLesson3Completed = true,
                starsLesson3 = maxOf(current.starsLesson3, stars),
                xp = current.xp + xpEarned
            )
            prefsManager.saveProgress(updated)
            updated
        }
    }

    fun completeQuiz(score: Int, correctCount: Int) {
        val stars = when {
            score >= 100 -> 3
            score >= 60 -> 2
            score >= 40 -> 1
            else -> 0
        }
        val xpGain = score * 3

        _progress.update { current ->
            val updated = current.copy(
                isQuizCompleted = true,
                quizScore = maxOf(current.quizScore, score),
                quizAnsweredCount = maxOf(current.quizAnsweredCount, correctCount),
                starsQuiz = maxOf(current.starsQuiz, stars),
                xp = current.xp + xpGain
            )
            prefsManager.saveProgress(updated)
            updated
        }
    }

    fun addXp(amount: Int) {
        _progress.update { current ->
            val updated = current.copy(xp = current.xp + amount)
            prefsManager.saveProgress(updated)
            updated
        }
    }

    fun resetProgress() {
        val reset = prefsManager.resetProgress()
        _progress.value = reset
        _screen.value = AppScreen.Home
    }
}
