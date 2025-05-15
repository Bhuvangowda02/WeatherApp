package com.example.weatherapp.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.api.WeatherResponse
import com.example.weatherapp.api.RetrofitInstance
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    private val _weather = MutableStateFlow<WeatherResponse?>(null)
    val weather: StateFlow<WeatherResponse?> = _weather.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val apiKey = "a5d7dd9eba59093282d1f91939402d91"

    fun loadWeatherByCity(city: String) {
        viewModelScope.launch {
            try {
                _errorMessage.value = null // Clear previous errors
                val response = RetrofitInstance.api.getWeatherByCity(city, apiKey)
                if (response.isSuccessful && response.body() != null) {
                    _weather.value = response.body()
                } else {
                    _weather.value = null
                    _errorMessage.value = "City not found or API error."
                }
            } catch (e: Exception) {
                _weather.value = null
                _errorMessage.value = "Failed to load weather: ${e.localizedMessage ?: "Unknown error"}"
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun loadWeatherByLocation(context: Context) {
        val fusedClient: FusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(context)

        fusedClient.lastLocation.addOnSuccessListener { location: Location? ->
            location?.let {
                loadWeatherByCoordinates(it.latitude, it.longitude)
            } ?: run {
                _weather.value = null
                _errorMessage.value = "Could not retrieve location."
            }
        }.addOnFailureListener {
            _weather.value = null
            _errorMessage.value = "Failed to get location."
        }
    }

    private fun loadWeatherByCoordinates(lat: Double, lon: Double) {
        viewModelScope.launch {
            try {
                _errorMessage.value = null
                val response = RetrofitInstance.api.getWeatherByCoordinates(lat, lon, apiKey)
                if (response.isSuccessful && response.body() != null) {
                    _weather.value = response.body()
                } else {
                    _weather.value = null
                    _errorMessage.value = "Location not found or API error."
                }
            } catch (e: Exception) {
                _weather.value = null
                _errorMessage.value = "Failed to load weather: ${e.localizedMessage ?: "Unknown error"}"
            }
        }
    }
}
