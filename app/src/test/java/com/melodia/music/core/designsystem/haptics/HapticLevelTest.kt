package com.melodia.music.core.designsystem.haptics

import com.melodia.music.core.designsystem.tokens.BaselineTokens
import com.melodia.music.core.designsystem.tokens.TokenIds
import com.melodia.music.core.designsystem.tokens.TokenValue
import org.junit.Assert.assertEquals
import org.junit.Test

class HapticLevelTest {
    @Test
    fun levelIsOffWhenDisabled() {
        val haptic = TokenValue.Haptic(enabled = false, strength = 1f)
        assertEquals(HapticLevel.OFF, haptic.level())
    }

    @Test
    fun levelIsOffWhenStrengthIsZeroOrNegative() {
        val zero = TokenValue.Haptic(enabled = true, strength = 0f)
        val negative = TokenValue.Haptic(enabled = true, strength = -0.5f)
        assertEquals(HapticLevel.OFF, zero.level())
        assertEquals(HapticLevel.OFF, negative.level())
    }

    @Test
    fun levelSplitsStrengthIntoThreeBands() {
        assertEquals(HapticLevel.LIGHT, TokenValue.Haptic(enabled = true, strength = 0.01f).level())
        assertEquals(HapticLevel.LIGHT, TokenValue.Haptic(enabled = true, strength = 0.33f).level())
        assertEquals(HapticLevel.MEDIUM, TokenValue.Haptic(enabled = true, strength = 0.34f).level())
        assertEquals(HapticLevel.MEDIUM, TokenValue.Haptic(enabled = true, strength = 0.66f).level())
        assertEquals(HapticLevel.FIRM, TokenValue.Haptic(enabled = true, strength = 0.67f).level())
        assertEquals(HapticLevel.FIRM, TokenValue.Haptic(enabled = true, strength = 1.0f).level())
        assertEquals(HapticLevel.FIRM, TokenValue.Haptic(enabled = true, strength = 5.0f).level())
    }

    @Test
    fun baselineHapticTokenIsEnabledAtLightLevel() {
        val baseline = BaselineTokens.catalog.find(TokenIds.hapticFeedback)?.baseline as? TokenValue.Haptic
        val expected = TokenValue.Haptic(enabled = true, strength = 0.25f)
        assertEquals(expected, baseline)
        assertEquals(HapticLevel.LIGHT, baseline?.level())
    }
}
