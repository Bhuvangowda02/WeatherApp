package com.example.weatherapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.viewmodel.WeatherViewModel

@Composable
fun App(viewModel: WeatherViewModel) {
    var darkThemeEnabled by remember { mutableStateOf(false) }

    WeatherAppTheme(darkTheme = darkThemeEnabled) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Text(
                    text = if (darkThemeEnabled) "Dark Mode" else "Light Mode",
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.width(8.dp))
                Switch(
                    checked = darkThemeEnabled,
                    onCheckedChange = { darkThemeEnabled = it }
                )
            }

            MainScreen(viewModel)
        }
    }
}
