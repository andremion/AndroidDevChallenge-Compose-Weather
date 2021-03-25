package com.example.androiddevchallenge.ui.home

import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androiddevchallenge.ui.cities.MyCities
import com.example.androiddevchallenge.ui.city.CityDetails
import com.example.androiddevchallenge.ui.city.CityDetailsViewModel
import com.example.androiddevchallenge.util.BackPressHandler
import kotlinx.coroutines.launch

private const val None: String = ""

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Home() {
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

    val viewModel = viewModel(CityDetailsViewModel::class.java)

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = { CityDetails() },
        sheetPeekHeight = 0.dp
    ) {
        MyCities { id ->
            expand(id)
            viewModel.onCitySelected(id)
        }
    }
}
