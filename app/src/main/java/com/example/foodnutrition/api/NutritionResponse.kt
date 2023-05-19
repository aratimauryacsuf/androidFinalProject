package com.example.foodnutrition.api

import com.google.gson.annotations.SerializedName

data class NutritionResponse (
    @SerializedName("name")
    var name: String ="",
    @SerializedName("calories")
    var calories: Double= 0.0,
    @SerializedName("fat_total_g")
    var totalFat: Double=0.0,
    @SerializedName("protein_g")
    var protein: Double=0.0,
    @SerializedName("carbohydrates_total_g")
    var carbohydrates: Double=0.0
)