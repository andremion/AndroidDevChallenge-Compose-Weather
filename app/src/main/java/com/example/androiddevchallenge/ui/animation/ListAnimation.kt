package com.example.androiddevchallenge.ui.animation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.example.androiddevchallenge.util.toPx

@Composable
fun AnimatedList(
    itemCount: Int,
    itemHeight: Dp,
    content: @Composable AnimatedListScope.() -> Unit
) {
    val animation = FoldListAnimation(itemCount, itemHeight.toPx())
    AnimatedListScopeImpl(animation).apply {
        StartAnimation()
        content()
    }
}

@Composable
fun AnimatedList(
    animation: ListAnimation,
    content: @Composable AnimatedListScope.() -> Unit
) {
    AnimatedListScopeImpl(animation).apply {
        StartAnimation()
        content()
    }
}

class AnimationData(
    alpha: State<Float>,
    translationY: State<Float>,
    rotationX: State<Float>
) {
    val alpha by alpha
    val translationY by translationY
    val rotationX by rotationX
}

interface AnimatedListScope {

    fun Modifier.applyAnimation(itemIndex: Int): Modifier
}

abstract class ListAnimation : AnimatedListScope {

    protected lateinit var animation: AnimationData

    @SuppressLint("ComposableNaming")
    @Composable
    abstract fun startAnimation()

    protected fun requireAnimationStarted() {
        if (!this::animation.isInitialized) {
            throw IllegalStateException("No animation has started")
        }
    }
}

private class AnimatedListScopeImpl(
    private val animation: ListAnimation
) : AnimatedListScope by animation {

    @Composable
    fun StartAnimation() {
        animation.startAnimation()
    }
}
