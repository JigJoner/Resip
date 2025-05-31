package com.example.resip.data.database.ingredient

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.resip.model.MeasurementUnit

@Entity(tableName = "Ingredients")
data class IngredientEntity(
    @PrimaryKey
    val name: String,
    val unit: MeasurementUnit,
    val quantity: Float,
    val isCustom: Boolean,
    val isOwned: Boolean
)