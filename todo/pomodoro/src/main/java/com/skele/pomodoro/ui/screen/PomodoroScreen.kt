package com.skele.pomodoro.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skele.pomodoro.ui.component.TimerCanvasSelector
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

private const val TAG = "PomodoroScreen"

@Composable
fun PomodoroScreen(modifier: Modifier = Modifier) {
    var timer by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize().background(color = Color(0xFFFF5959)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        TimerCanvasSelector(
            currentTime = timer,
            maxTime = 60,
            itemWidth = 50.dp,
            onScrollFinished = {
                timer = it
            }
        )

        Button(
            onClick = {
                timer = (timer + 1) % 60
            },
            modifier = Modifier.padding(top = 16.dp),
        ) {
            Text(text = "Increment Timer")
        }
    }
}

@Composable
fun TimerSelector(
    currentTime: Int,
    maxTime: Int = 60,
    onMinuteSelected: (Int) -> Unit,
    itemWidth: Dp = 50.dp,
    modifier: Modifier = Modifier,
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val density = LocalDensity.current
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    var lastSelectedIndex by remember { mutableIntStateOf(-1) }

    LaunchedEffect(currentTime) {
        val index = (currentTime * 2) // minute → itemIndex (including half-steps)
        coroutineScope.launch {
            listState.animateScrollToItem(index)
        }
    }

    // Snap to nearest item
    LaunchedEffect(listState) {
        snapshotFlow { listState.isScrollInProgress }
            // Only when scrolling has stopped
            // Because flow emits even if scroll is steady, check for ItemScrollOffset change
            .filter { isScrolling -> !isScrolling && listState.firstVisibleItemScrollOffset > 0 }
            .collectLatest {
                val offsetPx = listState.firstVisibleItemScrollOffset
                val index = listState.firstVisibleItemIndex

                val targetIndex =
                    if (offsetPx > with(density) { itemWidth.toPx() } / 2) {
                        index + 1
                    } else {
                        index
                    }

                if (targetIndex != lastSelectedIndex) {
                    lastSelectedIndex = targetIndex
                    onMinuteSelected(targetIndex)
                }

                coroutineScope.launch {
                    listState.animateScrollToItem(targetIndex)
                }
            }
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        val sidePadding = (screenWidth - itemWidth) / 2

        LazyRow(
            state = listState,
            contentPadding = PaddingValues(horizontal = sidePadding),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.Bottom,
            modifier =
                Modifier
                    .fillMaxWidth(),
        ) {
            items(maxTime * 2 + 1) { minute ->
                if (minute % 2 == 0) {
                    MinuteItem(
                        minute = minute / 2,
                        width = itemWidth,
                    )
                } else {
                    HalfMinuteItem(
                        width = itemWidth,
                    )
                }
            }
        }
        HorizontalDivider(thickness = 8.dp, color = Color.White)
        HorizontalDivider(thickness = 4.dp, color = Color.DarkGray)
        HorizontalDivider(thickness = 8.dp, color = Color.White)

        Box(
            modifier =
                Modifier
                    .size(32.dp)
                    .background(color = Color(0xFF56E39F), shape = TriangleShape),
        )
    }
}

@Composable
fun MinuteItem(
    minute: Int,
    width: Dp,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.width(width),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = minute.toString(),
            fontSize = 36.sp,
            color = Color.White,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier =
                Modifier
                    .height(56.dp)
                    .width(4.dp)
                    .background(Color.White),
        )
    }
}

@Composable
fun HalfMinuteItem(
    width: Dp,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.width(width),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier =
                Modifier
                    .height(32.dp)
                    .width(2.dp)
                    .background(Color.White),
        )
    }
}

object TriangleShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline =
        Outline.Generic(
            Path().apply {
                moveTo(size.width / 2f, 0f)
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            },
        )
}

@Preview
@Composable
fun TimerSelectorPreview() {
    TimerSelector(
        currentTime = 60,
        onMinuteSelected = {
            Log.d(TAG, "Selected minute: $it")
        },
        itemWidth = 50.dp,
    )
}

@Preview
@Composable
fun MinuteItemPreview() {
    MinuteItem(
        minute = 25,
        width = 50.dp,
    )
}

@Preview
@Composable
fun HalfMinuteItemPreview() {
    HalfMinuteItem(
        width = 50.dp,
    )
}
