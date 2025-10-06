package com.thehubstudios.jordandrivingtheorytest.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.thehubstudios.jordandrivingtheorytest.R
import com.thehubstudios.jordandrivingtheorytest.viewmodel.LanguageManager
import com.thehubstudios.jordandrivingtheorytest.viewmodel.PurchaseManager

@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Add your logo here
            Text(
                text = "Jordan Driving\nTheory Test",
                style = MaterialTheme.typography.displayMedium,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "The Hub Studios®",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}