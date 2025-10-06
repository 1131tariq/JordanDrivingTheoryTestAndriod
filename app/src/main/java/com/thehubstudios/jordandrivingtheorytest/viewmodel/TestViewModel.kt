package com.thehubstudios.jordandrivingtheorytest.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.thehubstudios.jordandrivingtheorytest.model.Question

/**
 * ViewModel for managing test/quiz state
 */
class TestViewModel(private val questions: List<Question>) {

    var currentIndex by mutableStateOf(0)
        private set

    var score by mutableStateOf(0)
        private set

    var selectedAnswer by mutableStateOf<Int?>(null)
        private set

    var showFeedback by mutableStateOf(false)
        private set

    /**
     * Get the current question being displayed
     */
    val currentQuestion: Question
        get() = questions[currentIndex]

    /**
     * Calculate progress as a fraction (0.0 to 1.0)
     */
    val progress: Float
        get() {
            val completed = if (showFeedback) currentIndex + 1 else currentIndex
            return completed.toFloat() / questions.size.toFloat()
        }

    /**
     * Handle answer selection
     * @param index The index of the selected answer
     */
    fun selectAnswer(index: Int) {
        selectedAnswer = index
        if (index == currentQuestion.correctIndex) {
            score++
        }
        showFeedback = true
    }

    /**
     * Move to the next question
     */
    fun nextQuestion() {
        selectedAnswer = null
        showFeedback = false
        if (currentIndex < questions.size - 1) {
            currentIndex++
        }
    }

    /**
     * Restart the test from the beginning
     */
    fun restart() {
        score = 0
        currentIndex = 0
        selectedAnswer = null
        showFeedback = false
    }

    /**
     * Check if this is the last question
     */
    fun isLastQuestion() = currentIndex == questions.size - 1
}