package com.melodia.music.core.designsystem.haptics

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.platform.LocalHapticFeedback
import com.melodia.music.core.designsystem.tokens.TokenValue

class MelodiaHaptics(
    private val feedback: HapticFeedback,
    private val spec: TokenValue.Haptic,
) {
    fun perform(event: HapticEvent) {
        cueFor(event, spec.level()).toFeedbackType()?.let { feedback.performHapticFeedback(it) }
    }
}

@Composable
fun rememberMelodiaHaptics(spec: TokenValue.Haptic): MelodiaHaptics {
    val feedback = LocalHapticFeedback.current
    return remember(feedback, spec) { MelodiaHaptics(feedback, spec) }
}
