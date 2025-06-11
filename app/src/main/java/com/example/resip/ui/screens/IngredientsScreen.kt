package com.example.resip.ui.screens

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.resip.ui.components.ListTypes
import com.example.resip.ui.utils.ScreenType
import com.example.resip.ui.viewmodel.IngredientsUiState
import com.example.resip.ui.viewmodel.IngredientsViewModel
import com.example.resip.ui.viewmodel.ResipViewModelProvider
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.zIndex
import com.example.resip.R
import com.example.resip.model.Ingredient
import com.example.resip.ui.components.ButtonType1
import com.example.resip.ui.components.ButtonType2
import com.example.resip.ui.components.Demo_SearchableExposedDropdownMenuBox
import com.example.resip.ui.components.LoadingIndicator
import com.example.resip.ui.components.NumberTextField
import com.example.resip.ui.components.ResipPopup
import com.example.resip.ui.components.SearchableDropDownMenu
import com.example.resip.ui.components.WarningPopup
import kotlinx.coroutines.CoroutineScope
import okhttp3.Dispatcher
import kotlin.math.max

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun IngredientsScreen(
    modifier: Modifier = Modifier,
    screenType: ScreenType,
    viewModel: IngredientsViewModel,
    listType: ListTypes = ListTypes.Card
) {
    val uiState = viewModel.uiState.collectAsState()
    when {
        screenType == ScreenType.PORTRAIT_PHONE || screenType == ScreenType.PORTRAIT_TABLET -> {
            IngredientsScreenPortrait(
                modifier = modifier,
                listType = listType,
                uiState = uiState,
                viewModel = viewModel
            )
        }

        screenType == ScreenType.LANDSCAPE_PHONE || screenType == ScreenType.LANDSCAPE_TABLET -> {

        }
    }
}

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun IngredientsScreenPortrait(
    modifier: Modifier = Modifier,
    uiState: State<IngredientsUiState>,
    viewModel: IngredientsViewModel,
    listType: ListTypes = ListTypes.Card
) {
    IngredientsList(
        listType = listType,
        viewModel = viewModel
    )
}

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun IngredientsList(
    modifier: Modifier = Modifier.Companion,
    listType: ListTypes = ListTypes.Card,
    viewModel: IngredientsViewModel,
) {
    val preList = viewModel.getPreIngredients()
    var ownedList = viewModel.getOwnedIngredients()
    val uiState = viewModel.uiState.collectAsState()
    var warningPopup by rememberSaveable { mutableStateOf(false) }
    if (uiState.value.isOwnedLoading) {
        LoadingIndicator()
    } else {
        LazyVerticalGrid(
            modifier = modifier
                .fillMaxWidth(),
            columns = GridCells.Fixed(1)
        ) {
            items(ownedList) { item ->
                IngredientCard(
                    item = item,
                    viewModel = viewModel
                )
            }
        }
        val popupItem = ownedList.find { it.name == uiState.value.popupId }
        if (popupItem != null) {
            IngredientPopup(
                modifier = Modifier,
                dismissRequest = { viewModel.removePopup() },
                item = popupItem
            )
        }
        if (uiState.value.addIngredientPopup) {
            AddIngredientPopup(
                dismissRequest = {
                    warningPopup = true
                },
                viewModel = viewModel
            )
//            Demo_SearchableExposedDropdownMenuBox()
        }
        if (warningPopup) {
            WarningPopup(
                text = "Discard Changes",
                onClickConfirm = {
                    warningPopup = false
                    viewModel.removeAddIngredientPopup()
                },
                onClickCancel = { warningPopup = false }
            )
        }
    }
}

@Composable
fun IngredientCard(
    modifier: Modifier = Modifier,
    item: Ingredient,
    viewModel: IngredientsViewModel,
) {
    val uiState = viewModel.uiState.collectAsState()
    Card(
        modifier = modifier
            .padding(dimensionResource(R.dimen.spacing_small))
            .height(dimensionResource(R.dimen.button_height) + dimensionResource(R.dimen.spacing_small) * 2),
        onClick = {
            if (uiState.value.popupId == null) {
                viewModel.loadPopup(item.name)
            }
        }
    ) {

        val onValueChange: suspend (Int) -> Unit =
            { newQuantity -> viewModel.updateQuantity(newQuantity, item) }
        Row(
        ) {
            Row(
                modifier = Modifier.weight(0.5f),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    imageVector = Icons.Filled.Image, //Temp
                    contentDescription = null,
                    modifier = Modifier
                        .height(dimensionResource(R.dimen.image_width))
                        .aspectRatio(1f)
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth(0.8f),
                    text = item.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    softWrap = true,

                    )
            }
            Row(
                modifier = Modifier
                    .weight(0.5f),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                NumberTextField(
                    currentQuantity = item.quantity.toString(),
                    onValueChange = onValueChange,
                    trailingIcon = (item.unit).unit
                )
            }
        }
    }
}

@Composable
fun IngredientPopup(
    modifier: Modifier = Modifier,
    dismissRequest: () -> Unit,
    item: Ingredient
) {
    ResipPopup(
        onDismiss = dismissRequest,
        modifier = modifier
            .fillMaxWidth(0.8f)
            .aspectRatio(1f)
    ) {
        Text(text = item.name)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun AddIngredientPopup(
    modifier: Modifier = Modifier,
    dismissRequest: () -> Unit,
    viewModel: IngredientsViewModel
) {
    var showAlert by rememberSaveable { mutableStateOf(false) }
    var itemToRemove by rememberSaveable { mutableStateOf("") }
    ResipPopup(
        onDismiss = dismissRequest,
        modifier = modifier
            .padding(dimensionResource(R.dimen.spacing_large))
    ) {
        var step by rememberSaveable { mutableStateOf(1) }

        val list by rememberSaveable {mutableStateOf((viewModel.getPreIngredients().map { it -> it.name }))}
        val list2 by rememberSaveable {mutableStateOf (viewModel.getOwnedIngredients().map { it -> it.name }) }

        when(step) {
            1 -> {
                SearchableDropDownMenu(
                    list = list,
                    list2 = list2,

                    onClick = { it ->
                        if (list.contains(it)) {

                        } else {

                        }
                    },
                    onDelete = { it ->
                        showAlert = true
                        itemToRemove = it
                    }
                )
            }
        }
    }
    if (showAlert){
        WarningPopup(
            text = "Discard Saved Ingredient",
            onClickConfirm = {
                showAlert = false
                viewModel.deleteOwnedIngredient(itemToRemove)
                itemToRemove = ""
            },
            onClickCancel = { showAlert = false }
        )
    }
}
