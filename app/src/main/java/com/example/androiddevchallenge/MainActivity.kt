/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.data.WeatherRepository
import com.example.androiddevchallenge.ui.cities.MyCities
import com.example.androiddevchallenge.ui.city.CityDetails
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.util.BackPressHandler
import com.example.androiddevchallenge.util.makeTransparentStatusBar
import com.google.accompanist.insets.ProvideWindowInsets
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            window.makeTransparentStatusBar(isSystemInDarkTheme())
            MyTheme {
                MyApp()
            }
        }
    }
}

// Start building your app here!
@Composable
fun MyApp(repository: WeatherRepository = WeatherRepository()) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background,
    ) {
        ProvideWindowInsets {
            MyScreen(repository)
        }
    }
}

private const val None: String = ""

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MyScreen(repository: WeatherRepository) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()
    var selectedId by rememberSaveable { mutableStateOf(None) }

    fun collapse() {
        scope.launch { scaffoldState.bottomSheetState.collapse() }
            .invokeOnCompletion { selectedId = None }
    }

    fun expand(id: String) {
        selectedId = id
        scope.launch { scaffoldState.bottomSheetState.expand() }
    }

    BackPressHandler(selectedId != None, ::collapse)

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            if (selectedId != None) {
                val cityDetails = remember(selectedId) { repository.getCityDetails(selectedId) }
                CityDetails(cityDetails)
            }
        },
        sheetPeekHeight = 0.dp
    ) {
        val myCities = remember { repository.getMyCities() }
        MyCities(myCities, ::expand)
    }
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApp()
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MyApp()
    }
}
