package com.thehubstudios.jordandrivingtheorytest.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.thehubstudios.jordandrivingtheorytest.R
import com.thehubstudios.jordandrivingtheorytest.viewmodel.*

@Composable
fun TestScreen(
    examId: Int,
    languageManager: LanguageManager,
    purchaseManager: PurchaseManager,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    val questionLoader = remember { QuestionLoader(context) }
    val questions = remember { questionLoader.loadQuestions("questions$examId") }
    val viewModel = remember { TestViewModel(questions) }

    var isFinished by remember { mutableStateOf(false) }
    var showBackDialog by remember { mutableStateOf(false) }
    var selectedImageUrl by remember { mutableStateOf<String?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.backdrop2),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.3f
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 85.dp, vertical = 35.dp)
        ) {
            // Back button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                TextButton(onClick = { showBackDialog = true }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(stringResource(R.string.main_menu))
                }
            }

            if (isFinished) {
                ResultView(
                    score = viewModel.score,
                    total = questions.size,
                    onRestart = {
                        viewModel.restart()
                        isFinished = false
                    },
                    onExit = { showBackDialog = true }
                )
            } else {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                ) {
                    // Progress
                    Text(
                        text = "${viewModel.currentIndex + 1} / ${questions.size}",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )

                    LinearProgressIndicator(
                        progress = viewModel.progress,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        color = Color(0xFFFFEB3B)
                    )

                    // Image
                    viewModel.currentQuestion.imgName?.let { imgName ->
                        val resId = context.resources.getIdentifier(
                            imgName, "drawable", context.packageName
                        )
                        if (resId != 0) {
                            Image(
                                painter = painterResource(id = resId),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(200.dp)
                                    .align(Alignment.CenterHorizontally)
                                    .clickable { selectedImageUrl = imgName }
                            )
                        }
                    }

                    viewModel.currentQuestion.imageURL?.let { url ->
                        AsyncImage(
                            model = url,
                            contentDescription = null,
                            modifier = Modifier
                                .size(200.dp)
                                .align(Alignment.CenterHorizontally)
                                .clickable { selectedImageUrl = url }
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Question text
                    Text(
                        text = viewModel.currentQuestion.getText(languageManager.language),
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Answer buttons
                    viewModel.currentQuestion.getOptions(languageManager.language)
                        .forEachIndexed { index, option ->
                            AnswerButton(
                                text = option,
                                isSelected = viewModel.selectedAnswer == index,
                                isCorrect = index == viewModel.currentQuestion.correctIndex,
                                showFeedback = viewModel.showFeedback,
                                onClick = {
                                    if (viewModel.selectedAnswer == null) {
                                        viewModel.selectAnswer(index)
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                }

                // Feedback and Score
                Column(
                    modifier = Modifier.padding(vertical = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (viewModel.showFeedback) {
                        val isCorrect = viewModel.selectedAnswer == viewModel.currentQuestion.correctIndex
                        Text(
                            text = if (isCorrect) "✅ ${stringResource(R.string.correct)}"
                            else "❌ ${stringResource(R.string.wrong)}",
                            style = MaterialTheme.typography.titleLarge,
                            color = if (isCorrect) Color.Green else Color.Red
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = {
                                if (viewModel.isLastQuestion()) {
                                    isFinished = true
                                } else {
                                    viewModel.nextQuestion()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF2196F3)
                            )
                        ) {
                            Text(stringResource(R.string.next))
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "${stringResource(R.string.score)}: ${viewModel.score}",
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            }
        }

        // Image zoom dialog
        selectedImageUrl?.let { url ->
            Dialog(onDismissRequest = { selectedImageUrl = null }) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable { selectedImageUrl = null },
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = url,
                        contentDescription = null,
                        modifier = Modifier.size(400.dp)
                    )
                }
            }
        }

        // Back confirmation dialog
        if (showBackDialog) {
            AlertDialog(
                onDismissRequest = { showBackDialog = false },
                title = { Text("Exit Test?") },
                text = { Text("Your progress will be lost.") },
                confirmButton = {
                    TextButton(onClick = onNavigateBack) {
                        Text("Exit")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showBackDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}

@Composable
fun AnswerButton(
    text: String,
    isSelected: Boolean,
    isCorrect: Boolean,
    showFeedback: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = when {
        !showFeedback -> Color(0xFFFF9800)
        isCorrect -> Color(0xFF4CAF50)
        isSelected -> Color(0xFFF44336)
        else -> Color.Gray
    }

    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor)
    ) {
        Text(
            text = text,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}