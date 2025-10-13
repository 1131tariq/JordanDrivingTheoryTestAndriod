package com.thehubstudios.jordandrivingtheorytest.ui.screens

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.thehubstudios.jordandrivingtheorytest.R
import com.thehubstudios.jordandrivingtheorytest.model.Exam
import com.thehubstudios.jordandrivingtheorytest.viewmodel.LanguageManager
import com.thehubstudios.jordandrivingtheorytest.viewmodel.PurchaseManager
import com.thehubstudios.jordandrivingtheorytest.ui.ads.BannerAdView
import com.thehubstudios.jordandrivingtheorytest.ui.ads.InterstitialAdManager

@Composable
fun MainScreen(
    languageManager: LanguageManager,
    purchaseManager: PurchaseManager,
    navController: NavController
) {
    val activity = LocalContext.current as Activity
    val interstitialAdManager = remember {
        InterstitialAdManager(
            activity,
            "ca-app-pub-5866389650741773/1194057628" // replace with your interstitial ad unit ID
        )
    }

    val freeExamIds = (1..15).toSet()
    val exams = remember { (1..15).map { Exam(it, "exam_$it", "questions$it") } }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.backdrop2),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.7f
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            // Language and settings row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = { languageManager.updateLanguage("en") },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (languageManager.language == "en") Color(0xFFFF9800) else Color.Gray
                        )
                    ) { Text("English") }

                    Button(
                        onClick = { languageManager.updateLanguage("ar") },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (languageManager.language == "ar") Color(0xFFFF9800) else Color.Gray
                        )
                    ) { Text("العربية") }
                }

                IconButton(onClick = { navController.navigate("settings") }) {
                    Icon(Icons.Default.Settings, contentDescription = "Settings", tint = Color.White)
                }
            }

            Text(
                text = stringResource(R.string.main_action_title),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                textAlign = TextAlign.Center
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(exams) { exam ->
                    val isLocked = !freeExamIds.contains(exam.id) && !purchaseManager.hasRemovedAds

                    ExamCard(
                        exam = exam,
                        isLocked = isLocked,
                        onClick = {
                            if (isLocked) {
                                purchaseManager.purchaseRemoveAds()
                            } else {
                                if (!purchaseManager.hasRemovedAds) {
                                    // ✅ Show interstitial ad, then go to exam
                                    interstitialAdManager.showAd {
                                        navController.navigate("test/${exam.id}")
                                    }
                                } else {
                                    navController.navigate("test/${exam.id}")
                                }
                            }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ✅ Banner Ad at bottom
            if (!purchaseManager.hasRemovedAds) {
                BannerAdView(adUnitId = "ca-app-pub-5866389650741773/9319959659")
            }
        }
    }
}

@Composable
fun ExamCard(
    exam: Exam,
    isLocked: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isLocked) Color.Gray else Color(0xFFFF9800)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Text(
                text = if (isLocked) "Exam ${exam.id}\nComing Soon" else "Exam ${exam.id}",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                color = Color.White
            )
            if (isLocked) {
                Spacer(modifier = Modifier.height(8.dp))
                Icon(Icons.Default.Lock, contentDescription = "Locked", tint = Color.White)
            }
        }
    }
}
