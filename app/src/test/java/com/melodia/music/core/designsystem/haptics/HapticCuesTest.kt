package com.melodia.music.core.designsystem.haptics

import org.junit.Assert.assertEquals
import org.junit.Test

class HapticCuesTest {
    @Test
    fun cueIsNoneWhenLevelIsOff() {
        for (event in HapticEvent.entries) {
            assertEquals("Event $event at level OFF must give NONE", HapticCue.NONE, cueFor(event, HapticLevel.OFF))
        }
    }

    @Test
    fun tapCuesGetFirmerWithLevel() {
        assertEquals(HapticCue.LIGHT_TICK, cueFor(HapticEvent.TAP, HapticLevel.LIGHT))
        assertEquals(HapticCue.TICK, cueFor(HapticEvent.TAP, HapticLevel.MEDIUM))
        assertEquals(HapticCue.CLICK, cueFor(HapticEvent.TAP, HapticLevel.FIRM))
    }

    @Test
    fun tickCuesStopGettingFirmerAtMedium() {
        assertEquals(HapticCue.LIGHT_TICK, cueFor(HapticEvent.TICK, HapticLevel.LIGHT))
        assertEquals(HapticCue.TICK, cueFor(HapticEvent.TICK, HapticLevel.MEDIUM))
        assertEquals(HapticCue.TICK, cueFor(HapticEvent.TICK, HapticLevel.FIRM))
    }

    @Test
    fun confirmAndRejectIgnoreLevel() {
        val levels = listOf(HapticLevel.LIGHT, HapticLevel.MEDIUM, HapticLevel.FIRM)
        for (level in levels) {
            assertEquals("CONFIRM at $level must give CONFIRM", HapticCue.CONFIRM, cueFor(HapticEvent.CONFIRM, level))
            assertEquals("REJECT at $level must give REJECT", HapticCue.REJECT, cueFor(HapticEvent.REJECT, level))
        }
    }
}
