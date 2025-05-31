package com.example.resip.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.resip.ui.components.ListTypes
import com.example.resip.ui.utils.ScreenType
import com.example.resip.ui.viewmodel.IngredientsUiState
import com.example.resip.ui.viewmodel.IngredientsViewModel
import com.example.resip.ui.viewmodel.ResipViewModelProvider
import kotlinx.coroutines.flow.StateFlow
import androidx.compose.runtime.State
import com.example.resip.ui.components.IngredientsList

@Composable
fun IngredientsScreen(
    modifier: Modifier = Modifier,
    screenType: ScreenType,
    viewModel: IngredientsViewModel = viewModel(factory = ResipViewModelProvider.Factory),
    listType: ListTypes = ListTypes.Card
){
    val uiState = viewModel.uiState.collectAsState()
    when {
        screenType == ScreenType.PORTRAIT_PHONE || screenType == ScreenType.PORTRAIT_TABLET -> {
            IngredientsScreenPortrait(
                modifier = modifier,
                listType = listType,
                uiState = uiState
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
    listType: ListTypes = ListTypes.Card
){
    IngredientsList(
        listType = listType,
        list = uiState.value.ingredients
    )
}