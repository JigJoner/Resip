package com.example.resip.ui.screens

import androidx.compose.material3.Icon
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.resip.ui.components.ListTypes
import com.example.resip.ui.utils.ScreenType
import com.example.resip.ui.viewmodel.IngredientsUiState
import com.example.resip.ui.viewmodel.IngredientsViewModel
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.example.resip.R
import com.example.resip.model.Ingredient
import com.example.resip.ui.components.CancelButton
import com.example.resip.ui.components.DeleteButton
import com.example.resip.ui.components.LoadingIndicator
import com.example.resip.ui.components.NumberTextField
import com.example.resip.ui.components.ResipPopup
import com.example.resip.ui.components.WarningPopup

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

    var ingredientDetailsPopup by rememberSaveable { mutableStateOf("") }

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
                    viewModel = viewModel,
                    onClick = {ingredientDetailsPopup = item.name}
                )
            }
        }
//       Popups
        val popupItem = ownedList.find { it.name.equals(ingredientDetailsPopup)  }
        if (popupItem != null) {
            IngredientDetailsPopup(
                modifier = Modifier,
                dismissRequest = { ingredientDetailsPopup = "" },
                item = popupItem
            )
        }
        if (uiState.value.addIngredientPopup) {
            AddIngredientPopup(
                viewModel = viewModel
            )
        }

    }
}

@Composable
fun IngredientCard(
    modifier: Modifier = Modifier,
    item: Ingredient,
    viewModel: IngredientsViewModel,
    onClick: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsState()
    Card(
        modifier = modifier
            .padding(dimensionResource(R.dimen.spacing_small))
            .height(dimensionResource(R.dimen.button_height) + dimensionResource(R.dimen.spacing_small) * 2),
        onClick = onClick
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
fun IngredientDetailsPopup(
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
    viewModel: IngredientsViewModel
) {
    var discardSavedIngredientPopupWarning by rememberSaveable { mutableStateOf(false) }
    var dismissAddIngredientPopupWarning by rememberSaveable { mutableStateOf(false) }
    var itemToRemove by rememberSaveable { mutableStateOf("") }

    ResipPopup(
        onDismiss = {dismissAddIngredientPopupWarning = true},
        modifier = modifier
            .padding(dimensionResource(R.dimen.spacing_large))
    ) {
        val selectingIngredient = 0
        val addingPreIngredient = 1
        val addingOwnedIngredient = 2
        var step by rememberSaveable { mutableStateOf(selectingIngredient) }


        val preList by rememberSaveable {mutableStateOf((viewModel.getPreIngredients().map { it -> it.name }))}
        val ownedList by rememberSaveable {mutableStateOf (viewModel.getOwnedIngredients().map { it -> it.name }) }
        Box(
            modifier = Modifier
                .fillMaxSize()
        ){
            AddIngredientDropdownMenu(
                preList = preList,
                ownedList = ownedList,
                onClickMenuItem = { it ->
                    step = when{
                        preList.contains(it) -> addingPreIngredient
                        ownedList.contains(it) -> addingOwnedIngredient
                        else -> selectingIngredient
                    }
                },
                onDeleteMenuItem = { it ->
                    discardSavedIngredientPopupWarning = true
                    itemToRemove = it
                }
            )
            /* CANCEL AND CONFIRM BUTTONS */
            CancelButton(
                onClick = {dismissAddIngredientPopupWarning = true},
                modifier = Modifier
                    .align(Alignment.BottomStart)
            )
            when(step) {
                addingPreIngredient, addingOwnedIngredient -> {
                    IconButton(
                        onClick = {},
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                    ) {
                        Icon(Icons.Filled.Check, contentDescription = "Confirm")
                    }
                }
                else -> {
                }
            }

        }

    }

/* POP UPS */
    if (discardSavedIngredientPopupWarning){
        WarningPopup(
            text = "Discard Saved Ingredient",
            onClickConfirm = {
                discardSavedIngredientPopupWarning = false
                viewModel.deleteOwnedIngredient(itemToRemove)
                itemToRemove = ""
            },
            onClickCancel = { discardSavedIngredientPopupWarning = false }
        )
    }
    if (dismissAddIngredientPopupWarning){
        WarningPopup(
            text = "Discard Unsaved Changes",
            onClickConfirm = {
                dismissAddIngredientPopupWarning = false
                viewModel.removeAddIngredientPopup()
            },
            onClickCancel = { dismissAddIngredientPopupWarning = false }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddIngredientDropdownMenu(
    label: String = "Search",
    preList: List<String>,
    ownedList: List<String>,
    onClickMenuItem: (String) -> Unit,
    onDeleteMenuItem: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var menuExpanded by remember { mutableStateOf(false) }
    var valueInTextfield by rememberSaveable { mutableStateOf("") }
    var filteredPreList by remember { mutableStateOf(preList) }
    var filteredOwnedList by remember { mutableStateOf(ownedList) }

    var isFocused by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(valueInTextfield) {
        filteredPreList = if (valueInTextfield.isEmpty() || preList.contains(valueInTextfield)) {
            listOf()
        } else {
            preList.filter { it.contains(valueInTextfield, ignoreCase = true) }
        }
        filteredOwnedList = if (valueInTextfield.isEmpty() || preList.contains(valueInTextfield)) {
            listOf()
        } else {
            ownedList.filter { it.contains(valueInTextfield, ignoreCase = true) }
        }
    }
    // When valueInTextField changes such that the value isn't in either lists, then the
    // UI needs to reset instead of continuing the addIngredient options
    // Thus, this onClick function needs to be called here
    onClickMenuItem(valueInTextfield)

    val focusManager = LocalFocusManager.current
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.spacing_small))
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                focusManager.clearFocus()
            }
            .height(dimensionResource(R.dimen.textfield_height))
    ) {
        TextField(
            modifier = Modifier
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                    menuExpanded = focusState.isFocused
                },
            value = valueInTextfield,
            onValueChange = { it ->
                valueInTextfield = it
            },
            label = {
                Text(label)
            },
            keyboardOptions = KeyboardOptions.Default,
            shape = if (isFocused) MaterialTheme.shapes.small else MaterialTheme.shapes.medium,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = menuExpanded) }
        )

        // Position of Menu is right below the parent Box Composable
        DropdownMenu(
            modifier = Modifier
                .height(200.dp),
            expanded = menuExpanded,
            properties = PopupProperties(focusable = false),
            onDismissRequest = {
            }
        ) {
            filteredPreList.forEach { name ->
                DropdownMenuItem(
                    text = { Text(name) },
                    onClick = {
                        valueInTextfield = name
                        focusManager.clearFocus()
                        onClickMenuItem(name)
                    }
                )
            }
            HorizontalDivider()
            filteredOwnedList.forEach { name ->
                DropdownMenuItem(
                    text = {
                        Row (
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(name)
                            DeleteButton(
                                onClick = {
                                    onDeleteMenuItem(name)
                                }
                            )
                        }
                    },
                    onClick = {
                        valueInTextfield = name
                        focusManager.clearFocus()
                        onClickMenuItem(valueInTextfield)
                    }
                )
            }
        }
    }
}