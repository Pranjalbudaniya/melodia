package com.melodia.music.core.designsystem.haptics

import com.melodia.music.core.designsystem.tokens.TokenValue

enum class HapticLevel {
    OFF,
    LIGHT,
    MEDIUM,
    FIRM,
}

private const val LIGHT_BELOW = 0.34f
private const val MEDIUM_BELOW = 0.67f

fun TokenValue.Haptic.level(): HapticLevel =
    when {
        !enabled || strength <= 0f -> HapticLevel.OFF
        strength < LIGHT_BELOW -> HapticLevel.LIGHT
        strength < MEDIUM_BELOW -> HapticLevel.MEDIUM
        else -> HapticLevel.FIRM
    }
