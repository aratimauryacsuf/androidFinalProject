package com.example.foodnutrition

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

private const val TAG ="MainActivity"
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val foodNutritionQueryFragment = this.supportFragmentManager.findFragmentById(R.id.food_nutrition_query_container)
        if (foodNutritionQueryFragment == null) {
            val fragment1 = FoodNutritionQueryFragment()
            this.supportFragmentManager
                .beginTransaction()
                .add(R.id.food_nutrition_query_container, fragment1, "fragment1")
                .commit()
        }

        val foodNutritionResponseFragment = this.supportFragmentManager.findFragmentById(R.id.food_nutrition_response_container)
        if (foodNutritionResponseFragment == null) {
            val fragment2 = FoodNutritionResponseFragment()
            this.supportFragmentManager
                .beginTransaction()
                .add(R.id.food_nutrition_response_container, fragment2, "fragment2")
                .commit()
        }
    }
    fun refreshResponseFragment(){
        Log.d(TAG, "Inside main activity refreshResponseFrag method")
        val responseFragment = this.supportFragmentManager.findFragmentByTag("fragment2") as FoodNutritionResponseFragment?
    responseFragment?.refresh()
    }
}