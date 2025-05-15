package com.example.weatherapp.util

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.weatherapp.api.WeatherResponse
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class WeatherDataStore(private val context: Context) {
    private val Context.dataStore by preferencesDataStore(name = "weather_prefs")
    private val WEATHER_KEY = stringPreferencesKey("weather_data")

    suspend fun saveWeather(data: WeatherResponse) {
        context.dataStore.edit { prefs ->
            prefs[WEATHER_KEY] = Json.encodeToString(data)
        }
    }

    suspend fun getSavedWeather(): WeatherResponse? {
        val json = context.dataStore.data.map { it[WEATHER_KEY] ?: "" }.first()
        return if (json.isNotEmpty()) Json.decodeFromString(json) else null
    }
}
