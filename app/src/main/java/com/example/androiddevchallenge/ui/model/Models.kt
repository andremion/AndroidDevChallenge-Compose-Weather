package com.example.androiddevchallenge.ui.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import com.example.androiddevchallenge.data.model.City
import com.example.androiddevchallenge.data.model.Weather
import com.example.androiddevchallenge.ui.theme.rust600

val City.backgroundColor: Color
    get() = when (weather.conditions) {
        Weather.Conditions.Cloudy -> Color.LightGray
        Weather.Conditions.PartiallyCloudy -> Color.Blue
        Weather.Conditions.Sunny -> Color.Yellow
        Weather.Conditions.Rainy -> Color.Gray
        Weather.Conditions.Stormy -> Color.DarkGray
    }

val Color.contentColor: Color
    get() = if (this.luminance() >= 0.5) rust600 else Color.White
