package com.example.resip.data.repository

import com.example.resip.data.database.ownedIngredient.OwnedIngredientDao
import com.example.resip.data.database.ownedIngredient.OwnedIngredient
import com.example.resip.data.database.preIngredient.PreIngredient
import com.example.resip.data.database.preIngredient.PreIngredientDao
import com.example.resip.model.Ingredient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import android.util.Log



class IngredientRepository(
    private val ownedIngredientDao: OwnedIngredientDao,
    private val preIngredientDao: PreIngredientDao
){

    fun getAllOwnedIngredientsStream(): Flow<List<Ingredient>> {
        return ownedIngredientDao.getAllIngredients().map { it.map { it.toDomain() } }
    }
    fun getAllPreIngredientsStream(): Flow<List<Ingredient>> {
        return preIngredientDao.getAllIngredients().map { it.map { it.toDomain() } }
    }

    suspend fun addPre(item: Ingredient){
        preIngredientDao.insert(item.toPreIngredientEntity())
    }
    suspend fun addOwnedIngredient(item: Ingredient){
        ownedIngredientDao.insert(item.toOwnedIngredientEntity())
    }

    suspend fun updateOwnedIngredient(item: Ingredient){
        ownedIngredientDao.update(item.toOwnedIngredientEntity())
    }

    suspend fun deleteOwnedIngredient(item: Ingredient){
        ownedIngredientDao.delete(item.toOwnedIngredientEntity())
    }

//    fun getAllIngredientsStream(): Flow<List<Ingredient>> {
//        return ingredientDao.getAllIngredients().map { it.map { it.toDomain() } }
//    }
//
//    fun getIngredientStream(name: String): Flow<Ingredient> {
//        return ingredientDao.getIngredient(name).map { it.toDomain() }
//    }
//
//    suspend fun addCustomIngredient(item: Ingredient){
//        ingredientDao.insert(item.toEntity())
//    }
//
//    suspend fun addDefinedIngredient(item: Ingredient){
//        try{
//            if (!item.isCustom){
//                ingredientDao.update(item.toEntity())
//            }else{
//                throw IllegalArgumentException()
//            }
//        }catch (e: Exception){
//            throw e
//        }
//    }

//    suspend fun updateIngredient(item: Ingredient){
//        ingredientDao.update(item.toEntity())
//    }
}

fun OwnedIngredient.toDomain(): Ingredient {
    return Ingredient(name, unit, quantity)
}

fun Ingredient.toOwnedIngredientEntity(): OwnedIngredient {
    return OwnedIngredient(name, unit, quantity)
}

fun PreIngredient.toDomain(): Ingredient {
    return Ingredient(name, unit, quantity)
}

fun Ingredient.toPreIngredientEntity(): PreIngredient {
    return PreIngredient(name, unit, quantity)
}