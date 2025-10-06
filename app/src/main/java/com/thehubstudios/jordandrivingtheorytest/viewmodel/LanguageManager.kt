package com.thehubstudios.jordandrivingtheorytest.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/**
 * Manages the app's language state
 * Supports English (en) and Arabic (ar)
 */
class LanguageManager {
    var language by mutableStateOf("en")
        private set

    fun setLanguage(lang: String) {
        language = lang
    }
}