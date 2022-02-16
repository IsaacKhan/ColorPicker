package com.goldenhour.colorpicker.extensions

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.ui.graphics.Color
import com.goldenhour.colorpicker.R
import com.goldenhour.colorpicker.managers.SHARED_PREFERENCES_MANAGER

fun Context.validateSharedPreferneces() {
    //
    SHARED_PREFERENCES_MANAGER.shared.initializePreferences(
        getSharedPreferences(
            getString(R.string.color_picker_preferences),
            ComponentActivity.MODE_PRIVATE
        )
    )
}

fun String.isNumber(): Boolean {
    val integerChars = '0'..'9'
    var dotOccurred = 0
    return this.all { it in integerChars || it == '.' && dotOccurred++ < 1 }
}

fun Color.Companion.parse(colorString: String): Color =
    Color(color = android.graphics.Color.parseColor(colorString))