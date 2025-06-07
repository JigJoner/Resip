package com.example.resip

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
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
import com.example.resip.ui.components.ResipIconButton
import com.example.resip.ui.components.ResipNavBar
import com.example.resip.ui.components.ResipTopBar
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

    // This is the state of the current Screen.
    // Updates when pushing entries, but not popping
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    var selectedRoute = rememberSaveable { mutableStateOf(startRoute.ordinal) }
    var listType: ListTypes = ListTypes.Card

    Scaffold(
        bottomBar = {
            if (!(currentRoute.equals(ResipScreen.Login.name))) {
                ResipNavBar(
                    selectedRoute = selectedRoute,
                    navController = navController,
                    windowInsets = WindowInsets.navigationBars
                )
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
            var actionsContent: (@Composable () -> Unit)? = null
            var titleContent: @Composable () -> Unit = {}
            when (currentRoute){
                ResipScreen.Ingredient.name ->{
                    titleContent = {
                        Text(ResipScreen.Ingredient.name)
                    }
                    actionsContent = {
                        Row(
                            modifier = Modifier
                        ){
                            ResipIconButton(
                                onClick = {

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
                else ->{

                }
            }
            ResipTopBar(
                modifier = Modifier,
                windowInsets = WindowInsets.navigationBars,
                titleContent = titleContent,
                navIconContent = {
                    IconButton(onClick = {
                        // Popping the navstack doesn't update the currentState State
                        navController.popBackStack()
                        // Thus, you need to use navController?.currentBackStackEntry?.destination?.route manually
                        selectedRoute.value = NavBarItems.valueOf(navController?.currentBackStackEntry?.destination?.route ?: ResipScreen.HomePage.name).ordinal
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actionsContent = actionsContent
            )
            //
//            if (!(currentRoute.equals(ResipScreen.Login.name))) {
//                TopAppBar(
//                    modifier = Modifier
//                        .height(96.dp),
//                    windowInsets = WindowInsets.navigationBars,
//                    title = {
//                        Text("Test")
//                    },
//                    colors = TopAppBarDefaults.topAppBarColors(
//                        containerColor = MaterialTheme.colorScheme.primaryContainer,
//                        titleContentColor = MaterialTheme.colorScheme.primary,
//                    ),
//                )
//            }
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