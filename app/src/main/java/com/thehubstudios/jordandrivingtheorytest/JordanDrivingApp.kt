package com.thehubstudios.jordandrivingtheorytest

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.thehubstudios.jordandrivingtheorytest.ui.screens.*
import com.thehubstudios.jordandrivingtheorytest.viewmodel.*
import kotlinx.coroutines.delay

/**
 * Main app composable that handles navigation and state management
 */
@Composable
fun JordanDrivingApp() {
    val context = LocalContext.current
    val navController = rememberNavController()

    // Create managers - these won't crash now
    val languageManager = remember { LanguageManager() }
    val purchaseManager = remember { PurchaseManager() }
    val networkMonitor = remember { NetworkMonitor(context) }

    // Clean up network monitor when app is disposed
    DisposableEffect(Unit) {
        onDispose {
            networkMonitor.cleanup()
        }
    }

    // Track splash screen and onboarding state
    var showSplash by rememberSaveable { mutableStateOf(true) }
    var hasSeenOnboarding by rememberSaveable { mutableStateOf(false) }

    // Show splash screen for 2 seconds
    LaunchedEffect(Unit) {
        delay(2000)
        showSplash = false
    }

    when {
        // Show splash screen first
        showSplash -> SplashScreen()

        // Show no connection screen if offline (unless user has purchased)
        !networkMonitor.isConnected && !purchaseManager.hasRemovedAds -> {
            NoConnectionScreen()
        }

        // Show main app with navigation
        else -> {
            NavHost(
                navController = navController,
                startDestination = if (hasSeenOnboarding) "main" else "onboarding"
            ) {
                // Onboarding flow (first time users)
                composable("onboarding") {
                    OnboardingScreen(
                        languageManager = languageManager,
                        onFinish = {
                            hasSeenOnboarding = true
                            navController.navigate("main") {
                                popUpTo("onboarding") { inclusive = true }
                            }
                        }
                    )
                }

                // Main screen - exam selection grid
                composable("main") {
                    MainScreen(
                        languageManager = languageManager,
                        purchaseManager = purchaseManager,
                        navController = navController
                    )
                }

                // Test screen - takes exam ID as parameter
                composable("test/{examId}") { backStackEntry ->
                    val examId = backStackEntry.arguments?.getString("examId")?.toIntOrNull() ?: 1
                    TestScreen(
                        examId = examId,
                        languageManager = languageManager,
                        purchaseManager = purchaseManager,
                        onNavigateBack = {
                            navController.popBackStack()
                        }
                    )
                }

                // Settings screen
                composable("settings") {
                    SettingsScreen(
                        purchaseManager = purchaseManager,
                        onNavigateBack = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}
