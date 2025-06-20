package com.example.resip.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

enum class MeasurementUnit(val unit: String){
    ML(unit = "ml"),
    G(unit="g"),
    Large(unit="Large"),
    Medium(unit="Med"),
    Small(unit="Small")
}

@Serializable
data class Ingredient(
    val name: String,
    val unit: MeasurementUnit = MeasurementUnit.ML,
    val quantity: Int,
//    @SerialName(value = "is_custom") val isCustom: Boolean,
//    @SerialName(value = "is_owned") val isOwned: Boolean
)
