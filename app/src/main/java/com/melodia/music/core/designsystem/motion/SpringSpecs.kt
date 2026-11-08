package com.melodia.music.core.designsystem.motion

import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.spring
import com.melodia.music.core.designsystem.tokens.TokenValue
import kotlin.math.max

private const val NO_OVERSHOOT = 1f

fun TokenValue.Spring.reduced(): TokenValue.Spring = copy(dampingRatio = max(dampingRatio, NO_OVERSHOOT))

fun <T> TokenValue.Spring.toSpringSpec(): SpringSpec<T> = spring(dampingRatio = dampingRatio, stiffness = stiffness)
