package com.example.foodnutrition.api

import retrofit2.Call
import retrofit2.http.*


//private const val API_KEY ="0lclJ4kOY2wkVONE3itf5YJ6dqT90mRbx1Z9088o"
interface NutritionApi {

    @GET("v1/nutrition")
    @Headers("X-Api-Key:rJDgoCqFCNW4nx6KoyIbLuQvnnln5PpwrBJrM73V")
    fun getNutrition(@Query("query") query: String): Call<List<NutritionResponse>>

}