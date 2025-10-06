package com.thehubstudios.jordandrivingtheorytest.model

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class Question(
    val text_en: String,
    val text_ar: String,
    val options_en: List<String>,
    val options_ar: List<String>,
    val correctIndex: Int,
    val imgName: String? = null,
    val imageURL: String? = null
) {
    // Generate unique ID for each question
    @kotlinx.serialization.Transient
    val id: String = UUID.randomUUID().toString()

    /**
     * Get question text based on selected language
     * @param language "en" for English, "ar" for Arabic
     * @return The question text in the specified language
     */
    fun getText(language: String): String {
        return if (language == "ar") text_ar else text_en
    }

    /**
     * Get answer options based on selected language
     * @param language "en" for English, "ar" for Arabic
     * @return List of answer options in the specified language
     */
    fun getOptions(language: String): List<String> {
        return if (language == "ar") options_ar else options_en
    }
}