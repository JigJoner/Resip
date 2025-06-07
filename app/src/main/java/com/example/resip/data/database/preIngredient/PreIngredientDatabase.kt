package com.example.resip.data.database.ownedIngredient

import androidx.room.Database
import androidx.room.RoomDatabase
import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.resip.data.database.preIngredient.PreIngredient
import com.example.resip.data.database.preIngredient.PreIngredientDao

@Database(entities = [PreIngredient::class], version = 1, exportSchema = false)
abstract class PreIngredientDatabase : RoomDatabase(){
    abstract fun preIngredientDao() : PreIngredientDao
    companion object{
        @Volatile
        private var Instance: PreIngredientDatabase? = null

        fun getInstance(context: Context): PreIngredientDatabase {
            // If database instance already exists, then return it
            Log.d("PreIngredient", "Instance Returned")
            return Instance ?: synchronized(this){ // synchronized on this class so no conflict on the same thread

                //build the database instance
                Room.databaseBuilder(context.applicationContext, PreIngredientDatabase::class.java, "pre_ingredient_database")
                    .build().also { Instance = it}
            }
        }
    }
}