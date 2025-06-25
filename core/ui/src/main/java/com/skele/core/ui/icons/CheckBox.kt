package com.skele.core.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path

val CustomIcons.CheckBox_Blank: ImageVector
    get() {
        if (_checkBoxBlank != null) return _checkBoxBlank!!
        _checkBoxBlank =
            customIcon(
                name = "CheckBox",
            ) {
                path(
                    fill = SolidColor(Color.White), // #FFFFFFFF
                    stroke = null,
                    strokeLineWidth = 0f,
                    strokeLineCap = StrokeCap.Butt,
                    strokeLineJoin = StrokeJoin.Miter,
                    strokeLineMiter = 4f,
                    pathFillType = PathFillType.NonZero,
                ) {
                    moveTo(19f, 3f)
                    horizontalLineTo(5f)
                    curveTo(3.89f, 3f, 3f, 3.89f, 3f, 5f)
                    verticalLineToRelative(14f)
                    curveToRelative(0f, 0.53f, 0.21f, 1.04f, 0.59f, 1.41f)
                    curveTo(3.96f, 20.8f, 4.47f, 21f, 5f, 21f)
                    horizontalLineToRelative(14f)
                    curveToRelative(0.53f, 0f, 1.04f, -0.21f, 1.41f, -0.59f)
                    curveTo(20.8f, 20.04f, 21f, 19.53f, 21f, 19f)
                    verticalLineTo(5f)
                    curveToRelative(0f, -0.53f, -0.21f, -1.04f, -0.59f, -1.41f)
                    curveTo(20.04f, 3.2f, 19.53f, 3f, 19f, 3f)
                    close()
                    moveTo(19f, 5f)
                    verticalLineToRelative(14f)
                    horizontalLineTo(5f)
                    verticalLineTo(5f)
                    horizontalLineToRelative(14f)
                    close()
                }
            }

        return _checkBoxBlank!!
    }

val CustomIcons.CheckBox_Filled: ImageVector
    get() {
        if (_checkBoxFilled != null) return _checkBoxFilled!!
        _checkBoxFilled =
            customIcon(
                name = "CheckBox",
            ) {
                path(
                    fill = SolidColor(Color.White), // #FFFFFFFF
                    stroke = null,
                    strokeLineWidth = 0f,
                    strokeLineCap = StrokeCap.Butt,
                    strokeLineJoin = StrokeJoin.Miter,
                    strokeLineMiter = 4f,
                    pathFillType = PathFillType.EvenOdd, // android:fillType="evenOdd"
                ) {
                    moveTo(5f, 3f)
                    curveTo(4.47f, 3f, 3.96f, 3.21f, 3.59f, 3.59f)
                    curveTo(3.2f, 3.96f, 3f, 4.47f, 3f, 5f)
                    verticalLineToRelative(14f)
                    curveToRelative(0f, 0.53f, 0.21f, 1.04f, 0.59f, 1.41f)
                    curveTo(3.96f, 20.8f, 4.47f, 21f, 5f, 21f)
                    horizontalLineToRelative(14f)
                    curveToRelative(0.53f, 0f, 1.04f, -0.21f, 1.41f, -0.59f)
                    curveTo(20.8f, 20.04f, 21f, 19.53f, 21f, 19f)
                    verticalLineTo(5f)
                    curveToRelative(0f, -0.53f, -0.21f, -1.04f, -0.59f, -1.41f)
                    curveTo(20.04f, 3.2f, 19.53f, 3f, 19f, 3f)
                    horizontalLineTo(5f)
                    close()
                    moveTo(16.95f, 9.8f)
                    curveToRelative(0.19f, -0.2f, 0.3f, -0.45f, 0.3f, -0.71f)
                    curveToRelative(0f, -0.27f, -0.11f, -0.52f, -0.3f, -0.7f)
                    curveToRelative(-0.19f, -0.2f, -0.44f, -0.3f, -0.7f, -0.3f)
                    curveToRelative(-0.27f, 0f, -0.53f, 0.1f, -0.71f, 0.3f)
                    lineToRelative(-4.95f, 4.94f)
                    lineToRelative(-2.13f, -2.12f)
                    curveToRelative(-0.09f, -0.1f, -0.2f, -0.17f, -0.32f, -0.22f)
                    curveToRelative(-0.12f, -0.05f, -0.25f, -0.07f, -0.38f, -0.07f)
                    curveToRelative(-0.27f, 0f, -0.52f, 0.1f, -0.7f, 0.29f)
                    curveToRelative(-0.2f, 0.19f, -0.3f, 0.44f, -0.3f, 0.7f)
                    curveToRelative(0f, 0.27f, 0.1f, 0.53f, 0.29f, 0.71f)
                    lineToRelative(2.76f, 2.76f)
                    curveToRelative(0.1f, 0.1f, 0.22f, 0.19f, 0.35f, 0.24f)
                    curveToRelative(0.14f, 0.06f, 0.28f, 0.08f, 0.43f, 0.08f)
                    curveToRelative(0.14f, 0f, 0.28f, -0.02f, 0.42f, -0.08f)
                    curveToRelative(0.13f, -0.05f, 0.25f, -0.14f, 0.35f, -0.24f)
                    lineToRelative(5.59f, -5.58f)
                    close()
                }
            }
        return _checkBoxFilled!!
    }

private var _checkBoxBlank: ImageVector? = null

private var _checkBoxFilled: ImageVector? = null
