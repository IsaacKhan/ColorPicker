package com.goldenhour.colorpicker.ui.screens.colorpicker

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.AndroidUiDispatcher.Companion.Main
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goldenhour.colorpicker.extensions.isNumber
import com.goldenhour.colorpicker.managers.SHARED_PREFERENCES_MANAGER
import com.goldenhour.colorpicker.managers.getSavedColorSet
import com.goldenhour.colorpicker.managers.setSavedColorSet
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.roundToInt

@InternalCoroutinesApi
@ExperimentalPagerApi
class COLOR_PICKER_DATA_PROVIDER : ViewModel() {
    companion object {
        const val COLORS_PER_PAGE = 10
        const val COLORS_PER_ROW = 12
        const val INITIAL_COLOR = "1"
        const val red_code = 1
        const val green_code = 2
        const val blue_code = 3
    }

    val tabNames = listOf(
        "Grid",
        "Spectrum",
        "Sliders",
    )
    val selectedTabIndex = mutableStateOf(2)

    // Grid Colors
    val grid_color_list = listOf(
        // White to Black
        "#FFFFFF",
        "#EEEEEE",
        "#CCCCCC",
        "#BBBBBB",
        "#AAAAAA",
        "#999999",
        "#888888",
        "#777777",
        "#666666",
        "#333333",
        "#111111",
        "#000000",

        // Darker
        "#009696",
        "#000096",
        "#800096",
        "#960096",
        "#960000",
        "#968000",
        "#96C500",
        "#96D500",
        "#969600",
        "#809600",
        "#009600",
        "#009680",
        "#00AFAF",
        "#0000AF",
        "#8000AF",
        "#AF00AF",
        "#AF0000",
        "#AF8000",
        "#AFC500",
        "#AFD500",
        "#AFAF00",
        "#80AF00",
        "#00AF00",
        "#00AF80",
        "#00C8C8",
        "#0000C8",
        "#8000C8",
        "#C800C8",
        "#C80000",
        "#C88000",
        "#C8C500",
        "#C8D500",
        "#C8C800",
        "#80C800",
        "#00C800",
        "#00C880",
        "#00E6E6",
        "#0000E6",
        "#8000E6",
        "#E600E6",
        "#E60000",
        "#E68000",
        "#E6C500",
        "#E6D500",
        "#E6E600",
        "#80E600",
        "#00E600",
        "#00E680",

        //   Cyan,      Blue,    Purple,      Pink,       Red,    Orange, Y. Orange,      Gold,    Yellow,  Y. Green,     Green,  Sea Green,
        "#00FFFF",
        "#0000FF",
        "#8000FF",
        "#FF00FF",
        "#FF0000",
        "#FF8000",
        "#FFC500",
        "#FFD500",
        "#FFFF00",
        "#80FF00",
        "#00FF00",
        "#00FF80",

        // Lighter
        "#64FFFF",
        "#6464FF",
        "#8064FF",
        "#FF64FF",
        "#FF6464",
        "#FF8064",
        "#FFC564",
        "#FFD564",
        "#FFFF64",
        "#80FF64",
        "#64FF64",
        "#64FF80",
        "#7DFFFF",
        "#7D7DFF",
        "#807DFF",
        "#FF7DFF",
        "#FF7D7D",
        "#FF807D",
        "#FFC57D",
        "#FFD57D",
        "#FFFF7D",
        "#80FF7D",
        "#7DFF7D",
        "#7DFF80",
        "#96FFFF",
        "#9696FF",
        "#8096FF",
        "#FF96FF",
        "#FF9696",
        "#FF8096",
        "#FFC596",
        "#FFD596",
        "#FFFF96",
        "#80FF96",
        "#96FF96",
        "#96FF80",
        "#AFFFFF",
        "#AFAFFF",
        "#80AFFF",
        "#FFAFFF",
        "#FFAFAF",
        "#FF80AF",
        "#FFC5AF",
        "#FFD5AF",
        "#FFFFAF",
        "#80FFAF",
        "#AFFFAF",
        "#AFFF80",
    )


    // Slider States
    var redPosition by mutableStateOf(1f)
    var greenPosition by mutableStateOf(1f)
    var bluePosition by mutableStateOf(1f)

    var redTextField by mutableStateOf(INITIAL_COLOR)
    var greenTextField by mutableStateOf(INITIAL_COLOR)
    var blueTextField by mutableStateOf(INITIAL_COLOR)

    var redReadOnly by mutableStateOf(false)
    var greenReadOnly by mutableStateOf(false)
    var blueReadOnly by mutableStateOf(false)

    val pagerState = PagerState()
    var pageCount by mutableStateOf(1)
    var visiblePageIndex by mutableStateOf(0)

    // Saved Colors List Data
    private var savedColorSet = mutableStateOf(setOf(""))
    var colorListToDisplay = mutableStateOf(listOf(""))

    private var _colorToDisplay: MutableStateFlow<Color> = MutableStateFlow(Color.White)
    var colorToDisplay: StateFlow<Color> = _colorToDisplay


    init {
        viewModelScope.launch {
            withContext(Main) {
                initializeData()
            }
            observeCurrentPage()
        }
    }

    private fun initializeData() {
        savedColorSet.value = SHARED_PREFERENCES_MANAGER.shared.getSavedColorSet().toSet()
        colorListToDisplay.value = setColorSetToDisplay(visiblePageIndex)
        pageCount = calculatePageCount()
    }

    private suspend fun observeCurrentPage() {
        snapshotFlow { pagerState.currentPage }.collect(object : FlowCollector<Int> {
            override suspend fun emit(value: Int) {
                visiblePageIndex = value
                colorListToDisplay.value = setColorSetToDisplay(value)
            }
        })
    }

    private fun calculatePageCount(): Int {
        return if (savedColorSet.value.size > COLORS_PER_PAGE - 1)
            (savedColorSet.value.size / COLORS_PER_PAGE) + 1
        else 1
    }

    private fun setInitialState(color_to_update: Int) {
        viewModelScope.launch {
            when (color_to_update) {
                1 -> {
                    redReadOnly = false
                    redTextField = ""
                    redPosition = 1f
                }
                2 -> {
                    greenReadOnly = false
                    greenTextField = ""
                    greenPosition = 1f
                }
                3 -> {
                    blueReadOnly = false
                    blueTextField = ""
                    bluePosition = 1f
                }
            }
        }
    }

    private fun updatedSavedColorSet(colorAsHex: String): Set<String> = when {
        savedColorSet.value.first().isEmpty() -> {
            mutableSetOf(colorAsHex)
        }
        savedColorSet.value.last().isEmpty() -> {
            val newColorSet = savedColorSet.value.toMutableSet()
            val removeThisEmptyColor = newColorSet.last()
            newColorSet.remove(removeThisEmptyColor)
            newColorSet.add(colorAsHex)
            newColorSet
        }
        (savedColorSet.value.size + 1) % COLORS_PER_PAGE == 0 -> {
            val newColorSet = savedColorSet.value.toMutableSet()
            newColorSet.add(colorAsHex)
            newColorSet.add("")
            newColorSet
        }
        else -> {
            val newColorSet = savedColorSet.value.toMutableSet()
            newColorSet.add(colorAsHex)
            newColorSet
        }
    }

    fun setReadOnly(color_to_update: Int, read_only: Boolean) {
        viewModelScope.launch {
            when (color_to_update) {
                1 -> {
                    redReadOnly = read_only
                }
                2 -> {
                    greenReadOnly = read_only
                }
                3 -> {
                    blueReadOnly = read_only
                }
            }
        }
    }

    fun setCurrentDisplayColor(color: Color) {
        _colorToDisplay.value = color
    }

    fun setColorSetToDisplay(index: Int): List<String> {
        return savedColorSet.value.chunked(COLORS_PER_PAGE)[index]
    }

    fun updateColorListToDisplay() {
        viewModelScope.launch {
            val color = colorToDisplay.value.toArgb()
            val colorAsHex = java.lang.String.format(
                "#%02x%02x%02x",
                color.red,
                color.green,
                color.blue
            )

            savedColorSet.value = updatedSavedColorSet(colorAsHex)

            colorListToDisplay.value =
                savedColorSet.value.chunked(COLORS_PER_PAGE)[visiblePageIndex]
            SHARED_PREFERENCES_MANAGER.shared.setSavedColorSet(savedColorSet.value)
            pageCount = calculatePageCount()
        }
    }

    fun updateTabIndex(index: Int) {
        viewModelScope.launch {
            selectedTabIndex.value = index
        }
    }

    fun updateColorFromSlider(color_to_update: Int, read_only: Boolean, position: Float) {
        viewModelScope.launch {
            when (color_to_update) {
                1 -> {
                    redReadOnly = read_only
                    redPosition = position
                    redTextField = position.roundToInt().toString()

                    setCurrentDisplayColor(
                        Color(
                            position.roundToInt(),
                            greenPosition.roundToInt(),
                            bluePosition.roundToInt()
                        )
                    )
                }
                2 -> {
                    greenReadOnly = read_only
                    greenPosition = position
                    greenTextField = position.roundToInt().toString()

                    setCurrentDisplayColor(
                        Color(
                            redPosition.roundToInt(),
                            position.roundToInt(),
                            bluePosition.roundToInt()
                        )
                    )
                }
                3 -> {
                    blueReadOnly = read_only
                    bluePosition = position
                    blueTextField = position.roundToInt().toString()

                    setCurrentDisplayColor(
                        Color(
                            redPosition.roundToInt(),
                            greenPosition.roundToInt(),
                            position.roundToInt(),
                        )
                    )
                }
            }
        }
    }

    fun updateColorFromTextField(color_to_update: Int, text: String) {
        viewModelScope.launch {
            if (text.isNotEmpty()) {
                if (text.isNumber() && text.toFloat() in 1f..255f) {
                    when (color_to_update) {
                        1 -> {
                            redTextField = text
                            redPosition = text.toFloat()
                            setCurrentDisplayColor(
                                Color(
                                    text.toInt(),
                                    greenPosition.roundToInt(),
                                    bluePosition.roundToInt()
                                )
                            )
                        }
                        2 -> {
                            greenTextField = text
                            greenPosition = text.toFloat()
                            setCurrentDisplayColor(
                                Color(
                                    redPosition.roundToInt(),
                                    text.toInt(),
                                    bluePosition.roundToInt()
                                )
                            )
                        }
                        3 -> {
                            blueTextField = text
                            bluePosition = text.toFloat()
                            setCurrentDisplayColor(
                                Color(
                                    redPosition.roundToInt(),
                                    greenPosition.roundToInt(),
                                    text.toInt()
                                )
                            )
                        }
                    }
                }
            } else setInitialState(color_to_update)
        }
    }
}