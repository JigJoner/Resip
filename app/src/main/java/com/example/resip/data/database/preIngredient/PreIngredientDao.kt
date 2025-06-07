package com.example.resip.data.database.preIngredient

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PreIngredientDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(item: PreIngredient)

    @Update
    suspend fun update(item: PreIngredient)

    @Delete
    suspend fun delete(item: PreIngredient)

    @Query("SELECT * from PreIngredients WHERE name = :id")
    fun getIngredient(id: String): Flow<PreIngredient>

    @Query("SELECT * from PreIngredients ORDER BY name ASC")
    fun getAllIngredients(): Flow<List<PreIngredient>>
}