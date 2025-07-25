package com.skele.todolist.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.max
import kotlin.math.min

@Composable
fun ProgressButton(
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    text: String? = null,
    progress: Float = 0f,
    backgroundColor: Color,
    overlayColor: Color,
    onClick: () -> Unit = {},
) {
    val clampedProgress = max(0f, min(1f, progress))

    val animatedProgress by animateFloatAsState(
        targetValue = clampedProgress,
        animationSpec = tween(100),
        label = "progress",
    )

    val interactionSource = remember { MutableInteractionSource() }

    Row(
        modifier =
            modifier
                .clip(RoundedCornerShape(8.dp))
                .clickable(
                    interactionSource = interactionSource,
                    indication =
                        rememberRipple(
                            color = Color.White,
                        ),
                    onClick = onClick,
                ).drawBehind {
                    val canvasWidth = size.width
                    val canvasHeight = size.height
                    val progressWidth = canvasWidth * animatedProgress

                    // Draw background
                    drawRect(
                        color = backgroundColor.copy(alpha = 0.75f),
                        size = Size(progressWidth, canvasHeight),
                    )

                    // Draw progress fill
                    if (animatedProgress > 0f) {
                        drawRect(
                            topLeft = Offset(x = progressWidth, y = 0f),
                            color = backgroundColor,
                            size = Size(canvasWidth - progressWidth, canvasHeight),
                        )
                    }
                }.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        if (icon != null) Icon(icon, contentDescription = text, tint = overlayColor)
        if (text != null) Text(text, color = overlayColor)
    }
}

@Preview(showBackground = true)
@Composable
fun IconButtonPreview() {
    ProgressButton(
        modifier = Modifier,
        progress = 0.2f,
        backgroundColor = Color.Blue,
        overlayColor = Color.White,
        icon = Icons.Default.Check,
        text = "Text",
    )
}
