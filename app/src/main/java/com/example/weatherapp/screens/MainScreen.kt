package com.example.weatherapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.api.WeatherResponse
import com.example.weatherapp.viewmodel.WeatherViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun MainScreen(viewModel: WeatherViewModel) {
    val weatherData by viewModel.weather.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val context = LocalContext.current
    var city by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Surface( // Applies theme to entire screen
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Weather App",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = city,
                onValueChange = { city = it },
                label = { Text("Enter city") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = {
                        if (city.isNotBlank()) {
                            isLoading = true
                            viewModel.loadWeatherByCity(city.trim())
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Get Weather")
                }

                Button(
                    onClick = {
                        isLoading = true
                        viewModel.loadWeatherByLocation(context)
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text("Use Location")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            errorMessage?.let {
                Text(
                    text = it,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            when {
                isLoading -> CircularProgressIndicator()
                weatherData != null -> WeatherInfo(weatherData!!)
                else -> Text(
                    text = "Enter a city name or use location to fetch weather",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        // Reset loading state when weather or error updates
        LaunchedEffect(weatherData, errorMessage) {
            if (weatherData != null || errorMessage != null) {
                isLoading = false
            }
        }
    }
}

@Composable
fun WeatherInfo(weather: WeatherResponse) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Weather in ${weather.name}",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "${weather.main.temp}°C - ${weather.weather.firstOrNull()?.description ?: ""}",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text("Humidity: ${weather.main.humidity}%", style = MaterialTheme.typography.bodyMedium)
        Text("Pressure: ${weather.main.pressure} hPa", style = MaterialTheme.typography.bodyMedium)
        Text("Wind Speed: ${weather.wind.speed} m/s", style = MaterialTheme.typography.bodyMedium)

        val sunrise = formatTime(weather.sys.sunrise)
        val sunset = formatTime(weather.sys.sunset)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Sunrise: $sunrise", style = MaterialTheme.typography.bodyMedium)
        Text("Sunset: $sunset", style = MaterialTheme.typography.bodyMedium)
    }
}

fun formatTime(timestamp: Long): String {
    val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
    sdf.timeZone = TimeZone.getDefault()
    return sdf.format(Date(timestamp * 1000))
}
