package com.example.foodnutrition

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

import android.util.Log

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.foodnutrition.api.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

const val INITIAL_QUERY_TEXT_VALUE = ""
private const val TAG = "NutritionViewModel"

class FoodNutritionViewModel: ViewModel() {
    private var query: String = INITIAL_QUERY_TEXT_VALUE

    private val prefs = NutritionDataStore.getRepository()

    private fun saveQuery(editText: String) {
        Log.i(TAG, "In saveQuery method VIewModel: -  $editText")
        viewModelScope.launch {
            prefs.saveQueryText(editText)
            Log.d(TAG, "Saving the query text: $query")
        }
    }
fun loadQueryText(){
    GlobalScope.launch {
        prefs.query_text.collectLatest {
            query = it
            Log.d(TAG, "Loaded the query Text from DataStore to view model: $query")
        }
    }
    Thread.sleep(1000)
}

    fun getQueryString(): String {
        return this.query
    }
    fun setQueryText(q: String) {
        this.query = q
        saveQuery(query)
        Log.d(TAG, "sending query Text to datastore method: $query")

    }


    // data from API
     var nutritionInformationLiveData: LiveData<List<NutritionResponse>> = MutableLiveData()


    fun getNutrition(query:String):LiveData<List<NutritionResponse>>{
        Log.d(TAG, "Received request to Nutrition # $query")
this.nutritionInformationLiveData = NutritionExecutor().getNutrition(query.toString())
        Log.d(TAG, "In viewModel ${this.nutritionInformationLiveData.value?.get(0)?.name}")
        return this.nutritionInformationLiveData
    }

}