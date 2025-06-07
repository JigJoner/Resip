package com.example.resip.data

import android.content.Context
import com.example.resip.data.database.ownedIngredient.OwnedIngredientDatabase
import com.example.resip.data.database.ownedIngredient.PreIngredientDatabase
import com.example.resip.data.repository.IngredientRepository

interface AppContainer{
    val ingredientRepository: IngredientRepository
}

class ResipAppContainer(private val context: Context): AppContainer {
    override val ingredientRepository: IngredientRepository by lazy{
        IngredientRepository(
            ownedIngredientDao = OwnedIngredientDatabase.getInstance(context).ownedIngredientDao(),
            preIngredientDao = PreIngredientDatabase.getInstance(context).preIngredientDao()
        )
    }
}