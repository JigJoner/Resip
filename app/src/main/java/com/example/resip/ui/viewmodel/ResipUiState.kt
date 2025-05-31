package com.example.resip.ui.viewmodel

import com.example.resip.model.Ingredient
import com.example.resip.ui.components.ListTypes

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)

data class HomePageUiState(
    val isLoading: Boolean = false,
    val error: String? = null
)
data class RecipesUiState(
    val isLoading: Boolean = false,
    val error: String? = null
)
data class IngredientsUiState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val ingredients: List<Ingredient> = emptyList()
)
data class BrowseUiState(
    val isLoading: Boolean = false,
    val error: String? = null
)


