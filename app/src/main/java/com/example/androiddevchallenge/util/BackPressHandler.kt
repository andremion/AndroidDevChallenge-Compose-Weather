package com.example.androiddevchallenge.util

import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember

@Composable
fun BackPressHandler(enabled: Boolean = true, onBackPressed: () -> Unit) {

    val backCallback = remember {
        object : OnBackPressedCallback(enabled) {
            override fun handleOnBackPressed() {
                onBackPressed()
            }
        }
    }

    // Guarantees that failed transactions don't incorrectly toggle the remembered callback.
    SideEffect { backCallback.isEnabled = enabled }

    val dispatcher = LocalOnBackPressedDispatcherOwner.current.onBackPressedDispatcher
    DisposableEffect(dispatcher) {
        // Whenever there's a new dispatcher set up the callback
        dispatcher.addCallback(backCallback)
        onDispose {
            // Prevent leaks
            backCallback.remove()
        }
    }
}
