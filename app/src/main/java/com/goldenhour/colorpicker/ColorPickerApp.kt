package com.goldenhour.colorpicker

import android.app.Application
import androidx.compose.material.ExperimentalMaterialApi
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


@InternalCoroutinesApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
class ColorPickerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ColorPickerApp)
            modules(homeModule)
        }
    }
}