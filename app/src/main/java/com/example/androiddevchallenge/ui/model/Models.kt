package com.example.androiddevchallenge.ui.model

import androidx.annotation.RawRes
import androidx.compose.ui.graphics.Color
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.data.model.Weather

val Weather.backgroundColors: List<Color>
    get() = when (conditions) {
        Weather.Conditions.PartiallyCloudy -> listOf(Color(0xFF81d4fa), Color(0xFF0288d1))
        Weather.Conditions.PartiallyCloudyNight -> listOf(Color(0xFFb0bec5), Color(0xFF546e7a))
        Weather.Conditions.Sunny -> listOf(Color(0xFFffe082), Color(0xFFffa000))
        Weather.Conditions.Night -> listOf(Color(0xFFCCCCCC), Color(0xFF888888))
        Weather.Conditions.Rainy -> listOf(Color(0xFFCCCCCC), Color(0xFFb0bec5))
        Weather.Conditions.RainyNight -> listOf(Color(0xFFb0bec5), Color(0xFF546e7a))
        Weather.Conditions.Stormy -> listOf(Color(0xFFCCCCCC), Color(0xFF888888))
        Weather.Conditions.StormyNight -> listOf(Color(0xFF888888), Color(0xFF444444))
    }

@get:RawRes
val Weather.icon: Int
    get() = when (conditions) {
        Weather.Conditions.PartiallyCloudy -> R.raw.partly_cloudy
        Weather.Conditions.PartiallyCloudyNight -> R.raw.partly_cloudy_night
        Weather.Conditions.Sunny -> R.raw.sunny
        Weather.Conditions.Night -> R.raw.night
        Weather.Conditions.Rainy -> R.raw.rainy
        Weather.Conditions.RainyNight -> R.raw.rainy_night
        Weather.Conditions.Stormy -> R.raw.stormy
        Weather.Conditions.StormyNight -> R.raw.stormy_night
    }
