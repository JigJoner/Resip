package com.example.resip.ui.components

import android.util.Log
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.example.resip.R


private const val TAG = "Button Activity"

@Composable
fun ButtonType1(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String = "default"
) {
    Button(
        modifier = modifier,
        enabled = true,
        onClick = onClick
    ) { Text(text = text) }
}

@Composable
fun ButtonType2(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String = "default"
) {
    OutlinedButton(
        modifier = modifier,
        enabled = true,
        onClick = onClick
    ) { Text(text = text) }
}

@Composable
fun LoginButton(
    modifier: Modifier = Modifier,
    text: String = stringResource(R.string.login),
    onClick: () -> Unit = {Log.d(TAG, "Login Pressed")}
) {
    Button(
        modifier = modifier,
        enabled = true,
        onClick = onClick
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun SignUpButton(
    modifier: Modifier = Modifier,
    text: String = stringResource(R.string.signup),
    onClick: () -> Unit = {Log.d(TAG, "SignUp Pressed")}
) {
    OutlinedButton(
        modifier = modifier,
        enabled = true,
        onClick = onClick
    ) {
        Text(
            style = MaterialTheme.typography.labelLarge,
            text = text,
            overflow = TextOverflow.Ellipsis
        )
    }
}
