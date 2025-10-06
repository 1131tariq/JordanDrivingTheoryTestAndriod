package com.thehubstudios.jordandrivingtheorytest.viewmodel

import android.content.Context
import com.thehubstudios.jordandrivingtheorytest.model.Question
import kotlinx.serialization.json.Json

/**
 * Loads questions from JSON files in the assets folder
 */
class QuestionLoader(private val context: Context) {
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    /**
     * Loads questions from a JSON file in the assets folder
     * @param filename Name of the JSON file (without .json extension)
     * @return List of questions, or empty list if file not found
     */
    fun loadQuestions(filename: String): List<Question> {
        return try {
            val jsonString = context.assets.open("$filename.json")
                .bufferedReader()
                .use { it.readText() }
            json.decodeFromString<List<Question>>(jsonString)
        } catch (e: Exception) {
            e.printStackTrace()
            println("❌ Error loading questions from $filename.json: ${e.message}")
            emptyList()
        }
    }
}