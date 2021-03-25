package com.example.androiddevchallenge.ui.city

import android.content.Context
import android.text.format.DateFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieAnimationSpec
import com.airbnb.lottie.compose.rememberLottieAnimationState
import com.example.androiddevchallenge.ui.model.backgroundColors
import com.example.androiddevchallenge.ui.model.icon
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.toPaddingValues
import java.util.Calendar

private val InnerPadding = 16.dp
private val OuterPadding = 8.dp
private val IconSize = 128.dp
private val IconSmallSize = 32.dp

@Composable
fun CityDetails() {
    val viewModel = viewModel(CityDetailsViewModel::class.java)
    val viewState by viewModel.state.collectAsState()
    val cityDetails = viewState.cityDetails
    val padding = LocalWindowInsets.current.statusBars.toPaddingValues(
        start = false,
        end = false,
        bottom = false,
        additionalHorizontal = OuterPadding,
        additionalVertical = OuterPadding
    )

    if (cityDetails != null) {
        Card(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
        ) {
            val backgroundColors = cityDetails.city.weather.backgroundColors
            val contentColor = Color.White
            CompositionLocalProvider(LocalContentColor provides contentColor) {
                Column(
                    modifier = Modifier.background(backgroundColors),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        modifier = Modifier.padding(top = InnerPadding * 2),
                        text = LocalContext.current.now(),
                        style = MaterialTheme.typography.h6
                    )

                    Text(
                        modifier = Modifier.padding(bottom = InnerPadding),
                        text = cityDetails.city.name,
                        style = MaterialTheme.typography.h4
                    )

                    LottieAnimation(
                        modifier = Modifier.size(IconSize),
                        spec = remember { LottieAnimationSpec.RawRes(cityDetails.city.weather.icon) },
                        animationState = rememberLottieAnimationState(
                            autoPlay = true,
                            repeatCount = Int.MAX_VALUE
                        ),
                    )

                    Text(
                        modifier = Modifier.padding(top = InnerPadding * 2),
                        text = "Conditions",
                        style = MaterialTheme.typography.h6
                    )

                    Text(
                        text = cityDetails.city.weather.temperature.asCelsiusString(),
                        style = MaterialTheme.typography.h2
                    )

                    LazyRow(
                        modifier = Modifier
                            .padding(top = InnerPadding * 2)
                            .fillMaxWidth(),
                        contentPadding = PaddingValues(InnerPadding),
                        horizontalArrangement = Arrangement.spacedBy(InnerPadding)
                    ) {
                        items(cityDetails.forecast) { forecast ->
                            Card(
                                shape = MaterialTheme.shapes.large,
                                backgroundColor = Color.DarkGray.copy(alpha = LocalContentAlpha.current)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .clickable {
                                            // TODO
                                        }
                                        .padding(InnerPadding),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = forecast.time.format(LocalContext.current),
                                        style = MaterialTheme.typography.caption
                                    )

                                    LottieAnimation(
                                        modifier = Modifier.size(IconSmallSize),
                                        spec = remember { LottieAnimationSpec.RawRes(forecast.weather.icon) },
                                        animationState = rememberLottieAnimationState(autoPlay = false),
                                    )

                                    Text(
                                        text = cityDetails.city.weather.temperature.asCelsiusString(),
                                        style = MaterialTheme.typography.body1
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun Modifier.background(colors: List<Color>) = composed {
    background(brush = Brush.radialGradient(colors = colors))
}

private fun Calendar.format(context: Context): String =
    DateFormat.getTimeFormat(context)
        .format(time)

private fun Context.now(): String =
    DateFormat.getMediumDateFormat(this)
        .format(Calendar.getInstance().time)
