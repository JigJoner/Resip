package com.example.resip.data.database.preIngredient

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.resip.model.MeasurementUnit

@Entity(tableName = "PreIngredients")
data class PreIngredient(
    @PrimaryKey
    val name: String,
    val unit: MeasurementUnit,
    val quantity: Int
)