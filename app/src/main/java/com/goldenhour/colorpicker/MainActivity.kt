package com.goldenhour.colorpicker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.remember
import com.goldenhour.colorpicker.extensions.validateSharedPreferneces
import com.goldenhour.colorpicker.ui.screens.Color_Picker_Content_View
import com.goldenhour.colorpicker.ui.screens.colorpicker.COLOR_PICKER_DATA_PROVIDER
import com.goldenhour.colorpicker.ui.theme.ColorPickerTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.java.KoinJavaComponent


@ExperimentalMaterialApi
@ExperimentalPagerApi
@InternalCoroutinesApi
@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        validateSharedPreferneces()
        setContent {
            ColorPickerTheme {
                val view_model =
                    remember { KoinJavaComponent.get(COLOR_PICKER_DATA_PROVIDER().javaClass) }
                Color_Picker_Content_View(view_model)
            }
        }
    }
}