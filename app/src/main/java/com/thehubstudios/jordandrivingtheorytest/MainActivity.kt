package com.thehubstudios.jordandrivingtheorytest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.google.android.gms.ads.MobileAds
import com.thehubstudios.jordandrivingtheorytest.ui.screens.MainScreen
import com.thehubstudios.jordandrivingtheorytest.ui.theme.JordanDrivingTheoryTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Google Mobile Ads
        MobileAds.initialize(this) { initializationStatus ->
            println("✅ AdMob initialized: ${initializationStatus.adapterStatusMap}")
        }

        setContent {
            JordanDrivingTheoryTestTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    JordanDrivingApp()
                }
            }
        }
    }
}