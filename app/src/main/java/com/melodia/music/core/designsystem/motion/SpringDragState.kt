package com.melodia.music.core.designsystem.motion

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.SpringSpec
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Read position inside a layout or graphics-layer lambda (for example Modifier.offset { ... })
 * so the screen does not recompose every frame.
 */
class SpringDragState(
    initial: Float,
    anchors: List<Float>,
    private val velocityThreshold: Float,
) {
    private val anchors: List<Float> = anchors.sorted()
    var position: Float by mutableFloatStateOf(clampToAnchors(initial, this.anchors))
        private set
    var isSettling: Boolean by mutableStateOf(false)
        private set
    private var settleJob: Job? = null

    fun dragBy(delta: Float) {
        position = clampToAnchors(position + delta, anchors)
    }

    fun cancelSettle() {
        settleJob?.cancel()
        settleJob = null
        isSettling = false
    }

    fun settle(
        scope: CoroutineScope,
        velocity: Float,
        spring: SpringSpec<Float>,
    ) {
        cancelSettle()
        val target = settleTarget(position, velocity, anchors, velocityThreshold)
        isSettling = true
        settleJob =
            scope.launch {
                Animatable(position).animateTo(target, spring, initialVelocity = velocity) {
                    position = value
                }
                position = target
                isSettling = false
            }
    }
}
