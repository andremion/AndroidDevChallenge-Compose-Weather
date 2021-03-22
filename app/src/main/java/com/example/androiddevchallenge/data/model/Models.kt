package com.example.androiddevchallenge.data.model

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

    enum class Conditions { Cloudy, PartiallyCloudy, Sunny, Rainy, Stormy }

    data class Temperature(private val value: Int) {

        fun asCelsius(): Int = value

        fun asFahrenheit(): Int = ((value * 1.8f) + 32).roundToInt()
    }
}

data class CityDetails(
    val city: City,
    val forecast: List<Forecast>
)

data class Forecast(val time: Calendar, val weather: Weather)
