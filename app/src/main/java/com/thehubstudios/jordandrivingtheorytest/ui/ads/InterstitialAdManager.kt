package com.thehubstudios.jordandrivingtheorytest.ui.ads

import android.app.Activity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

/**
 * Manages interstitial (full-screen) ads
 *
 * Usage:
 * val adManager = InterstitialAdManager(activity, AdUnitIds.INTERSTITIAL_TEST)
 * adManager.showAd {
 *     // Code to run after ad is closed
 *     navController.navigate("test/1")
 * }
 */
class InterstitialAdManager(
    private val activity: Activity,
    private val adUnitId: String
) {
    private var interstitialAd: InterstitialAd? = null
    private var isLoading = false

    init {
        loadAd()
    }

    /**
     * Loads an interstitial ad from AdMob
     * Called automatically on init and after each ad is shown
     */
    private fun loadAd() {
        if (isLoading) {
            println("⚠️ Ad is already loading")
            return
        }

        isLoading = true
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            activity,
            adUnitId,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    interstitialAd = ad
                    isLoading = false
                    println("✅ Interstitial ad loaded successfully")
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    interstitialAd = null
                    isLoading = false
                    println("❌ Interstitial ad failed to load: ${error.message}")
                    println("   Error code: ${error.code}")
                }
            }
        )
    }

    /**
     * Shows the loaded interstitial ad
     * If no ad is available, calls onAdDismissed immediately
     *
     * @param onAdDismissed Callback executed when ad is dismissed or unavailable
     */
    fun showAd(onAdDismissed: () -> Unit) {
        interstitialAd?.let { ad ->
            ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    println("✅ User closed the interstitial ad")
                    interstitialAd = null
                    loadAd() // Preload next ad for better UX
                    onAdDismissed()
                }

                override fun onAdFailedToShowFullScreenContent(error: AdError) {
                    println("❌ Failed to show interstitial ad: ${error.message}")
                    interstitialAd = null
                    onAdDismissed()
                }

                override fun onAdShowedFullScreenContent() {
                    println("✅ Interstitial ad displayed to user")
                }
            }
            ad.show(activity)
        } ?: run {
            // No ad loaded, proceed without showing ad
            println("⚠️ No interstitial ad available, proceeding without ad")
            onAdDismissed()
        }
    }

    /**
     * Check if an ad is ready to be displayed
     * @return true if ad is loaded and ready
     */
    fun isAdReady(): Boolean = interstitialAd != null

    /**
     * Clean up resources when done
     * Call this in onDestroy or when no longer needed
     */
    fun destroy() {
        interstitialAd = null
    }
}

/**
 * Example usage in a Composable:
 *
 * @Composable
 * fun MyScreen() {
 *     val context = LocalContext.current
 *     val activity = context as Activity
 *     val adManager = remember {
 *         InterstitialAdManager(activity, AdUnitIds.INTERSTITIAL_TEST)
 *     }
 *
 *     Button(onClick = {
 *         adManager.showAd {
 *             // Navigate or do something after ad
 *             navController.navigate("nextScreen")
 *         }
 *     }) {
 *         Text("Show Ad & Continue")
 *     }
 * }
 */