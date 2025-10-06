package com.thehubstudios.jordandrivingtheorytest.model

/**
 * Represents a driving theory exam
 * @param id Unique identifier for the exam (1-15)
 * @param titleKey Localization key for the exam title (e.g., "exam_1")
 * @param filename Name of the JSON file containing questions (e.g., "questions1")
 */
data class Exam(
    val id: Int,
    val titleKey: String,
    val filename: String
)