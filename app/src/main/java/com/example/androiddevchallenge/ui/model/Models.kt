package com.example.androiddevchallenge.ui.model

import androidx.annotation.RawRes
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.data.model.City
import com.example.androiddevchallenge.data.model.Weather
import com.example.androiddevchallenge.ui.theme.rust600

val City.backgroundColor: Color
    get() = when (weather.conditions) {
        Weather.Conditions.PartiallyCloudy -> Color.Blue
        Weather.Conditions.PartiallyCloudyNight -> Color.Blue
        Weather.Conditions.Sunny -> Color.Yellow
        Weather.Conditions.Night -> Color.Yellow
        Weather.Conditions.Rainy -> Color.Gray
        Weather.Conditions.RainyNight -> Color.DarkGray
        Weather.Conditions.Stormy -> Color.DarkGray
        Weather.Conditions.StormyNight -> Color.DarkGray
    }

val City.backgroundColors: List<Color>
    get() = when (weather.conditions) {
        Weather.Conditions.PartiallyCloudy -> listOf(Color(0xFF81d4fa), Color(0xFF0288d1))
        Weather.Conditions.PartiallyCloudyNight -> listOf(Color(0xFFb0bec5), Color(0xFF546e7a))
        Weather.Conditions.Sunny -> listOf(Color(0xFFffe082), Color(0xFFffa000))
        Weather.Conditions.Night -> listOf(Color(0xFFCCCCCC), Color(0xFF888888))
        Weather.Conditions.Rainy -> listOf(Color(0xFFCCCCCC), Color(0xFFb0bec5))
        Weather.Conditions.RainyNight -> listOf(Color(0xFFb0bec5), Color(0xFF546e7a))
        Weather.Conditions.Stormy -> listOf(Color(0xFFCCCCCC), Color(0xFF888888))
        Weather.Conditions.StormyNight -> listOf(Color(0xFF888888), Color(0xFF444444))
    }

val Color.contentColor: Color
    get() = if (this.luminance() >= 0.5) rust600 else Color.White

@get:RawRes
val City.icon: Int
    get() = when (weather.conditions) {
        Weather.Conditions.PartiallyCloudy -> R.raw.partly_cloudy
        Weather.Conditions.PartiallyCloudyNight -> R.raw.partly_cloudy_night
        Weather.Conditions.Sunny -> R.raw.sunny
        Weather.Conditions.Night -> R.raw.night
        Weather.Conditions.Rainy -> R.raw.rainy
        Weather.Conditions.RainyNight -> R.raw.rainy_night
        Weather.Conditions.Stormy -> R.raw.stormy
        Weather.Conditions.StormyNight -> R.raw.stormy_night
    }
