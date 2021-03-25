package com.example.androiddevchallenge.domain.model

import java.util.Calendar
import kotlin.math.roundToInt

data class City(
    val id: String,
    val name: String,
    val weather: Weather
)

data class Weather(
    val temperature: Temperature,
    val conditions: Conditions
) {

    enum class Conditions {
        PartiallyCloudy, PartiallyCloudyNight,
        Sunny, Night,
        Rainy, RainyNight,
        Stormy, StormyNight
    }

    data class Temperature(private val value: Int) {

        fun asCelsius(): Int = value

        fun asCelsiusString(): String = "${asCelsius()}℃"

        fun asFahrenheit(): Int = ((value * 1.8f) + 32).roundToInt()

        fun asFahrenheitString(): String = "${asCelsius()}℉"
    }
}

data class CityDetails(
    val city: City,
    val forecast: List<Forecast>
)

data class Forecast(val time: Calendar, val weather: Weather)
