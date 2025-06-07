package com.example.resip.data.database.ownedIngredient

import androidx.room.Database
import androidx.room.RoomDatabase
import android.content.Context
import android.util.Log
import androidx.room.Room

@Database(entities = [OwnedIngredient::class], version = 1, exportSchema = false)
abstract class OwnedIngredientDatabase : RoomDatabase(){
    abstract fun ownedIngredientDao() : OwnedIngredientDao
    companion object{
        @Volatile
        private var Instance: OwnedIngredientDatabase? = null

        fun getInstance(context: Context): OwnedIngredientDatabase {
            // If database instance already exists, then return it
            Log.d("OwnedIngredient", "Instance Returned")
            return Instance ?: synchronized(this){ // synchronized on this class so no conflict on the same thread

                //build the database instance
                Room.databaseBuilder(context.applicationContext, OwnedIngredientDatabase::class.java, "owned_ingredient_database")
                    .build().also { Instance = it}
            }

        }
    }
}