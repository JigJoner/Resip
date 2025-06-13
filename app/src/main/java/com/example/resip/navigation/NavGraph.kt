package com.example.resip.navigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.rounded.Explore
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Kitchen
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.RestaurantMenu
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.resip.R
import com.example.resip.ui.components.ListTypes
import com.example.resip.ui.screens.BrowseScreen
import com.example.resip.ui.screens.HomePageScreen
import com.example.resip.ui.screens.IngredientsScreen
import com.example.resip.ui.screens.LoginScreen
import com.example.resip.ui.screens.RecipesScreen
import com.example.resip.ui.utils.ScreenType
import com.example.resip.ui.viewmodel.IngredientsViewModel
import com.example.resip.ui.viewmodel.ResipViewModels

enum class ResipScreen(
    @StringRes val title: Int,
//    @StringRes val contentDescription: Int,
    val icon: ImageVector
) {
    HomePage(title = R.string.homepage, icon = Icons.Rounded.Home),
    Recipe(title = R.string.recipe, icon = Icons.Rounded.RestaurantMenu),
    Ingredient(title = R.string.ingredient, icon = Icons.Rounded.Kitchen),
    Browse(title = R.string.browser, icon = Icons.Rounded.Explore),
    Login(title = R.string.login, icon = Icons.Rounded.Person),
    Setting(title = R.string.setting, icon = Icons.Rounded.Settings)
}

enum class NavBarItems(
    @StringRes val title: Int,
//    @StringRes val contentDescription: Int,
    val icon: ImageVector
) {
    HomePage(title = R.string.homepage, icon = Icons.Rounded.Home),
    Recipe(title = R.string.recipe, icon = Icons.Rounded.RestaurantMenu),
    Ingredient(title = R.string.ingredient, icon = Icons.Rounded.Kitchen),
    Browse(title = R.string.browser, icon = Icons.Rounded.Explore)
}


@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun ResipNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    screenType: ScreenType,
    startDestination: String,
    listType: ListTypes = ListTypes.Card,
    viewModels: ResipViewModels,
) {

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier

    ) {
        composable(route = ResipScreen.Login.name) {
            LoginScreen(
                screenType = screenType,
                navController = navController
            )
        }
        composable(route = ResipScreen.HomePage.name) {
            HomePageScreen(
                modifier = Modifier,
                screenType = screenType
            )
        }
        composable(route = ResipScreen.Recipe.name) {
            RecipesScreen(
                modifier = Modifier,
                screenType = screenType
            )
        }
        composable(route = ResipScreen.Ingredient.name) {
            IngredientsScreen(
                modifier = Modifier,
                screenType = screenType,
                listType = listType,
                viewModel = viewModels.ingredientsViewModel
            )
        }
        composable(route = ResipScreen.Browse.name) {
            BrowseScreen(
                modifier = Modifier,
                screenType = screenType
            )
        }
        composable(route = ResipScreen.Setting.name) {
        }

    }
}