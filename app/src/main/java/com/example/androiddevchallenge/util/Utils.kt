package com.example.androiddevchallenge.util

import android.view.View
import android.view.Window
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.core.view.WindowInsetsControllerCompat

@Suppress("DEPRECATION")
fun Window.makeTransparentStatusBar(isDarkTheme: Boolean) {
    decorView.systemUiVisibility =
        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    WindowInsetsControllerCompat(this, decorView)
        .isAppearanceLightStatusBars = isDarkTheme
}

@Composable
fun Dp.toPx() = with(LocalDensity.current) { toPx() }

@Composable
fun Int.toDp() = with(LocalDensity.current) { toDp() }
