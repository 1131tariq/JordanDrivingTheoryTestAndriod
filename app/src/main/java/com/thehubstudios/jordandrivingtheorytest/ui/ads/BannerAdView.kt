package com.thehubstudios.jordandrivingtheorytest.ui.ads

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

/**
 * Composable that displays a banner ad
 * Usage: BannerAdView(adUnitId = "ca-app-pub-xxxxx/yyyyy")
 */
@Composable
fun BannerAdView(adUnitId: String) {
    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        factory = { context ->
            AdView(context).apply {
                setAdSize(AdSize.BANNER)
                this.adUnitId = adUnitId
                loadAd(AdRequest.Builder().build())
            }
        }
    )
}

/**
 * Test Ad Unit IDs - Use these during development
 * Replace with your real IDs in production!
 */
object AdUnitIds {
    // Google's test ad unit IDs (safe to use for testing)
    const val BANNER_TEST = "ca-app-pub-3940256099942544/6300978111"
    const val INTERSTITIAL_TEST = "ca-app-pub-3940256099942544/1033173712"

    // TODO: Replace with your actual Ad Unit IDs from AdMob console
    // Get them from: https://apps.admob.com/
    // const val BANNER_PROD = "ca-app-pub-YOUR_PUBLISHER_ID/YOUR_BANNER_ID"
    // const val INTERSTITIAL_PROD = "ca-app-pub-YOUR_PUBLISHER_ID/YOUR_INTERSTITIAL_ID"
}

/**
 * How to use in your screens:
 *
 * if (!purchaseManager.hasRemovedAds) {
 *     BannerAdView(adUnitId = AdUnitIds.BANNER_TEST)
 * }
 */