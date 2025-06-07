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
    val isOwnedLoading: Boolean = true,
    val isPreLoading: Boolean = true,
    val error: String? = null,
    val ownedIngredients: List<Ingredient> = emptyList(),
    val preIngredients: List<Ingredient> = emptyList(),
    val popupId: String? = null
)
data class BrowseUiState(
    val isLoading: Boolean = false,
    val error: String? = null
)


