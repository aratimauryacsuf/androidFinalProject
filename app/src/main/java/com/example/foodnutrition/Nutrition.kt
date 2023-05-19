package com.example.foodnutrition

import com.google.gson.annotations.SerializedName
import java.util.*

data class Nutrition(
    val id: UUID = UUID.randomUUID()
)
{

    var name: String =""
    var calories: Double= 0.0
    var totalFat: Double=0.0
    var protein: Double=0.0
    var carbohydrates: Double=0.0
}