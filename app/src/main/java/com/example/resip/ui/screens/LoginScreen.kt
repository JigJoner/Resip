package com.example.resip.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.compose.ResiPTheme
import com.example.resip.R
import com.example.resip.ResipApp
import com.example.resip.navigation.ResipScreen
import com.example.resip.ui.components.EmailTextfield
import com.example.resip.ui.components.LoginButton
import com.example.resip.ui.components.PasswordTextfield
import com.example.resip.ui.components.ResipLogo
import com.example.resip.ui.components.SignUpButton
import com.example.resip.ui.utils.ScreenType

@Preview
@Composable
fun Previewer() {
    ResiPTheme {
        ResipApp(
            windowWidthSize = WindowWidthSizeClass.Compact,
            windowHeightSize = WindowHeightSizeClass.Medium
        )
    }
}

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    screenType: ScreenType,
    navController: NavController
) {
    when {
        screenType == ScreenType.PORTRAIT_PHONE || screenType == ScreenType.PORTRAIT_TABLET -> {
            LoginScreenPortrait(
                modifier = modifier
                    .fillMaxSize()
                    .padding(dimensionResource(R.dimen.spacing_large))
                    .sizeIn(
                        minWidth = dimensionResource(R.dimen.compact_width_min),
                        minHeight = dimensionResource(R.dimen.medium_height_min)
                    ),
                screenType = screenType,
                navController= navController
            )
        }

        screenType == ScreenType.LANDSCAPE_PHONE || screenType == ScreenType.LANDSCAPE_TABLET -> {
//            LoginScreenLandscape(
//                modifier = modifier
//                    .padding(dimensionResource(R.dimen.spacing_large))
//                    .sizeIn(
//                        minWidth = dimensionResource(R.dimen.compact_width_min),
//                        minHeight = dimensionResource(R.dimen.medium_height_min),
//                        maxHeight = dimensionResource(R.dimen.medium_height_max)
//                    )
//                    .verticalScroll(rememberScrollState()),
//                screenType = screenType,
//                navController= navController
//            )
        }
    }
}

@Composable
fun LoginScreenLandscape(
    modifier: Modifier = Modifier,
    screenType: ScreenType,
    navController: NavController
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ResipLogo(
            modifier = Modifier
                .weight(3f)
                .fillMaxSize(),
            screenType = screenType
        )
        LoginTextSection(
            modifier = Modifier
                .weight(2.5f),
            navController= navController
        )
    }
}

@Composable
fun LoginScreenPortrait(
    modifier: Modifier = Modifier,
    screenType: ScreenType,
    navController: NavController
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ResipLogo(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            screenType = screenType
        )
        LoginTextSection(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            navController= navController
        )
    }
}

@Composable
fun LoginTextSection(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val padding_medium = dimensionResource(R.dimen.spacing_medium)
        val padding_small = dimensionResource(R.dimen.spacing_small)

        EmailTextfield(
            modifier = Modifier
                .padding(top = padding_medium, bottom = padding_small)
                .fillMaxWidth()
        )
        PasswordTextfield(
            modifier = Modifier
                .padding(bottom = padding_medium)
                .fillMaxWidth()
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {

            LoginButton(
                modifier = Modifier
                    .weight(1f),
                onClick = {
                    navController.navigate(route = ResipScreen.HomePage.name)
                }
            )
            Spacer(
                modifier = Modifier
                    .weight(0.2f)
            )
            SignUpButton(
                modifier = Modifier
                    .weight(1f)
            ) { }
        }
    }
}