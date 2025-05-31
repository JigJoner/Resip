package com.example.resip

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.resip.navigation.NavBarItems
import com.example.resip.navigation.ResipNavHost
import com.example.resip.navigation.ResipScreen
import com.example.resip.ui.components.ListTypes
import com.example.resip.ui.utils.ScreenType
import com.example.resip.ui.viewmodel.IngredientsViewModel
import com.example.resip.ui.viewmodel.LoginViewModel
import com.example.resip.ui.viewmodel.ResipViewModelProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResipApp(
    modifier: Modifier = Modifier,
    windowWidthSize: WindowWidthSizeClass,
    windowHeightSize: WindowHeightSizeClass
) {
    var screenType: ScreenType = ScreenType.PORTRAIT_PHONE
    when {
        windowWidthSize == WindowWidthSizeClass.Compact -> {
            screenType = ScreenType.PORTRAIT_PHONE
        }

        windowWidthSize == WindowWidthSizeClass.Medium && windowHeightSize == WindowHeightSizeClass.Compact -> {
            screenType = ScreenType.LANDSCAPE_PHONE
        }

        windowWidthSize == WindowWidthSizeClass.Medium -> {
            screenType = ScreenType.PORTRAIT_TABLET
        }

        windowWidthSize == WindowWidthSizeClass.Expanded -> {
            screenType = ScreenType.LANDSCAPE_TABLET
        }

        else -> {
        }
    }

    val navController = rememberNavController()
    val startRoute = ResipScreen.Login

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    var selectedRoute by rememberSaveable { mutableStateOf(startRoute.ordinal) }
    var listType: ListTypes = ListTypes.Card
//    var viewModel: ViewModel = viewModel(factory = ResipViewModelProvider.Factory) as IngredientsViewModel
//    when (selectedRoute) {
//        ResipScreen.Login.ordinal -> {
//        }
//
//        ResipScreen.HomePage.ordinal -> {
//
//        }
//
//        ResipScreen.Recipe.ordinal -> {
//        }
//
//        ResipScreen.Ingredient.ordinal -> {
////            viewModel = viewModel(factory = ResipViewModelProvider.Factory)
//        }
//    }

    Scaffold(
        bottomBar = {
            if (!(currentRoute.equals(ResipScreen.Login.name))) {
                NavigationBar(
                    modifier = Modifier,
                    windowInsets = WindowInsets.navigationBars
                ) {
                    NavBarItems.entries.forEachIndexed { index, destination ->
                        NavigationBarItem(
                            selected = selectedRoute == index,
                            onClick = {
                                navController.navigate(route = destination.name)
                                selectedRoute = index
                            },
                            icon = {
                                Icon(
                                    imageVector = destination.icon,
                                    contentDescription = "test"
                                )
                            },
                            label = {
                                Text(
                                    text = destination.name
                                )
                            }
                        )
                    }
                }
            } else { // TEMPORARY
                NavigationBar(
                    modifier = Modifier,
                    windowInsets = WindowInsets.navigationBars
                ) {
                    var temp by rememberSaveable { mutableStateOf(false) }
                    NavigationBarItem(
                        selected = temp,
                        onClick = {
                            navController.navigate(route = ResipScreen.HomePage.name)
                            temp = true
                        },
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.ArrowUpward,
                                contentDescription = "test"
                            )
                        },
                        label = {
                            Text(
                                text = "Temporary"
                            )
                        }
                    )
                }
            }
        },
        topBar = {
            if (!(currentRoute.equals(ResipScreen.Login.name))) {
                TopAppBar(
                    modifier = Modifier
                        .height(96.dp),
                    windowInsets = WindowInsets.navigationBars,
                    title = {
                        Text("Test")
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                )
            }
        }
    ) { innerPadding ->
        ResipNavHost(
            navController = navController,
            screenType = screenType,
            innerPadding = innerPadding,
            start = startRoute.name,
            listType = listType
        )
    }
}