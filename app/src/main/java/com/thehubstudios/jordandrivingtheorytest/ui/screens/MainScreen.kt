package com.thehubstudios.jordandrivingtheorytest.ui.screens

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.thehubstudios.jordandrivingtheorytest.R
import com.thehubstudios.jordandrivingtheorytest.model.Exam
import com.thehubstudios.jordandrivingtheorytest.viewmodel.LanguageManager
import com.thehubstudios.jordandrivingtheorytest.viewmodel.PurchaseManager
import com.thehubstudios.jordandrivingtheorytest.viewmodel.NetworkMonitor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    languageManager: LanguageManager,
    purchaseManager: PurchaseManager,
    navController: NetworkMonitor
) {
    val freeExamIds = setOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val exams = remember {
        (1..15).map { Exam(it, "exam_$it", "questions$it") }
    }

    var showAdDialog by remember { mutableStateOf(false) }
    var pendingExamId by remember { mutableStateOf<Int?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        // Background image
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
                .padding(horizontal = 80.dp)
        ) {
            // Top bar with language picker and settings
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Language Picker
                Row(
                    modifier = Modifier.width(220.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { languageManager.setLanguage("en") },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (languageManager.language == "en")
                                Color(0xFFFF9800) else Color.Gray
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("English")
                    }

                    Button(
                        onClick = { languageManager.setLanguage("ar") },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (languageManager.language == "ar")
                                Color(0xFFFF9800) else Color.Gray
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("العربية")
                    }
                }

                IconButton(onClick = { navController.navigate("settings") }) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Title
            Text(
                text = stringResource(R.string.main_action_title),
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                textAlign = TextAlign.Center
            )

            // Exam Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 20.dp)
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
                                pendingExamId = exam.id
                                if (!purchaseManager.hasRemovedAds) {
                                    showAdDialog = true
                                } else {
                                    navController.navigate("test/${exam.id}")
                                }
                            }
                        }
                    )
                }
            }
        }

        // Ad Dialog
        if (showAdDialog) {
            AlertDialog(
                onDismissRequest = {
                    showAdDialog = false
                    pendingExamId?.let { navController.navigate("test/$it") }
                },
                title = { Text("Loading Ad...") },
                text = { Text("Please wait") },
                confirmButton = {
                    TextButton(onClick = {
                        showAdDialog = false
                        pendingExamId?.let { navController.navigate("test/$it") }
                    }) {
                        Text("Skip")
                    }
                }
            )
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
            .height(120.dp),
        enabled = !isLocked || true // Allow clicking locked to trigger purchase
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = if (isLocked)
                    "Exam ${exam.id}\nComing Soon"
                else
                    "Exam ${exam.id}",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                color = Color.White
            )

            if (isLocked) {
                Spacer(modifier = Modifier.height(8.dp))
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Locked",
                    tint = Color.White
                )
            }
        }
    }
}