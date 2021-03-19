package com.example.androiddevchallenge.ui.animation

import android.annotation.SuppressLint
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.zIndex

private enum class AnimationState { Initial, Final }

private const val TransitionDuration = 800
private val DefaultTransitionSpec: @Composable (Transition.Segment<AnimationState>.() -> FiniteAnimationSpec<Float>) =
    { TweenSpec(durationMillis = TransitionDuration) }

class FoldListAnimation(
    private val count: Int,
    private val itemHeight: Float
) : ListAnimation() {

    @SuppressLint("ComposableNaming")
    @Composable
    override fun startAnimation() {
        var animationState by remember { mutableStateOf(AnimationState.Initial) }
        LaunchedEffect(true) {
            animationState = AnimationState.Final
        }
        val transition = updateTransition(animationState)

        val alpha = transition.animateFloat(DefaultTransitionSpec) { state ->
            when (state) {
                AnimationState.Initial -> 0.5f
                AnimationState.Final -> 1f
            }
        }
        val translationY = transition.animateFloat(DefaultTransitionSpec) { state ->
            when (state) {
                AnimationState.Initial -> -1f
                AnimationState.Final -> 0f
            }
        }
        val rotationX = transition.animateFloat(DefaultTransitionSpec) { state ->
            when (state) {
                AnimationState.Initial -> 90f
                AnimationState.Final -> 0f
            }
        }

        animation = remember(transition) { AnimationData(alpha, translationY, rotationX) }
    }

    override fun Modifier.applyAnimation(itemIndex: Int): Modifier {
        requireAnimationStarted()
        return this
            .zIndex((count - itemIndex).toFloat())
            .graphicsLayer {
                alpha = if (itemIndex == 0) 1f else animation.alpha
                translationY = animation.translationY * itemHeight * itemIndex
                rotationX = animation.rotationX * itemIndex * 0.1f
            }
    }
}
