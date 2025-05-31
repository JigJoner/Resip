package com.example.resip.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.resip.R
import com.example.resip.ui.utils.ScreenType
import com.example.ui.theme.displayFontFamily


@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun ResipLogo(
    modifier: Modifier = Modifier,
    screenType: ScreenType
) {
    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        val parentWidth = maxWidth
        val fontSize = (parentWidth.value / 3).sp
        Text(
            text = stringResource(R.string.app_name),
            style = TextStyle(
                fontFamily = displayFontFamily,
                fontWeight = FontWeight.Normal,
                letterSpacing = 3.sp,
                textAlign = TextAlign.Center,
                fontSize = fontSize
            )
        )
    }
}