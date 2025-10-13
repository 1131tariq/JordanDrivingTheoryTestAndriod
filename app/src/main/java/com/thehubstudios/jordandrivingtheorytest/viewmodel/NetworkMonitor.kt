////package com.thehubstudios.jordandrivingtheorytest.viewmodel
////
////import android.content.Context
////import android.net.ConnectivityManager
////import android.net.Network
////import android.net.NetworkCapabilities
////import android.net.NetworkRequest
////import androidx.compose.runtime.getValue
////import androidx.compose.runtime.mutableStateOf
////import androidx.compose.runtime.setValue
////
////class NetworkMonitor(context: Context) {
////    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
////
////    var isConnected by mutableStateOf(checkConnection())
////        private set
////
////    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
////        override fun onAvailable(network: Network) {
////            isConnected = true
////        }
////
////        override fun onLost(network: Network) {
////            isConnected = checkConnection()
////        }
////    }
////
////    init {
////        val request = NetworkRequest.Builder()
////            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
////            .build()
////        connectivityManager.registerNetworkCallback(request, networkCallback)
////    }
////
////    private fun checkConnection(): Boolean {
////        val network = connectivityManager.activeNetwork ?: return false
////        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
////        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
////    }
////}
//package com.thehubstudios.jordandrivingtheorytest.viewmodel
//
//import android.content.Context
//
///**
// * Temporary stub for network monitoring.
// * Always reports as connected.
// */
//class NetworkMonitor(private val context: Context) {
//    val isConnected: Boolean = true
//}
package com.thehubstudios.jordandrivingtheorytest.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Handler
import android.os.Looper
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/**
 * Monitors network connectivity in real-time
 */
class NetworkMonitor(context: Context) {
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val mainHandler = Handler(Looper.getMainLooper())

    var isConnected by mutableStateOf(checkConnection())
        private set

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            postUpdate(true)
            println("✅ Network connected")
        }

        override fun onLost(network: Network) {
            postUpdate(checkConnection())
            println("⚠️ Network disconnected")
        }
    }

    init {
        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        connectivityManager.registerNetworkCallback(request, networkCallback)
    }

    private fun checkConnection(): Boolean {
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    private fun postUpdate(value: Boolean) {
        mainHandler.post {
            isConnected = value
        }
    }

    fun cleanup() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}
