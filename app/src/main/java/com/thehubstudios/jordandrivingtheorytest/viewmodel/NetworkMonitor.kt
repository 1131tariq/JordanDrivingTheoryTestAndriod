package com.thehubstudios.jordandrivingtheorytest.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/**
 * Monitors network connectivity
 * TODO: Implement actual ConnectivityManager monitoring
 */
class NetworkMonitor {
    var isConnected by mutableStateOf(true)
        private set

    // TODO: Implement ConnectivityManager monitoring
    // For now, always returns connected
}
