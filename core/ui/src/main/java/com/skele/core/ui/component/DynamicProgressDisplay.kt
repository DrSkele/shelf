package com.skele.core.ui.component

import android.R
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.atan2

@Composable
fun DynamicProgressDisplay(
    remainingTimeMillis: Long,
    totalTimeMillis: Long,
    isMoving: Boolean,
    startPoint: Offset = Offset(0.2f, 0.85f),
    endPoint: Offset = Offset(0.8f, 0.15f),
    traveledPathColor: Color = Color.Companion.Green,
    remainingPathColor: Color = Color.Companion.Red,
    originColor: Color = Color.Companion.Green,
    destinationColor: Color = Color.Companion.Red,
    originRadius: Float = 8f,
    destinationRadius: Float = 8f,
    idleResourceId: Int,
    movingResourceId: Int,
    modifier: Modifier = Modifier.Companion,
) {
    val progress =
        if (totalTimeMillis > 0) {
            1f - (remainingTimeMillis.toFloat() / totalTimeMillis.toFloat()).coerceIn(0f, 1f)
        } else {
            1f
        }

    Box(
        modifier =
            modifier
                .fillMaxSize(),
        contentAlignment = Alignment.Companion.Center,
    ) {
        Canvas(
            modifier = Modifier.Companion.fillMaxSize(),
        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height

            // Convert relative coordinates to absolute coordinates
            val absoluteStartPoint =
                Offset(
                    x = canvasWidth * startPoint.x,
                    y = canvasHeight * startPoint.y,
                )
            val absoluteEndPoint =
                Offset(
                    x = canvasWidth * endPoint.x,
                    y = canvasHeight * endPoint.y,
                )

            // Calculate current spaceship position on straight line
            val currentPos =
                Offset(
                    x = absoluteStartPoint.x + progress * (absoluteEndPoint.x - absoluteStartPoint.x),
                    y = absoluteStartPoint.y + progress * (absoluteEndPoint.y - absoluteStartPoint.y),
                )

            // Draw traveled path in green
            if (progress > 0f) {
                drawLine(
                    color = traveledPathColor,
                    start = absoluteStartPoint,
                    end = currentPos,
                    strokeWidth = 8.dp.toPx(),
                    cap = StrokeCap.Companion.Round,
                    pathEffect =
                        PathEffect.Companion.dashPathEffect(
                            floatArrayOf(20f, 60f),
                            originRadius.dp.toPx(),
                        ),
                )
            }

            // Draw remaining path in red
            if (progress < 1f) {
                drawLine(
                    color = remainingPathColor,
                    start = absoluteEndPoint,
                    end = currentPos,
                    strokeWidth = 8.dp.toPx(),
                    cap = StrokeCap.Companion.Round,
                    pathEffect =
                        PathEffect.Companion.dashPathEffect(
                            floatArrayOf(20f, 60f),
                            destinationRadius.dp.toPx(),
                        ),
                )
            }

            // Draw start point
            drawCircle(
                color = originColor,
                radius = originRadius.dp.toPx(),
                center = absoluteStartPoint,
            )

            // Draw end point
            drawCircle(
                color = destinationColor,
                radius = destinationRadius.dp.toPx(),
                center = absoluteEndPoint,
            )
        }

        IndicatorAlongPath(
            progress = progress,
            startPoint = startPoint,
            endPoint = endPoint,
            imageResourceId = if (isMoving) movingResourceId else idleResourceId,
            modifier = Modifier.Companion.fillMaxSize(),
        )
    }
}

@Composable
private fun IndicatorAlongPath(
    progress: Float,
    startPoint: Offset,
    endPoint: Offset,
    imageResourceId: Int,
    modifier: Modifier = Modifier.Companion,
) {
    BoxWithConstraints(modifier = modifier) {
        val canvasWidth = constraints.maxWidth.toFloat()
        val canvasHeight = constraints.maxHeight.toFloat()

        // Convert relative coordinates to absolute coordinates
        val absoluteStartPoint =
            Offset(
                x = canvasWidth * startPoint.x,
                y = canvasHeight * startPoint.y,
            )
        val absoluteEndPoint =
            Offset(
                x = canvasWidth * endPoint.x,
                y = canvasHeight * endPoint.y,
            )

        // Calculate position along the straight line
        val spaceshipPosition =
            Offset(
                x = absoluteStartPoint.x + progress * (absoluteEndPoint.x - absoluteStartPoint.x),
                y = absoluteStartPoint.y + progress * (absoluteEndPoint.y - absoluteStartPoint.y),
            )

        // Calculate rotation angle for straight line
        val rotationAngle =
            atan2(
                absoluteEndPoint.y - absoluteStartPoint.y,
                absoluteEndPoint.x - absoluteStartPoint.x,
            ) * 180f / PI.toFloat()

        Image(
            painter = painterResource(id = imageResourceId),
            contentDescription = "Indicator",
            modifier =
                Modifier.Companion
                    .size(48.dp)
                    .offset(
                        x = (spaceshipPosition.x / LocalDensity.current.density).dp - 24.dp,
                        y = (spaceshipPosition.y / LocalDensity.current.density).dp - 24.dp,
                    ).rotate(rotationAngle),
        )
    }
}

// Preview functions for different timer states
@Preview(showBackground = true, widthDp = 400, heightDp = 600)
@Composable
fun SpaceshipTimerPreview_Start() {
    MaterialTheme {
        DynamicProgressDisplay(
            totalTimeMillis = 900000L, // 15 minutes
            remainingTimeMillis = 900000L, // Full time remaining
            isMoving = false,
            startPoint = Offset(0.2f, 0.85f), // Default bottom-left
            endPoint = Offset(0.8f, 0.15f), // Default top-right
            traveledPathColor = Color.Companion.Green,
            remainingPathColor = Color.Companion.Red,
            idleResourceId = R.drawable.ic_media_play,
            movingResourceId = R.drawable.ic_media_play,
            modifier = Modifier.Companion.fillMaxSize(),
        )
    }
}

@Preview(showBackground = true, widthDp = 400, heightDp = 600)
@Composable
fun SpaceshipTimerPreview_HalfWay() {
    MaterialTheme {
        DynamicProgressDisplay(
            totalTimeMillis = 900000L, // 15 minutes
            remainingTimeMillis = 450000L, // 7.5 minutes remaining
            isMoving = true,
            startPoint = Offset(0.2f, 0.85f),
            endPoint = Offset(0.8f, 0.15f),
            traveledPathColor = Color.Companion.Green,
            remainingPathColor = Color.Companion.Red,
            idleResourceId = R.drawable.ic_media_play,
            movingResourceId = R.drawable.ic_media_ff,
            modifier = Modifier.Companion.fillMaxSize(),
        )
    }
}

@Preview(showBackground = true, widthDp = 400, heightDp = 600)
@Composable
fun SpaceshipTimerPreview_Horizontal() {
    MaterialTheme {
        DynamicProgressDisplay(
            totalTimeMillis = 600000L, // 10 minutes
            remainingTimeMillis = 300000L, // 5 minutes remaining
            isMoving = true,
            startPoint = Offset(0.1f, 0.5f), // Left center
            endPoint = Offset(0.9f, 0.5f), // Right center
            traveledPathColor = Color.Companion.Green,
            remainingPathColor = Color.Companion.Red,
            idleResourceId = R.drawable.ic_media_play,
            movingResourceId = R.drawable.ic_media_ff,
            modifier = Modifier.Companion.fillMaxSize(),
        )
    }
}

@Preview(showBackground = true, widthDp = 400, heightDp = 600)
@Composable
fun SpaceshipTimerPreview_Vertical() {
    MaterialTheme {
        DynamicProgressDisplay(
            totalTimeMillis = 300000L, // 5 minutes
            remainingTimeMillis = 120000L, // 2 minutes remaining
            isMoving = true,
            startPoint = Offset(0.5f, 0.9f), // Bottom center
            endPoint = Offset(0.5f, 0.1f), // Top center
            traveledPathColor = Color.Companion.Green,
            remainingPathColor = Color.Companion.Red,
            idleResourceId = R.drawable.ic_media_play,
            movingResourceId = R.drawable.ic_media_ff,
            modifier = Modifier.Companion.fillMaxSize(),
        )
    }
}

@Preview(showBackground = true, widthDp = 400, heightDp = 600)
@Composable
fun SpaceshipTimerPreview_CustomDiagonal() {
    MaterialTheme {
        DynamicProgressDisplay(
            totalTimeMillis = 600000L, // 10 minutes
            remainingTimeMillis = 200000L, // ~3.3 minutes remaining
            isMoving = true,
            startPoint = Offset(0.15f, 0.2f), // Top-left
            endPoint = Offset(0.85f, 0.8f), // Bottom-right
            traveledPathColor = Color.Companion.Blue,
            remainingPathColor = Color.Companion.Magenta,
            idleResourceId = R.drawable.ic_media_play,
            movingResourceId = R.drawable.ic_media_ff,
            modifier = Modifier.Companion.fillMaxSize(),
        )
    }
}

@Preview(showBackground = true, widthDp = 400, heightDp = 600)
@Composable
fun SpaceshipTimerPreview_Finished() {
    MaterialTheme {
        DynamicProgressDisplay(
            totalTimeMillis = 900000L, // 15 minutes
            remainingTimeMillis = 0L, // Timer finished
            isMoving = false,
            startPoint = Offset(0.2f, 0.85f),
            endPoint = Offset(0.8f, 0.15f),
            traveledPathColor = Color.Companion.Green,
            remainingPathColor = Color.Companion.Red,
            idleResourceId = R.drawable.ic_media_play,
            movingResourceId = R.drawable.ic_media_ff,
            modifier = Modifier.Companion.fillMaxSize(),
        )
    }
}