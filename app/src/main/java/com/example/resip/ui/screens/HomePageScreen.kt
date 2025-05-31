package com.example.resip.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import coil3.request.colorSpace
import com.example.resip.ui.components.CardList
import com.example.resip.ui.components.ListTypes
import com.example.resip.ui.utils.ScreenType


@Composable
fun HomePageScreen(
    modifier:Modifier = Modifier,
    screenType: ScreenType
){
    when {
        screenType == ScreenType.PORTRAIT_PHONE || screenType == ScreenType.PORTRAIT_TABLET -> {
            HomePageScreenPortrait(
                modifier = modifier
            )
        }
        screenType == ScreenType.LANDSCAPE_PHONE || screenType == ScreenType.LANDSCAPE_TABLET -> {

        }
    }
}

@Composable
fun HomePageScreenPortrait(
    modifier: Modifier = Modifier
){
    CardList(
        listType = ListTypes.Card,
        isRecipe = true,
    )
}