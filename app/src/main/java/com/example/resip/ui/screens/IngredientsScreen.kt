package com.example.resip.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.resip.ui.components.ListTypes
import com.example.resip.ui.utils.ScreenType
import com.example.resip.ui.viewmodel.IngredientsUiState
import com.example.resip.ui.viewmodel.IngredientsViewModel
import com.example.resip.ui.viewmodel.ResipViewModelProvider
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.example.resip.R
import com.example.resip.model.Ingredient
import com.example.resip.ui.components.LoadingIndicator
import com.example.resip.ui.components.NumberTextField
import kotlin.math.max

@Composable
fun IngredientsScreen(
    modifier: Modifier = Modifier,
    screenType: ScreenType,
    viewModel: IngredientsViewModel,
    listType: ListTypes = ListTypes.Card
){
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

@Composable
fun IngredientsScreenPortrait(
    modifier: Modifier = Modifier,
    uiState: State<IngredientsUiState>,
    viewModel: IngredientsViewModel,
    listType: ListTypes = ListTypes.Card
){
    IngredientsList(
        listType = listType,
        viewModel = viewModel
    )
}

@Composable
fun IngredientsList(
    modifier: Modifier = Modifier.Companion,
    listType: ListTypes = ListTypes.Card,
    viewModel: IngredientsViewModel,
) {
    val preList = viewModel.getPreIngredients()
    var ownedList = viewModel.getOwnedIngredients()
    val uiState = viewModel.uiState.collectAsState()
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
                val uiState = viewModel.uiState.collectAsState()
                if (uiState.value.popupId.equals(item.name)){
                    IngredientPopup (
                        onDismissRequest = {viewModel.removePopup()},
                        item = item
                    )
                }
            }
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
            if (uiState.value.popupId == null){
                viewModel.loadPopup(item.name)
            }
        }
    ) {

        val onValueChange: suspend (Int) -> Unit = {newQuantity -> viewModel.updateQuantity(newQuantity, item)}
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
    onDismissRequest: () -> Unit,
    item: Ingredient
){
    Popup(
        alignment = Alignment.Center,
        onDismissRequest = onDismissRequest,
    ){
        Surface(
            modifier = modifier
                .fillMaxWidth(0.8f)
                .aspectRatio(1f),
            tonalElevation = 8.dp,
            shadowElevation = 8.dp
        ) {
            Text(item.name)
        }

    }
}