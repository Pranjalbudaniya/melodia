package com.melodia.music.core.designsystem.motion

import androidx.compose.animation.core.SpringSpec
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier

@Composable
fun Modifier.springDraggable(
    state: SpringDragState,
    orientation: Orientation,
    spring: SpringSpec<Float>,
    enabled: Boolean = true,
): Modifier {
    val scope = rememberCoroutineScope()
    val draggableState = rememberDraggableState { delta -> state.dragBy(delta) }
    return draggable(
        state = draggableState,
        orientation = orientation,
        enabled = enabled,
        startDragImmediately = state.isSettling,
        onDragStarted = { state.cancelSettle() },
        onDragStopped = { velocity -> state.settle(scope, velocity, spring) },
    )
}
