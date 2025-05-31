package com.example.resip.data

import android.content.Context
import com.example.resip.data.database.ingredient.IngredientDatabase
import com.example.resip.data.repository.IngredientRepository
import com.example.resip.model.Ingredient

interface AppContainer{
    val ingredientRepository: IngredientRepository
}

class ResipAppContainer(private val context: Context): AppContainer {
    override val ingredientRepository: IngredientRepository by lazy{
        IngredientRepository(IngredientDatabase.getInventoryDatabase(context).ingredientDao())
    }
}