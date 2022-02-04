package com.goldenhour.colorpicker

import androidx.compose.material.ExperimentalMaterialApi
import com.goldenhour.colorpicker.ui.screens.colorpicker.COLOR_PICKER_DATA_PROVIDER
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


@ExperimentalPagerApi
@InternalCoroutinesApi
@ExperimentalMaterialApi
val homeModule = module {
    viewModel { COLOR_PICKER_DATA_PROVIDER() }
}