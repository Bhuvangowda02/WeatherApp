package com.example.weatherapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object RetrofitInstance {
    val api: WeatherApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }
}

interface WeatherApi {
    @GET("weather?units=metric")
    suspend fun getWeatherByCity(
        @Query("q") city: String,
        @Query("appid") apiKey: String
    ): retrofit2.Response<WeatherResponse>

    @GET("weather?units=metric")
    suspend fun getWeatherByCoordinates(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String
    ): retrofit2.Response<WeatherResponse>
}

