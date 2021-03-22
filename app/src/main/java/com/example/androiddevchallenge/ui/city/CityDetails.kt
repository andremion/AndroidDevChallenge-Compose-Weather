package com.example.androiddevchallenge.ui.city

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.data.model.CityDetails
import com.example.androiddevchallenge.ui.model.backgroundColor
import com.example.androiddevchallenge.ui.model.contentColor
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.toPaddingValues

private val InnerPadding = 8.dp

@Composable
fun CityDetails(cityDetails: CityDetails) {
    val backgroundColor = cityDetails.city.backgroundColor

    CompositionLocalProvider(LocalContentColor provides backgroundColor.contentColor) {
        val padding = LocalWindowInsets.current.statusBars.toPaddingValues(
            start = false,
            end = false,
            bottom = false,
            additionalHorizontal = InnerPadding,
            additionalVertical = InnerPadding
        )
        Card(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            backgroundColor = backgroundColor
        ) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = cityDetails.city.name,
                style = MaterialTheme.typography.h4
            )
        }
    }
}
