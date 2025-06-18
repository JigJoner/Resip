package com.example.resip

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalFocusManager
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.resip.navigation.NavBarItems
import com.example.resip.navigation.ResipNavHost
import com.example.resip.navigation.ResipScreen
import com.example.resip.ui.components.ResipIconButton
import com.example.resip.ui.components.ResipNavBar
import com.example.resip.ui.components.ResipTopBar
import com.example.resip.ui.utils.ScreenType
import com.example.resip.ui.viewmodel.ResipViewModelProvider
import com.example.resip.ui.viewmodel.ResipViewModels

@RequiresApi(Build.VERSION_CODES.S)
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

    // State of Navbar Selected Visual Effect
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
                                    viewModels.ingredientsViewModel.searchIngredientModeOn()
                                },
                                icon = Icons.Filled.Search,
                                contentDescription = null
                            )
                            ResipIconButton(
                                onClick = {
                                    viewModels.ingredientsViewModel.addIngredientModeOn()
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
                    .alpha(isShow),
                titleContent = titleContent,
                navIconContent = {
                    IconButton(onClick = {
                        // Popping the navstack doesn't update the currentState State
                        val canGoBack = navController.previousBackStackEntry != null
                                && navController.previousBackStackEntry?.destination?.route != ResipScreen.Login.name
                        if (canGoBack) {
                            navController.popBackStack()
                            selectedRoute.value = ResipScreen.valueOf(
                                navController.currentBackStackEntry?.destination?.route
                                    ?: ResipScreen.Login.name
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
        val focusManager = LocalFocusManager.current
        ResipNavHost(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
                .clickable(
                    // Make sure this doesn't consume clicks from child components like buttons/textfields
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    focusManager.clearFocus() // Clear focus on tap outside
                },
            navController = navController,
            screenType = screenType,
            startDestination = ResipScreen.Login.name,
            viewModels = viewModels,
        )
        BackHandler {
            val currentBackStackEntry = navController.currentBackStackEntry
            val canGoBack = navController.previousBackStackEntry != null
                    && navController.previousBackStackEntry?.destination?.route != ResipScreen.Login.name
            if (canGoBack) {
                navController.popBackStack()
                selectedRoute.value = ResipScreen.valueOf(
                    navController.currentBackStackEntry?.destination?.route
                        ?: ResipScreen.Login.name
                ).ordinal
            }
        }
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