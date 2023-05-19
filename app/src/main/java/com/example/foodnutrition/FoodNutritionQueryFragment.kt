package com.example.foodnutrition

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.foodnutrition.api.NutritionExecutor
import java.lang.Thread.sleep
import java.util.*

private const val TAG = "queryFragment"

class FoodNutritionQueryFragment : Fragment(), TextToSpeech.OnInitListener {

    private lateinit var textToSpeech: TextToSpeech
    private lateinit var foodNutritionViewModel: FoodNutritionViewModel
    private lateinit var getInfoButton: Button
    private lateinit var clearSearchButton: Button
    private lateinit var foodNutritionLabel: TextView
    private lateinit var foodQueryEditText: EditText


    private fun initializeViewModel() {

        Log.d("QueryFragment", "Initializing ViewModel")
        NutritionDataStore.initialize(this.requireContext())
        foodNutritionViewModel =
            ViewModelProvider(this.requireActivity())[FoodNutritionViewModel::class.java]
        Log.d("QueryFragment", "Initializing ViewModel")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeViewModel()
        this.foodNutritionViewModel =
            ViewModelProvider(this.requireActivity())[FoodNutritionViewModel::class.java]


    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_food_nutrition_query, container, false)
        this.foodNutritionLabel = view.findViewById(R.id.foodNutritionText)
        this.foodQueryEditText = view.findViewById(R.id.editText_food_query)
        this.getInfoButton = view.findViewById(R.id.informationButton)
//        this.clearSearchButton = view.findViewById(R.id.clearButton)
        this.foodNutritionViewModel.loadQueryText()
        this.foodQueryEditText.setText(foodNutritionViewModel.getQueryString().toString())

        textToSpeech = TextToSpeech(requireContext(), this)

        this.getInfoButton.setOnClickListener {
            val query = this.foodQueryEditText.text
            if (query.isNotEmpty()) {
                foodNutritionViewModel.setQueryText(query.toString())
                this.foodNutritionViewModel.getNutrition(query.toString())
                (activity as? MainActivity)?.refreshResponseFragment()
                val text = "Nutritional Information for " + query

                textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
            } else {
                Toast.makeText(
                    context,
                    "Enter food or ingredient to get information",
                    Toast.LENGTH_LONG
                ).show()
            }
            Log.d(TAG, "Inside get info button click")
        }


        return view
    }


    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Language data is missing or the language is not supported.
                // Handle the error condition if needed.
            }
        } else {
            // Initialization failed.
            // Handle the error condition if needed.
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        textToSpeech.stop()
        textToSpeech.shutdown()
    }
}