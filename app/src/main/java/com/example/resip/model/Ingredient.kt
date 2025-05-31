package com.example.resip.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

enum class MeasurementUnit(){
    ML,
    G,
    Large,
    Medium,
    Small
}

@Serializable
data class Ingredient(
    val name: String,
    val unit: MeasurementUnit,
    val quantity: Float,
    @SerialName(value = "is_custom") val isCustom: Boolean,
    @SerialName(value = "is_owned") val isOwned: Boolean
)
