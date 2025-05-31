package com.example.resip.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.resip.R
import com.example.ui.theme.displayFontFamily

@Composable
fun NumberTextField(
    modifier: Modifier = Modifier,
    label: String = stringResource(R.string.placeholder)
){
    var text by rememberSaveable { mutableStateOf("") }
    var isFocused by rememberSaveable { mutableStateOf(false) }

    TextField(
        value = text,
        onValueChange = { text = it },
        label = { Text(
            text = label,
            style = MaterialTheme.typography.labelSmall
        ) },
        modifier = modifier.onFocusChanged { focusState ->
            isFocused = focusState.isFocused
        },
        shape = if (isFocused) MaterialTheme.shapes.small else MaterialTheme.shapes.medium,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}

@Composable
fun EmailTextfield(
    modifier: Modifier = Modifier,
    label: String = stringResource(R.string.email)
) {
    var text by rememberSaveable { mutableStateOf("") }
    var isFocused by rememberSaveable { mutableStateOf(false) }

    TextField(
        value = text,
        onValueChange = { text = it },
        label = { Text(
            text = label,
            style = MaterialTheme.typography.labelLarge
        ) },
        modifier = modifier.onFocusChanged { focusState ->
            isFocused = focusState.isFocused
        },
        shape = if (isFocused) MaterialTheme.shapes.small else MaterialTheme.shapes.medium
    )
}

@Composable
fun PasswordTextfield(
    modifier: Modifier = Modifier, label: String = stringResource(R.string.password)
) {
    var text by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var isFocused by rememberSaveable { mutableStateOf(false) }
    TextField(
        modifier = modifier.onFocusChanged { focusState ->
            isFocused = focusState.isFocused
        },
        value = text,
        onValueChange = { text = it },
        label = { Text(
            text = label,
            style = MaterialTheme.typography.labelLarge
        ) },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        trailingIcon = {
            val image = if (passwordVisible) Icons.Filled.Visibility
            else Icons.Filled.VisibilityOff

            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(
                    imageVector = image, contentDescription = if (passwordVisible) {
                        stringResource(R.string.content_description_hide_password)
                    } else {
                        stringResource(R.string.content_description_show_password)
                    }
                )
            }
        },
        shape = if (isFocused) MaterialTheme.shapes.small else MaterialTheme.shapes.medium

    )
}