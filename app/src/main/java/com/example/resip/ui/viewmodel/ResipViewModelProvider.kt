package com.example.resip.ui.viewmodel

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.resip.ResipApplication

object ResipViewModelProvider {
    val Factory = viewModelFactory {

        // Initializer for IngredientViewModel
        initializer {
            IngredientsViewModel(ResipApplication().container.ingredientRepository)
        }

        // Initializer for IngredientViewModel
        initializer {
            RecipesViewModel()
        }
    }
    fun CreationExtras.ResipApplication(): ResipApplication =
        (this[AndroidViewModelFactory.APPLICATION_KEY] as ResipApplication)
}