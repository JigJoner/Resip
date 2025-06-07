package com.example.resip.data.database.ownedIngredient

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Delete
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface OwnedIngredientDao{
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(item: OwnedIngredient)

    @Update
    suspend fun update(item: OwnedIngredient)

    @Delete
    suspend fun delete(item: OwnedIngredient)

    @Query("SELECT * from OwnedIngredients WHERE name = :id")
    fun getIngredient(id: String): Flow<OwnedIngredient>

    @Query("SELECT * from OwnedIngredients ORDER BY name ASC")
    fun getAllIngredients(): Flow<List<OwnedIngredient>>
}