package com.example.resip.data.database.ingredient

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Delete
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface IngredientDao{
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(item: IngredientEntity)

    @Update
    suspend fun update(item: IngredientEntity)

    @Delete
    suspend fun delete(item: IngredientEntity)

    @Query("SELECT * from Ingredients WHERE name = :id")
    fun getIngredient(id: String): Flow<IngredientEntity>

    @Query("SELECT * from Ingredients ORDER BY name ASC")
    fun getAllIngredients(): Flow<List<IngredientEntity>>
}