package com.example.resip.ui.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.example.resip.R
import kotlin.math.exp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchableDropDownMenu(
    label: String = "Search",
    list: List<String>,
    list2: List<String>,
    onClick: (String) -> Unit,
    onDelete: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    var text by rememberSaveable { mutableStateOf("") }
    var filteredOptions1 by remember { mutableStateOf(list) }
    var filteredOptions2 by remember { mutableStateOf(list2) }
    var isFocused by rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(text) {
        filteredOptions1 = if (text.isEmpty() || list.contains(text)) {
            listOf()
        } else {
            list.filter { it.contains(text, ignoreCase = true) }
        }
        filteredOptions2 = if (text.isEmpty() || list.contains(text)) {
            listOf()
        } else {
            list2.filter { it.contains(text, ignoreCase = true) }
        }
    }
    onClick(text)
    val focusManager = LocalFocusManager.current
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.spacing_small))
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                focusManager.clearFocus()
            }
            .height(dimensionResource(R.dimen.textfield_height))
    ) {
        TextField(
            modifier = Modifier
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                    expanded = focusState.isFocused
                },
            value = text,
            onValueChange = { it: String ->
                text = it
            },
            enabled = true,
            readOnly = false,
            label = {
                Text(label)
            },
//            interactionSource = interactionSource,
            keyboardOptions = KeyboardOptions.Default,
            shape = if (isFocused) MaterialTheme.shapes.small else MaterialTheme.shapes.medium,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
        )

        DropdownMenu(
            modifier = Modifier
                .height(160.dp),
            expanded = expanded,
            properties = PopupProperties(focusable = false),
            onDismissRequest = {
            }
        ) {
            filteredOptions1.forEach { name ->
                DropdownMenuItem(
                    text = { Text(name) },
                    onClick = {
                        text = name
                        focusManager.clearFocus()
                        onClick(name)
                    }
                )
            }
            HorizontalDivider()
            filteredOptions2.forEach { name ->
                DropdownMenuItem(
                    text = {
                        Row (
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(name)
                            IconButton(
                                onClick = {
                                    onDelete(name)
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete item"
                                )
                            }
                        }
                    },
                    onClick = {
                        text = name
                        focusManager.clearFocus()
                        onClick(text)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Demo_SearchableExposedDropdownMenuBox() {
    val context = LocalContext.current
    val coffeeDrinks = arrayOf("Americano", "Cappuccino", "Espresso", "Latte", "Mocha")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp)
            .background(Color.Blue)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = selectedText,
                onValueChange = { selectedText = it },
                label = { Text(text = "Start typing the name of the coffee") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor()
            )

            val filteredOptions =
                coffeeDrinks.filter { it.contains(selectedText, ignoreCase = true) }
            if (filteredOptions.isNotEmpty()) {
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        // We shouldn't hide the menu when the user enters/removes any character
                    }
                ) {
                    filteredOptions.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(text = item) },
                            onClick = {
                                selectedText = item
                                expanded = false
                                Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                }
            }
        }
    }
}