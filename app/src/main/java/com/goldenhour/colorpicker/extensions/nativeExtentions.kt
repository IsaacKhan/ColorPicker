package com.goldenhour.colorpicker.extensions

import androidx.compose.ui.graphics.Color

fun String.isNumber(): Boolean {
    val integerChars = '0'..'9'
    var dotOccurred = 0
    return this.all { it in integerChars || it == '.' && dotOccurred++ < 1 }
}

fun Color.Companion.parse(colorString: String): Color =
    Color(color = android.graphics.Color.parseColor(colorString))