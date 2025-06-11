package com.example.resip.ui.components

import android.graphics.RenderEffect
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import android.graphics.Shader
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.window.PopupProperties
import androidx.compose.ui.zIndex
import com.example.resip.R


@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun ResipPopup(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    color: Color = MaterialTheme.colorScheme.surface,
    content: @Composable () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    Box( //Overlay
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.6f)) // Optional dimming
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                focusManager.clearFocus()
            }
    )
    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
    ){
        content()
    }

//    Popup(
//        onDismissRequest = onDismiss,
//        alignment = Alignment.Center,
//        properties = PopupProperties(focusable = true) // SO IMPORTANT FOR KEYBOARD TO WORK
//    ) {
//        Box(
//            modifier = modifier,
//            contentAlignment = Alignment.Center,
//        ) {
//            Surface(
//                modifier = modifier,
//                color = color
//            ) {
//                content()
//            }
//        }
//    }
}

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun WarningPopup(
    modifier: Modifier = Modifier,
    text: String,
    onClickConfirm: () -> Unit,
    onClickCancel: () -> Unit
) {
    ResipPopup(
        onDismiss = {},
        modifier = modifier
            .wrapContentSize(),
        color = MaterialTheme.colorScheme.errorContainer
    ) {
        Column() {
            Text(text = text)
            Row() {
                ButtonType1(
                    onClick = onClickConfirm,
                    text = "Confirm"
                )
                ButtonType2(
                    onClick = onClickCancel,
                    text = "Cancel"
                )
            }
        }
    }
}