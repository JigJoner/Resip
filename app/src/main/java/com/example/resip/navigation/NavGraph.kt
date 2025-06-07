package com.example.resip.navigation

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
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
import com.example.resip.ui.viewmodel.ResipViewModelProvider

enum class ResipScreen(
    @StringRes val title: Int,
//    @StringRes val contentDescription: Int,
    val icon: ImageVector
) {
    Login(title = R.string.login, icon = Icons.Rounded.Person),
    HomePage(title = R.string.homepage, icon = Icons.Rounded.Home),
    Recipe(title = R.string.recipe, icon = Icons.Rounded.RestaurantMenu),
    Ingredient(title = R.string.ingredient, icon = Icons.Rounded.Kitchen),
    Browse(title = R.string.browser, icon = Icons.Rounded.Explore),
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

enum class TempNavItems(
    val title: String,
//    @StringRes val contentDescription: Int,
    val icon: ImageVector
){
    Temp(title = "Temp", icon = Icons.Filled.ArrowUpward)
}

@Composable
fun ResipNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    screenType: ScreenType,
    start: String,
    innerPadding: PaddingValues,
    listType: ListTypes = ListTypes.Card
) {
    val focusManager = LocalFocusManager.current
    NavHost(
        navController = navController,
        startDestination = start,
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .clickable(
                // Make sure this doesn't consume clicks from child components like buttons/textfields
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                focusManager.clearFocus() // Clear focus on tap outside
            }
    ) {
        composable(route = ResipScreen.Login.name) {
            Log.d("NAV", "Login")
            LoginScreen(
                modifier = modifier,
                screenType = screenType
            )
        }
        composable(route = ResipScreen.HomePage.name) {
            Log.d("NAV", "HomePage")
            HomePageScreen(
                modifier = modifier,
                screenType = screenType
            )
        }
        composable(route = ResipScreen.Recipe.name) {
            Log.d("NAV", "Recipe")
            RecipesScreen(
                modifier = modifier,
                screenType = screenType
            )
        }
        composable(route = ResipScreen.Ingredient.name) {
            Log.d("NAV", "Ingredient")
            val viewModel: IngredientsViewModel = viewModel(factory = ResipViewModelProvider.Factory)
            IngredientsScreen(
                modifier = modifier,
                screenType = screenType,
                listType = listType,
                viewModel = viewModel
            )
        }
        composable(route = ResipScreen.Browse.name) {
            Log.d("NAV", "Browse")
            BrowseScreen(
                modifier = modifier,
                screenType = screenType
            )
        }
        composable(route = ResipScreen.Setting.name) {
            Log.d("NAV", "Setting")
        }

    }
}