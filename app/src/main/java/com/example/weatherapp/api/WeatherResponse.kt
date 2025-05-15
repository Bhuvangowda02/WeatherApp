package com.example.weatherapp.api

data class WeatherResponse(
    val name: String,
    val main: Main,
    val weather: List<Weather>,
    val wind: Wind,
    val sys: Sys
)

data class Main(
    val temp: Float,
    val pressure: Int,
    val humidity: Int
)

data class Weather(
    val description: String
)

data class Wind(
    val speed: Double
)

data class Sys(
    val sunrise: Long,
    val sunset: Long
)
