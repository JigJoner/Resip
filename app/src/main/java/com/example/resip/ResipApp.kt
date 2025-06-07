package com.example.resip

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.resip.navigation.NavBarItems
import com.example.resip.navigation.ResipNavHost
import com.example.resip.navigation.ResipScreen
import com.example.resip.ui.components.ListTypes
import com.example.resip.ui.components.ResipIconButton
import com.example.resip.ui.components.ResipNavBar
import com.example.resip.ui.components.ResipTopBar
import com.example.resip.ui.utils.ScreenType
import com.example.resip.ui.viewmodel.IngredientsViewModel
import com.example.resip.ui.viewmodel.LoginViewModel
import com.example.resip.ui.viewmodel.ResipViewModelProvider
import com.example.resip.ui.viewmodel.ResipViewModels

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResipApp(
    modifier: Modifier = Modifier,
    windowWidthSize: WindowWidthSizeClass,
    windowHeightSize: WindowHeightSizeClass
) {
    /* SCREEN TYPE */
    val screenType = getScreenType(windowWidthSize, windowHeightSize)

    /* NAV CONTROLLER */
    val navController = rememberNavController()

    /* VIEW MODELS */
    val viewModels: ResipViewModels = ResipViewModels(
        ingredientsViewModel = viewModel(factory = ResipViewModelProvider.Factory),
        recipesViewModel = viewModel(factory = ResipViewModelProvider.Factory)
    )


    // This is the state of the current Screen.
    // Updates when pushing entries, but not popping
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route


    val startRoute = NavBarItems.HomePage
    var selectedRoute = rememberSaveable { mutableStateOf(startRoute.ordinal) }

    val isShow = if (currentRoute.equals(ResipScreen.Login.name)) 0f else 1f
    Scaffold(
        bottomBar = {
            ResipNavBar(
                modifier = Modifier
                    .alpha(isShow),
                selectedRoute = selectedRoute,
                navController = navController
            )
        },
        topBar = {
            var actionsContent: (@Composable () -> Unit)? = null
            var titleContent: @Composable () -> Unit = {}
            when (currentRoute) {
                ResipScreen.Ingredient.name -> {
                    titleContent = {
                        Text(ResipScreen.Ingredient.name)
                    }
                    actionsContent = {
                        Row(
                            modifier = Modifier
                        ) {
                            ResipIconButton(
                                onClick = {
                                    viewModels.ingredientsViewModel
                                },
                                icon = Icons.Filled.Add,
                                contentDescription = null
                            )
                            ResipIconButton(
                                onClick = {

                                },
                                icon = Icons.Filled.Delete,
                                contentDescription = null
                            )

                        }
                    }
                }

                else -> {

                }
            }
            ResipTopBar(
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.navigationBars)
                    .alpha(isShow),
                titleContent = titleContent,
                navIconContent = {
                    IconButton(onClick = {
                        // Popping the navstack doesn't update the currentState State
                        navController.popBackStack()

                        // Thus, you need to use navController?.currentBackStackEntry?.destination?.route manually
                        if (!navController?.currentBackStackEntry?.destination?.route.equals(
                                ResipScreen.Login.name
                            )
                        ) {
                            selectedRoute.value = NavBarItems.valueOf(
                                navController?.currentBackStackEntry?.destination?.route
                                    ?: ResipScreen.HomePage.name
                            ).ordinal
                        }
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actionsContent = actionsContent
            )

        }
    ) { innerPadding ->
        ResipNavHost(
            modifier = modifier
                .padding(innerPadding),
            navController = navController,
            screenType = screenType,
            startDestination = ResipScreen.Login.name,
            viewModels = viewModels,
        )
    }
}

fun getScreenType(
    windowWidthSize: WindowWidthSizeClass,
    windowHeightSize: WindowHeightSizeClass
): ScreenType {
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
    return screenType
}