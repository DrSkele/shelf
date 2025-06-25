package com.skele.core.ui.icons

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

object CustomIcons

inline fun customIcon(
    name: String,
    block: ImageVector.Builder.() -> ImageVector.Builder,
): ImageVector =
    ImageVector
        .Builder(
            name = name,
            defaultWidth = CUSTOM_ICON_DIMENSION.dp,
            defaultHeight = CUSTOM_ICON_DIMENSION.dp,
            viewportWidth = CUSTOM_ICON_DIMENSION,
            viewportHeight = CUSTOM_ICON_DIMENSION,
        ).block()
        .build()

const val CUSTOM_ICON_DIMENSION = 24f
