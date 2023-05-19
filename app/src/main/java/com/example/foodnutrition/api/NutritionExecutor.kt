package com.example.foodnutrition.api


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.foodnutrition.Nutrition
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "NutritionExecutor"

class NutritionExecutor {

    private val api: NutritionApi

private lateinit var nutritionResult: List<NutritionResponse>

    init {


        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.api-ninjas.com/")
           // .client(this.client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        this.api = retrofit.create(NutritionApi::class.java)
    }



    fun getNutrition(query:String): LiveData<List<NutritionResponse>>{
        val responseLiveData:MutableLiveData<List<NutritionResponse>> = MutableLiveData()
        val nutritionRequest:Call<List<NutritionResponse>> = this.api.getNutrition(query = query)
        Log.d(TAG, "Enqueuing  a request to Nutrition query $query ")
        nutritionRequest.enqueue(object :Callback<List<NutritionResponse>>{
            override fun onFailure(call: Call<List<NutritionResponse>>, t: Throwable) {
                Log.e(TAG, "Response received from Nutrition fetch failed ${t.message}" )
            }
            override fun onResponse(
                call: Call<List<NutritionResponse>>,
                response: Response<List<NutritionResponse>>
            ){

                val nutritionInformationResponse:List<NutritionResponse>? = response.body()
                if(nutritionInformationResponse !=null) {
                    Log.d(TAG, "In response ${nutritionInformationResponse.size}")
                    responseLiveData.value = nutritionInformationResponse
                }else{
                    Log.d(TAG, "Request to Nutrition information has failed")
                }
            }
        })

        return responseLiveData
    }

}