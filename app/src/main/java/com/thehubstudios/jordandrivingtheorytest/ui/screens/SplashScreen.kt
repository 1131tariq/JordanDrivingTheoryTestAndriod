package com.thehubstudios.jordandrivingtheorytest.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.drawscope.Stroke


@Composable
fun SplashScreen() {
    val infiniteTransition = rememberInfiniteTransition(label = "shine")
    val shineOffset by infiniteTransition.animateFloat(
        initialValue = -200f,
        targetValue = 200f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shineAnim"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp),
                contentAlignment = Alignment.Center
            ) {
                // Two black rectangles = H pillars
                Row(
                    horizontalArrangement = Arrangement.spacedBy(100.dp, Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()   // take full width
                ) {
                    Box(
                        modifier = Modifier
                            .width(65.dp)
                            .height(360.dp)
                            .background(Color.Black)
                    )
                    Box(
                        modifier = Modifier
                            .width(65.dp)
                            .height(360.dp)
                            .background(Color.Black)
                    )
                }

                // Arc in the middle
                Canvas(modifier = Modifier.size(width = 130.dp, height = 60.dp)) {
                    val arcRect = Rect(
                        offset = Offset(0f, size.height / 2),
                        size = Size(size.width, size.height)
                    )

                    // White base arc (cutout effect)
                    drawArc(
                        color = Color.White,
                        startAngle = -145f,
                        sweepAngle = 110f,
                        useCenter = false,
                        topLeft = arcRect.topLeft,
                        size = arcRect.size,
                        style = Stroke(width = size.height)
                    )
                }

                // Red arc with shine overlay
                Canvas(modifier = Modifier.size(width = 120.dp, height = 50.dp)) {
                    val arcRect = Rect(
                        offset = Offset(0f, size.height / 2),
                        size = Size(size.width, size.height)
                    )

                    // Red arc
                    drawArc(
                        color = Color.Red,
                        startAngle = -145f,
                        sweepAngle = 110f,
                        useCenter = false,
                        topLeft = arcRect.topLeft,
                        size = arcRect.size,
                        style = Stroke(width = size.height)
                    )

                    // Shine effect (linear gradient moving across)
                    val shineBrush = Brush.linearGradient(
                        colors = listOf(Color.Transparent, Color.White.copy(alpha = 0.9f), Color.Transparent),
                        start = Offset(shineOffset, 0f),
                        end = Offset(shineOffset + 200f, size.height)
                    )

                    drawArc(
                        brush = shineBrush,
                        startAngle = -145f,
                        sweepAngle = 110f,
                        useCenter = false,
                        topLeft = arcRect.topLeft,
                        size = arcRect.size,
                        style = Stroke(width = size.height)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Footer text
            Text(
                text = "The Hub Studios®",
                color = Color.Black,
                fontSize = 20.sp,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
