package com.goldenhour.colorpicker.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.foundation.layout.Arrangement.SpaceEvenly
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.goldenhour.colorpicker.R
import com.goldenhour.colorpicker.extensions.parse
import com.goldenhour.colorpicker.ui.screens.colorpicker.COLOR_PICKER_DATA_PROVIDER
import com.goldenhour.colorpicker.ui.screens.colorpicker.COLOR_PICKER_DATA_PROVIDER.Companion.COLORS_PER_PAGE
import com.goldenhour.colorpicker.ui.screens.colorpicker.COLOR_PICKER_DATA_PROVIDER.Companion.COLORS_PER_ROW
import com.goldenhour.colorpicker.ui.screens.colorpicker.COLOR_PICKER_DATA_PROVIDER.Companion.blue_code
import com.goldenhour.colorpicker.ui.screens.colorpicker.COLOR_PICKER_DATA_PROVIDER.Companion.green_code
import com.goldenhour.colorpicker.ui.screens.colorpicker.COLOR_PICKER_DATA_PROVIDER.Companion.red_code
import com.goldenhour.colorpicker.ui.theme.alert_blue
import com.goldenhour.colorpicker.ui.theme.rainbow
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch

@InternalCoroutinesApi
@ExperimentalPagerApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun Color_Picker_Content_View (color_picker_view_model: COLOR_PICKER_DATA_PROVIDER){
    val viewModel = remember { color_picker_view_model }
    val color by remember(viewModel) { viewModel.colorToDisplay }.collectAsState()

    val coroutineScope = rememberCoroutineScope()
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(BottomSheetValue.Collapsed)
    )

    BackHandler(bottomSheetScaffoldState.bottomSheetState.isExpanded) {
        coroutineScope.launch {
            bottomSheetScaffoldState.bottomSheetState.collapse()
        }
    }

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        backgroundColor = Color.Black,
        topBar = {
            TopAppBar(
                backgroundColor = Color.Black,
            ) {
                IconButton(
                    onClick = {  },
                    modifier = Modifier.weight(.5f, true)
                ) {
                    Row {
                        Icon(
                            Icons.Outlined.KeyboardArrowLeft,
                            contentDescription = "",
                            modifier = Modifier.align(Alignment.CenterVertically),
                            tint = Color.White,
                        )
                        Text(
                            text = "Back",
                            style = MaterialTheme.typography.body2,
                            color = Color.White,
                            fontSize = 16.sp,
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                }
                Spacer(
                    modifier = Modifier.weight(1f, true)
                )
                Spacer(
                    modifier = Modifier.weight(.5f, true)
                )
            }
        },
        content = {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = SpaceEvenly
            ) {
                Row(
                    modifier = Modifier.weight(.5f, true),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.Center
                ){
                    // title
                    Text(
                        text = stringResource(id = R.string.COLOR_PICKER_TITLE),
                        fontSize = 24.sp,
                        color = MaterialTheme.colors.primaryVariant
                    )
                }

                Row(
                    modifier = Modifier.weight(1f, true),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ){
                    // Color Picker
                    OutlinedButton(
                        onClick = {
                            coroutineScope.launch {
                                if (bottomSheetScaffoldState.bottomSheetState.isCollapsed)
                                    bottomSheetScaffoldState.bottomSheetState.expand()
                                else bottomSheetScaffoldState.bottomSheetState.collapse()
                            }
                        },
                        modifier= Modifier.wrapContentHeight(),
                        shape = CircleShape,
                        contentPadding = PaddingValues(10.dp),  //avoid the little icon
                        border = BorderStroke(width = 14.dp, brush = Brush.sweepGradient(colors = rainbow))
                    ) {
                        Box(
                            modifier = Modifier
                                .size(200.dp)
                                .background(color = Color.Black, shape = CircleShape),
                            contentAlignment = Alignment.Center
                        ){
                            Box(
                                modifier = Modifier
                                    .size(160.dp)
                                    .background(color = color, shape = CircleShape),
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier.weight(.75f, true),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ){
                    // save button
                    FloatingActionButton(
                        onClick = { },
                        backgroundColor = Color.Transparent,
                        modifier = Modifier
                            .background(
                                MaterialTheme.colors.primary,
                                RoundedCornerShape(corner = CornerSize(27.dp))
                            )
                            .height(45.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.SAVE_COLOR),
                            color = Color.White,
                            style = MaterialTheme.typography.body1,
                            fontStyle = FontStyle.Italic,
                            modifier = Modifier.
                            padding(15.dp, 0.dp, 15.dp, 0.dp),
                            fontSize = 15.sp)
                    }
                }
            }
        },
        sheetContent = {
            Color_Picker_Selector_Content_View(
                viewModel = viewModel,
                color = color,
                onDismiss = {
                    coroutineScope.launch {
                        bottomSheetScaffoldState.bottomSheetState.collapse()
                    }
                }
            )
        },
        sheetShape = RoundedCornerShape(10.dp),
        sheetBackgroundColor = MaterialTheme.colors.onBackground,
        sheetPeekHeight = 0.dp
    )
}

@InternalCoroutinesApi
@ExperimentalPagerApi
@ExperimentalFoundationApi
@Composable
fun Color_Picker_Selector_Content_View(
    viewModel: COLOR_PICKER_DATA_PROVIDER,
    color: Color,
    onDismiss: (() -> Unit),
) {
    // Dropper Row
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = SpaceBetween
    ){
        IconButton(
            modifier = Modifier.size(48.dp),
            onClick = { /*TODO*/ }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_colorize_white),
                contentDescription = "Dropper Tool",
                tint = alert_blue
            )
        }
        IconButton(
            modifier = Modifier.size(48.dp),
            onClick = { onDismiss.invoke() }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_cancel_white),
                contentDescription = "Dropper Tool",
                tint = Color.Gray
            )
        }
    }

    // Tab Header Row
    TabRow(
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp)),
        backgroundColor = Color.DarkGray,
        selectedTabIndex = viewModel.selectedTabIndex.value,
        indicator = {
            Row(
                modifier = Modifier
                    .tabIndicatorOffset(it[viewModel.selectedTabIndex.value])
                    .zIndex(-1f)
                    .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                Spacer(
                    Modifier
                        .fillMaxSize()
                        .background(
                            Color.Gray,
                            RoundedCornerShape(8.dp)
                        )
                )
            }
        }
    ) {
        viewModel.tabNames.forEachIndexed { index, text ->
            Tab(
                selected = viewModel.selectedTabIndex.value == index,
                onClick = { viewModel.updateTabIndex(index) },
                text = {
                    Text(
                        text = text,
                        color = Color.Black
                    )
                }
            )
        }
    }

    // Tab Content Row
    when(viewModel.selectedTabIndex.value){
        0 -> { Grid_Content_View(viewModel) }
        1 -> { Spectrum_Content_View(viewModel) }
        2 -> { Sliders_Content_View(viewModel) }
    }

    // Saved Colors Row
    Saved_Colors_Content_View(
        viewModel = viewModel,
        color = color,
    )
}

@ExperimentalFoundationApi
@InternalCoroutinesApi
@ExperimentalPagerApi
@Composable
fun Grid_Content_View(
    viewModel: COLOR_PICKER_DATA_PROVIDER
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                end = 16.dp
            )
            .clip(RoundedCornerShape(8.dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        viewModel.grid_color_list.chunked(COLORS_PER_ROW).forEach { colorRow ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                colorRow.forEach { colorAsString ->
                    val color = Color.parse(colorAsString)
                    Spacer(
                        modifier = Modifier
                            .size(32.dp)
                            .background(color)
                            .clickable { viewModel.setCurrentDisplayColor(color) }
                    )
                }
            }
        }
    }
}

@InternalCoroutinesApi
@ExperimentalPagerApi
@Composable
fun Spectrum_Content_View(viewModel: COLOR_PICKER_DATA_PROVIDER) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ){
        Spacer(
            modifier = Modifier
                .size(48.dp)
                .background(
                    color = Color.Red,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(8.dp)
                .align(Alignment.CenterVertically),
        )
    }
}

@InternalCoroutinesApi
@ExperimentalPagerApi
@Composable
fun Sliders_Content_View(
    viewModel: COLOR_PICKER_DATA_PROVIDER
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 0.dp, horizontal = 8.dp)
    ){
        /* Red Slider w/ TextField */
        Row(
            modifier = Modifier.height(60.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = SpaceBetween
        ){
            Slider(
                modifier = Modifier.weight(1f, true),
                value = viewModel.redPosition,
                onValueChange = {
                    viewModel.updateColorFromSlider(
                        color_to_update = red_code,
                        read_only = true,
                        position = it
                    )
                },
                onValueChangeFinished = {
                    viewModel.setReadOnly(
                        color_to_update = red_code,
                        read_only = false
                    )
                },
                valueRange = 1f..255f,
                colors = SliderDefaults.colors(
                    thumbColor = Color.White,
                    activeTrackColor = Color.Red
                )
            )
            Column(
                modifier = Modifier
                    .width(100.dp)
                    .background(
                        MaterialTheme.colors.onSurface,
                        RoundedCornerShape(8.dp)
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                TextField(
                    value = viewModel.redTextField,
                    readOnly = viewModel.redReadOnly,
                    onValueChange = {
                        viewModel.updateColorFromTextField(
                            color_to_update = red_code,
                            text = it
                        )
                    },
                    textStyle = TextStyle(color = Color.White, textAlign = TextAlign.Center),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            viewModel.updateColorFromTextField(red_code, viewModel.redTextField)
                            focusManager.clearFocus()
                        }
                    )
                )
            }
        }

        /* Green Slider w/ TextField */
        Row(
            modifier = Modifier.height(60.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = SpaceBetween
        ){
            Slider(
                modifier = Modifier.weight(1f, true),
                value = viewModel.greenPosition,
                onValueChange = {
                    viewModel.updateColorFromSlider(
                        color_to_update = green_code,
                        read_only = true,
                        position = it
                    )
                },
                onValueChangeFinished = { viewModel.setReadOnly(green_code, false) },
                valueRange = 1f..255f,
                colors = SliderDefaults.colors(
                    thumbColor = Color.White,
                    activeTrackColor = Color.Green
                )
            )
            Column(
                modifier = Modifier
                    .width(100.dp)
                    .background(
                        MaterialTheme.colors.onSurface,
                        RoundedCornerShape(8.dp)
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                TextField(
                    value = viewModel.greenTextField,
                    readOnly = viewModel.greenReadOnly,
                    onValueChange = {
                        viewModel.updateColorFromTextField(green_code, it)
                    },
                    textStyle = TextStyle(color = Color.White, textAlign = TextAlign.Center),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            viewModel.updateColorFromTextField(green_code, viewModel.greenTextField)
                            focusManager.clearFocus()
                        }
                    )
                )
            }
        }

        /* Blue Slider w/ TextField */
        Row(
            modifier = Modifier.height(60.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = SpaceBetween
        ){
            Slider(
                modifier = Modifier.weight(1f, true),
                value = viewModel.bluePosition,
                onValueChange = {
                    viewModel.updateColorFromSlider(
                        color_to_update = blue_code,
                        read_only = true,
                        position = it
                    )
                },
                onValueChangeFinished = { viewModel.setReadOnly(blue_code, false) },
                valueRange = 1f..255f,
                colors = SliderDefaults.colors(
                    thumbColor = Color.White,
                    activeTrackColor = Color.Blue
                )
            )
            Column(
                modifier = Modifier
                    .width(100.dp)
                    .background(
                        MaterialTheme.colors.onSurface,
                        RoundedCornerShape(8.dp)
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                TextField(
                    value = viewModel.blueTextField,
                    readOnly = viewModel.blueReadOnly,
                    onValueChange = {
                        viewModel.updateColorFromTextField(blue_code, it)
                    },
                    textStyle = TextStyle(color = Color.White, textAlign = TextAlign.Center),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            viewModel.updateColorFromTextField(blue_code, viewModel.blueTextField)
                            focusManager.clearFocus()
                        }
                    )
                )
            }
        }
    }
}

@InternalCoroutinesApi
@ExperimentalPagerApi
@Composable
@ExperimentalFoundationApi
fun Saved_Colors_Content_View(
    viewModel: COLOR_PICKER_DATA_PROVIDER,
    color: Color,
){
    Column{
        Row(
            modifier = Modifier.padding(start = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = color,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .align(Alignment.CenterVertically),
            )
            HorizontalPager(
                count = viewModel.pageCount,
                state = viewModel.pagerState
            ){ pageIndex ->
                LazyVerticalGrid(
                    cells = GridCells.Fixed(5),
                    contentPadding = PaddingValues(4.dp)
                ) {
                    if (viewModel.colorListToDisplay.value.first().isEmpty()){
                        item {
                            IconButton(onClick = { viewModel.updateColorListToDisplay() }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_add_circle_white),
                                    contentDescription = "save_color",
                                )
                            }
                        }
                    }
                    else {
                        // user has colors saved
                        items(viewModel.colorListToDisplay.value.size) { displayedColorIndex ->
                            val colorToUse = Color.Companion.parse(viewModel.colorListToDisplay.value[displayedColorIndex])
                            IconButton(onClick = { viewModel.setCurrentDisplayColor(colorToUse) }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_circle_white),
                                    contentDescription = "saved_color",
                                    tint = colorToUse
                                )
                            }
                        }
                        if(pageIndex + 1 == viewModel.pageCount){
                            if(viewModel.colorListToDisplay.value.size < COLORS_PER_PAGE){
                                item{
                                    IconButton(onClick = { viewModel.updateColorListToDisplay() }) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_add_circle_white),
                                            contentDescription = "save_color",
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if(viewModel.pageCount > 1){
            Row {
                Spacer(
                    modifier = Modifier.width(48.dp)
                )
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    HorizontalPagerIndicator(
                        pagerState = viewModel.pagerState,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(16.dp),
                    )
                }
            }
        }
    }
}



@Preview
@Composable
@InternalCoroutinesApi
@ExperimentalPagerApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
fun ColorPickerPreview(){
    Color_Picker_Content_View(COLOR_PICKER_DATA_PROVIDER())
}