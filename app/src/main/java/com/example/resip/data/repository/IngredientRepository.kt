package com.example.resip.data.repository

import com.example.resip.data.database.ingredient.IngredientDao
import com.example.resip.data.database.ingredient.IngredientEntity
import com.example.resip.model.Ingredient
import com.example.resip.ui.components.IngredientCard
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun IngredientEntity.toDomain(): Ingredient {
    return Ingredient(name, unit, quantity, isCustom, isOwned)
}

fun Ingredient.toEntity(): IngredientEntity {
    return IngredientEntity(name, unit, quantity, isCustom, isOwned)
}


class IngredientRepository(private val ingredientDao: IngredientDao){
    fun getAllIngredientsStream(): Flow<List<Ingredient>> {
        return ingredientDao.getAllIngredients().map { it.map { it.toDomain() } }
    }

    fun getIngredientStream(name: String): Flow<Ingredient> {
        return ingredientDao.getIngredient(name).map { it.toDomain() }
    }

    suspend fun addCustomIngredient(item: Ingredient){
        ingredientDao.insert(item.toEntity())
    }

    suspend fun addDefinedIngredient(item: Ingredient){
        try{
            if (!item.isCustom){
                ingredientDao.update(item.toEntity())
            }else{
                throw IllegalArgumentException()
            }
        }catch (e: Exception){
            throw e
        }
    }

    suspend fun updateIngredient(item: Ingredient){
        ingredientDao.update(item.toEntity())
    }
}