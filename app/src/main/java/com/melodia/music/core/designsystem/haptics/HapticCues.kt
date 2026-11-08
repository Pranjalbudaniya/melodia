package com.melodia.music.core.designsystem.haptics

import androidx.compose.ui.hapticfeedback.HapticFeedbackType

enum class HapticEvent {
    TAP,
    TICK,
    CONFIRM,
    REJECT,
}

enum class HapticCue {
    NONE,
    LIGHT_TICK,
    TICK,
    CLICK,
    CONFIRM,
    REJECT,
}

fun cueFor(
    event: HapticEvent,
    level: HapticLevel,
): HapticCue =
    if (level == HapticLevel.OFF) {
        HapticCue.NONE
    } else {
        when (event) {
            HapticEvent.TAP -> {
                when (level) {
                    HapticLevel.OFF -> HapticCue.NONE
                    HapticLevel.LIGHT -> HapticCue.LIGHT_TICK
                    HapticLevel.MEDIUM -> HapticCue.TICK
                    HapticLevel.FIRM -> HapticCue.CLICK
                }
            }

            HapticEvent.TICK -> {
                when (level) {
                    HapticLevel.OFF -> HapticCue.NONE
                    HapticLevel.LIGHT -> HapticCue.LIGHT_TICK
                    HapticLevel.MEDIUM, HapticLevel.FIRM -> HapticCue.TICK
                }
            }

            HapticEvent.CONFIRM -> {
                HapticCue.CONFIRM
            }

            HapticEvent.REJECT -> {
                HapticCue.REJECT
            }
        }
    }

fun HapticCue.toFeedbackType(): HapticFeedbackType? =
    when (this) {
        HapticCue.NONE -> null
        HapticCue.LIGHT_TICK -> HapticFeedbackType.SegmentFrequentTick
        HapticCue.TICK -> HapticFeedbackType.SegmentTick
        HapticCue.CLICK -> HapticFeedbackType.VirtualKey
        HapticCue.CONFIRM -> HapticFeedbackType.Confirm
        HapticCue.REJECT -> HapticFeedbackType.Reject
    }
