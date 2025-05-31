package com.example.resip.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.resip.ui.components.CardList
import com.example.resip.ui.components.ListTypes
import com.example.resip.ui.utils.ScreenType

@Composable
fun RecipesScreen(
    modifier: Modifier = Modifier,
    screenType: ScreenType
){
    when {
        screenType == ScreenType.PORTRAIT_PHONE || screenType == ScreenType.PORTRAIT_TABLET -> {
            RecipesScreenPortrait(
                modifier = modifier
            )
        }
        screenType == ScreenType.LANDSCAPE_PHONE || screenType == ScreenType.LANDSCAPE_TABLET -> {

        }
    }
}

@Composable
fun RecipesScreenPortrait(
    modifier: Modifier = Modifier,
){
    CardList(
        listType = ListTypes.Detailed,
        isRecipe = true,
    )
}