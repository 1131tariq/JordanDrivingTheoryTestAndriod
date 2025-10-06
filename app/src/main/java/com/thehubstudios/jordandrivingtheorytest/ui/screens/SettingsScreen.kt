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
fun SettingsScreen(
    purchaseManager: PurchaseManager,
    onNavigateBack: () -> Unit
) {
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
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextButton(onClick = onNavigateBack) {
                Text(stringResource(R.string.main_menu))
            }

            Spacer(modifier = Modifier.weight(1f))

            if (!purchaseManager.hasRemovedAds) {
                Button(
                    onClick = { purchaseManager.purchaseRemoveAds() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF9800)
                    )
                ) {
                    Text(stringResource(R.string.remove_ads))
                }

                Spacer(modifier = Modifier.height(16.dp))
            } else {
                Text(
                    text = stringResource(R.string.ads_removed),
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            Button(
                onClick = { purchaseManager.restorePurchases() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFEB3B)
                )
            ) {
                Text(stringResource(R.string.restore_purchases))
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}