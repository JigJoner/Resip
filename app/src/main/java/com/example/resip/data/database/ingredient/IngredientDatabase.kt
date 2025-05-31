package com.example.resip.data.database.ingredient

import androidx.room.Database
import androidx.room.RoomDatabase
import android.content.Context
import androidx.room.Room

@Database(entities = [IngredientEntity::class], version = 1, exportSchema = false)
abstract class IngredientDatabase : RoomDatabase(){
    abstract fun ingredientDao() : IngredientDao
    companion object{
        @Volatile
        private var Instance: IngredientDatabase? = null

        fun getInventoryDatabase(context: Context): IngredientDatabase {
            // If database instance already exists, then return it
            return Instance ?: synchronized(this){ // synchronized on this class so no conflict on the same thread

                //build the database instance
                Room.databaseBuilder(context, IngredientDatabase::class.java, "ingredient_database")
                    .build().also { Instance = it}
            }
        }
    }
}