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
fun ResultView(
    score: Int,
    total: Int,
    onRestart: () -> Unit,
    onExit: () -> Unit
) {
    val percentage = (score.toFloat() / total.toFloat() * 100).toInt()
    val passed = percentage >= 80

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.test_complete),
            style = MaterialTheme.typography.displaySmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.your_score_was),
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "$score / $total",
            style = MaterialTheme.typography.displayMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "${stringResource(R.string.you_scored_percent)} $percentage%",
            color = if (passed) Color.Green else Color.Red,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(
                onClick = onRestart,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2196F3)
                )
            ) {
                Text(stringResource(R.string.try_again))
            }

            Button(
                onClick = onExit,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF9800)
                )
            ) {
                Text(stringResource(R.string.main_menu))
            }
        }
    }
}