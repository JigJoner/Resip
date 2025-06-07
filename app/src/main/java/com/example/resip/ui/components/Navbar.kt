package com.example.resip.ui.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.resip.R
import com.example.resip.navigation.NavBarItems

@Composable
fun ResipNavBar(
    modifier: Modifier = Modifier,
    selectedRoute: MutableState<Int>,
    navController: NavController,
    windowInsets: WindowInsets,
) {
    NavigationBar(
        modifier = Modifier,
        windowInsets = windowInsets
    ) {
        NavBarItems.entries.forEachIndexed { index, destination ->
            NavigationBarItem(
                selected = selectedRoute.value == index,
                onClick = {
                    navController.navigate(route = destination.name)
                    selectedRoute.value = index
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResipTopBar(
    windowInsets: WindowInsets,
    modifier: Modifier = Modifier,
    titleContent: @Composable () -> Unit,
    navIconContent: (@Composable () -> Unit)? = null,
    actionsContent: (@Composable () -> Unit)? = null
){
    TopAppBar(
        navigationIcon = { navIconContent?.invoke() },
        actions = { actionsContent?.invoke() },
        modifier = modifier
//            .height(dimensionResource(R.dimen.topbar_height))
            .windowInsetsPadding(windowInsets),
        title = titleContent,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
    )
}
