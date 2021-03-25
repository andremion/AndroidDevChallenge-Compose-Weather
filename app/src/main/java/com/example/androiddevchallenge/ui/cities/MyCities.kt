package com.example.androiddevchallenge.ui.cities

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieAnimationSpec
import com.example.androiddevchallenge.ui.animation.AnimatedList
import com.example.androiddevchallenge.ui.model.backgroundColors
import com.example.androiddevchallenge.ui.model.icon
import com.example.androiddevchallenge.util.toPx
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.toPaddingValues

private val ItemHeight = 164.dp
private val InnerPadding = 24.dp
private val OuterPadding = 8.dp

@Composable
fun MyCities(
    onClick: (id: String) -> Unit
) {
    val viewModel = viewModel(MyCitiesViewModel::class.java)
    val viewState by viewModel.state.collectAsState()
    val cities = viewState.cities

    AnimatedList(
        itemCount = cities.size,
        itemHeight = ItemHeight
    ) {
        val contentPadding = LocalWindowInsets.current.statusBars.toPaddingValues(
            start = false,
            end = false,
            additionalTop = OuterPadding,
            additionalBottom = OuterPadding
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = contentPadding,
            verticalArrangement = Arrangement.spacedBy(OuterPadding)
        ) {
            itemsIndexed(cities) { index, city ->
                Card(
                    modifier = Modifier
                        .padding(horizontal = OuterPadding)
                        .height(ItemHeight)
                        .fillMaxWidth()
                        .applyAnimation(index)
                ) {
                    val backgroundColors = city.weather.backgroundColors
                    val contentColor = Color.White
                    CompositionLocalProvider(LocalContentColor provides contentColor) {
                        Row(
                            modifier = Modifier
                                .background(backgroundColors)
                                .clickable { onClick(city.id) }
                                .padding(InnerPadding),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = city.name,
                                    style = MaterialTheme.typography.h5
                                )
                                Text(
                                    text = city.weather.temperature.asCelsiusString(),
                                    style = MaterialTheme.typography.h3
                                )
                            }
                            LottieAnimation(
                                modifier = Modifier.fillMaxHeight(),
                                spec = remember { LottieAnimationSpec.RawRes(city.weather.icon) }
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun Modifier.background(colors: List<Color>) = composed {
    background(
        brush = Brush.radialGradient(
            colors = colors,
            center = Offset(ItemHeight.toPx(), ItemHeight.toPx() * 0.3f),
            radius = ItemHeight.toPx()
        )
    )
}
