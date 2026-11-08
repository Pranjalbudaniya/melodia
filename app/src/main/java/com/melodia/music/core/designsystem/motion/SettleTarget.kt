package com.melodia.music.core.designsystem.motion

import kotlin.math.abs

fun settleTarget(
    position: Float,
    velocity: Float,
    anchors: List<Float>,
    velocityThreshold: Float,
): Float =
    when {
        anchors.isEmpty() -> {
            position
        }

        velocity != 0f && abs(velocity) >= velocityThreshold && velocity > 0f -> {
            anchors.firstOrNull { it > position } ?: anchors.last()
        }

        velocity != 0f && abs(velocity) >= velocityThreshold && velocity < 0f -> {
            anchors.lastOrNull { it < position } ?: anchors.first()
        }

        else -> {
            anchors.minByOrNull { abs(position - it) } ?: position
        }
    }

fun clampToAnchors(
    position: Float,
    anchors: List<Float>,
): Float =
    when {
        anchors.isEmpty() -> position
        else -> position.coerceIn(anchors.first(), anchors.last())
    }
