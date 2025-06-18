package com.example.resip.ui.screens

import androidx.compose.material3.Icon
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.resip.R
import com.example.resip.model.Ingredient
import com.example.resip.ui.components.CancelButton
import com.example.resip.ui.components.DeleteButton
import com.example.resip.ui.components.LoadingIndicator
import com.example.resip.ui.components.NumberTextField
import com.example.resip.ui.components.ResipPopup
import com.example.resip.ui.components.WarningPopup
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

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
                    onClick = { ingredientDetailsPopup = item.name }
                )
            }
        }
//       Popups
        val popupItem = ownedList.find { it.name.equals(ingredientDetailsPopup) }
        if (popupItem != null) {
            IngredientDetailsPopup(
                modifier = Modifier,
                dismissRequest = { ingredientDetailsPopup = "" },
                item = popupItem
            )
        }
        if (uiState.value.addIngredientMode) {
            AddIngredientPopup(
                viewModel = viewModel
            )
        }
        if (uiState.value.searchIngredientMode) {

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
        onDismiss = { dismissAddIngredientPopupWarning = true },
        modifier = modifier
            .padding(dimensionResource(R.dimen.spacing_large))
    ) {
        val selectingIngredient = 0
        val addingPreIngredient = 1
        val addingOwnedIngredient = 2
        val addingNewIngredient = 3
        var step by rememberSaveable { mutableStateOf(selectingIngredient) }

        val preList by remember  {
            mutableStateOf(
                (viewModel.getPreIngredients().map { it -> it.name })
            )
        }
        val ownedList by remember  {
            mutableStateOf(
                viewModel.getOwnedIngredients().map { it -> it.name })
        }
        var toAddList = rememberSaveable  {
            mutableStateOf(listOf<String>())
        }
        var toIncrementList = rememberSaveable  {
            mutableStateOf(listOf<String>())
        }
        val scope = rememberCoroutineScope()
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column() {
                Text(
                    text = "Add Ingredients",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                AddIngredientDropdownMenu(
                    modifier = Modifier
                        .heightIn(max = dimensionResource(R.dimen.textfield_height) * 5),
                    preList = preList,
                    ownedList = ownedList,
                    onClickMenuItem = { it ->
                        when {
                            preList.contains(it) ->{
                                if (!(toIncrementList.value.contains(it))){
                                    toIncrementList.value = toIncrementList.value + it
                                }
                            }
                            ownedList.contains(it) -> {
                                if (!(toAddList.value.contains(it))){
                                    toAddList.value = toAddList.value + it
                                }
                            }
                        }
                        step = when {
                            preList.contains(it) -> addingPreIngredient
                            ownedList.contains(it) -> addingOwnedIngredient
                            else -> selectingIngredient
                        }
                    },
                    onDeleteMenuItem = { it ->
                        discardSavedIngredientPopupWarning = true
                        itemToRemove = it
                    },
                    onClickNewItem = { step = addingNewIngredient }
                )
                HorizontalDivider(
                    modifier = Modifier
                        .padding(
                            top = dimensionResource(R.dimen.spacing_small),
                            bottom = dimensionResource(R.dimen.spacing_small)
                        ),
                    thickness = 3.dp
                )
                if (toIncrementList.value.isNotEmpty()){
                    Text(
                        text = "Increment Quantity",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                LazyColumn {
                    toIncrementList.value.forEach {
                        item {
                            Row {
                                Text("TEST")
                            }
                        }
                    }
                }
                if (toAddList.value.isNotEmpty()){
                    Text(
                        text = "Add New Ingredient",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                LazyColumn {
                    toAddList.value.forEach {
                        item {
                            Row {
                                Text("TEST")
                            }
                        }
                    }
                }
            }

            /* CANCEL AND CONFIRM BUTTONS */
            CancelButton(
                onClick = { dismissAddIngredientPopupWarning = true },
                modifier = Modifier
                    .align(Alignment.BottomStart)
            )
            when (step) {
                addingPreIngredient, addingOwnedIngredient -> {
                    IconButton(
                        onClick = {},
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                    ) {
                        Icon(Icons.Filled.Check, contentDescription = "Confirm")
                    }
                }

                addingPreIngredient, addingOwnedIngredient -> {
//                    NumberTextField(
//                        modifier = Modifier,
//                        currentQuantity = 0,
//
//                    )
                }

                else -> {
                }
            }
        }
    }

    /* POP UPS */
    if (discardSavedIngredientPopupWarning) {
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
    if (dismissAddIngredientPopupWarning) {
        WarningPopup(
            text = "Discard Unsaved Changes",
            onClickConfirm = {
                dismissAddIngredientPopupWarning = false
                viewModel.addIngredientModeOff()
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
    onClickNewItem: () -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var filteredPreList by remember { mutableStateOf(preList) }
    var filteredOwnedList by remember { mutableStateOf(ownedList) }
    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(searchQuery) {
        filteredPreList = if (searchQuery.isEmpty() || preList.contains(searchQuery)) {
            listOf()
        } else {
            preList.filter { it.contains(searchQuery, ignoreCase = true) }
        }
        filteredOwnedList = if (searchQuery.isEmpty() || preList.contains(searchQuery)) {
            listOf()
        } else {
            ownedList.filter { it.contains(searchQuery, ignoreCase = true) }
        }
    }

    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .onFocusChanged{focusState ->
                if (!focusState.isFocused){
                    expanded = false
                }
            }
    ){
        SearchBar(
            modifier = Modifier
                .align(Alignment.TopCenter),
            inputField = {
                // Customizable input field implementation
                SearchBarDefaults.InputField(
                    modifier = Modifier,
//                        .padding(top = 0.dp)
//                        .height(56.dp)
//                        .heightIn(max = dimensionResource(R.dimen.textfield_height)),
                    query = searchQuery,
                    onQueryChange = {
                        it ->
                        searchQuery = it
                    },
                    onSearch = {
//                        onSearch(query)
                        expanded = false
                    },
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
//                    placeholder = placeholder,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = null
                        )
                        },
//                    trailingIcon = trailingIcon
                )
            },
            expanded = expanded,
            onExpandedChange = { expanded = it },
        ){
            LazyColumn {
                item{
                    ListItem(
                        headlineContent = {
                            Text("Add New")
                        },
                        leadingContent = {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = null
                            )
                        },
                        modifier = Modifier
                            .clickable{
                                focusManager.clearFocus()
                                onClickNewItem()
                            }
                    )
                }
                filteredPreList.forEach { name ->
                    item{
                        ListItem(
                            headlineContent = {
                                Text(name)
                            },
                            modifier = Modifier
                                .clickable{
                                    searchQuery = name
                                    focusManager.clearFocus()
                                    onClickMenuItem(name)
                                }
                        )
                    }
                }
                item{
                    HorizontalDivider()
                }
                filteredOwnedList.forEach { name ->
                    item {
                        ListItem(
                            headlineContent = {
                                Text(name)
                            },
                            trailingContent = {
                                DeleteButton(
                                    onClick = {
                                        onDeleteMenuItem(name)
                                    }
                                )
                            },
                            modifier = Modifier
                                .clickable {
                                    searchQuery = name
                                    focusManager.clearFocus()
                                    onClickMenuItem(searchQuery)
                                }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SearchIngredient(

) {

}