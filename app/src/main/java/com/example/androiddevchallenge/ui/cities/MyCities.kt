package com.example.androiddevchallenge.ui.cities

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.data.model.City
import com.example.androiddevchallenge.ui.animation.AnimatedList
import com.example.androiddevchallenge.ui.model.backgroundColor
import com.example.androiddevchallenge.ui.model.contentColor
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.toPaddingValues

private val ItemHeight = 156.dp
private val InnerPadding = 8.dp

@Composable
fun MyCities(cities: List<City>, onClick: (id: String) -> Unit) {

    AnimatedList(
        itemCount = cities.size,
        itemHeight = ItemHeight
    ) {
        val contentPadding = LocalWindowInsets.current.statusBars.toPaddingValues(
            start = false,
            end = false,
            bottom = false,
            additionalTop = InnerPadding
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = contentPadding,
            verticalArrangement = Arrangement.spacedBy(InnerPadding)
        ) {
            itemsIndexed(cities) { index, city ->
                val backgroundColor = city.backgroundColor
                CompositionLocalProvider(LocalContentColor provides backgroundColor.contentColor) {
                    Card(
                        modifier = Modifier
                            .padding(horizontal = InnerPadding)
                            .height(ItemHeight)
                            .fillMaxWidth()
                            .applyAnimation(index),
                        backgroundColor = backgroundColor
                    ) {
                        Text(
                            modifier = Modifier
                                .clickable { onClick(city.id) }
                                .padding(16.dp),
                            text = city.name,
                            style = MaterialTheme.typography.h4
                        )
                    }
                }
            }
        }
    }
}
