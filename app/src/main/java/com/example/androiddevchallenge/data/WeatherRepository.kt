package com.example.androiddevchallenge.data

import com.example.androiddevchallenge.domain.model.City
import com.example.androiddevchallenge.domain.model.CityDetails
import com.example.androiddevchallenge.domain.model.Forecast
import com.example.androiddevchallenge.domain.model.Weather
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import java.util.Calendar
import kotlin.random.Random
import kotlin.random.nextInt

class WeatherRepository {

    fun getMyCities(): Flow<List<City>> =
        flowOf(myCities.values.map(CityDetails::city))

    fun getCityDetails(id: String): Flow<CityDetails> =
        flow {
            myCities[id]?.let { emit(it) }
                ?: throw NotFoundException("\"$id\" was not found")
        }
}

class NotFoundException(message: String) : RuntimeException(message)

private val cities: List<Pair<String, String>> = listOf(
    "NYC" to "New York",
    "LON" to "London",
    "PAR" to "Paris",
    "BER" to "Berlin",
    "REC" to "Recife",
    "LIS" to "Lisbon",
    "MAD" to "Madrid"
)

private val myCities: Map<String, CityDetails> = cities
    .map { (id, name) ->
        id to CityDetails(
            city = City(
                id = id,
                name = name,
                weather = Weather(
                    temperature = generateTemperature(),
                    conditions = generateConditions()
                )
            ),
            forecast = generateForecast()
        )
    }
    .toMap()

private fun generateForecast(): List<Forecast> = (0..10).map { i ->
    val time = Calendar.getInstance().apply { add(Calendar.HOUR_OF_DAY, i) }
    Forecast(
        time = time,
        weather = Weather(
            temperature = generateTemperature(),
            conditions = generateConditions()
        )
    )
}

private fun generateTemperature(): Weather.Temperature =
    Weather.Temperature(Random.nextInt(10..30))

private fun generateConditions(): Weather.Conditions {
    val values = Weather.Conditions.values()
    val index = Random.nextInt(values.size)
    return values[index]
}
