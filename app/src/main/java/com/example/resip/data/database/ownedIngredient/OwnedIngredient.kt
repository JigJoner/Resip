package com.example.resip.data.database.ownedIngredient

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.resip.model.MeasurementUnit

@Entity(tableName = "OwnedIngredients")
data class OwnedIngredient(
    @PrimaryKey
    val name: String,
    val unit: MeasurementUnit,
    val quantity: Int
)