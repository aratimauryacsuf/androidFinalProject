package com.example.foodnutrition


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStoreFile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
class NutritionDataStore private constructor( private val dataStore: DataStore<Preferences>) {
private val QUERY_TEXT_KEY = stringPreferencesKey("query_text")

    val query_text: Flow<String> =this.dataStore.data.map { prefs ->
        prefs[QUERY_TEXT_KEY]?:INITIAL_QUERY_TEXT_VALUE
    }.distinctUntilChanged()

    private suspend fun saveStringValue(key: Preferences.Key<String>, value: String) {
        this.dataStore.edit { prefs ->
            prefs[key] = value
        }
    }

    suspend fun saveQueryText(value: String) {
        saveStringValue(QUERY_TEXT_KEY, value)
    }

    companion object {
        private const val PREFERENCES_DATA_FILE_NAME = "settings"
        private var INSTANCE: NutritionDataStore? = null
        fun initialize(context: Context) {
            if (INSTANCE == null) {
                val dataStore = PreferenceDataStoreFactory.create {
                    context.preferencesDataStoreFile(PREFERENCES_DATA_FILE_NAME)
                }
                INSTANCE = NutritionDataStore(dataStore)
            }
        }

        fun getRepository(): NutritionDataStore {
            return INSTANCE
                ?: throw IllegalStateException("AppPreferencesRepository not initialized yet")
        }
    }
}