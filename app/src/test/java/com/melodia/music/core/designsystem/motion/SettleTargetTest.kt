package com.melodia.music.core.designsystem.motion

import org.junit.Assert.assertEquals
import org.junit.Test

class SettleTargetTest {
    private val defaultAnchors = listOf(0f, 100f, 200f)
    private val threshold = 1000f

    @Test
    fun slowReleaseSettlesToNearestAnchor() {
        assertEquals(
            0f,
            settleTarget(position = 40f, velocity = 0f, anchors = defaultAnchors, velocityThreshold = threshold),
            0f,
        )
        assertEquals(
            100f,
            settleTarget(position = 60f, velocity = 0f, anchors = defaultAnchors, velocityThreshold = threshold),
            0f,
        )
        assertEquals(
            200f,
            settleTarget(position = 160f, velocity = 0f, anchors = defaultAnchors, velocityThreshold = threshold),
            0f,
        )
    }

    @Test
    fun slowReleaseTieGoesToLowerAnchor() {
        assertEquals(
            0f,
            settleTarget(position = 50f, velocity = 0f, anchors = defaultAnchors, velocityThreshold = threshold),
            0f,
        )
        assertEquals(
            100f,
            settleTarget(position = 150f, velocity = 0f, anchors = defaultAnchors, velocityThreshold = threshold),
            0f,
        )
    }

    @Test
    fun fastReleaseMovesToNextAnchorInVelocityDirection() {
        assertEquals(
            100f,
            settleTarget(position = 40f, velocity = 1500f, anchors = defaultAnchors, velocityThreshold = threshold),
            0f,
        )
        assertEquals(
            100f,
            settleTarget(position = 160f, velocity = -1500f, anchors = defaultAnchors, velocityThreshold = threshold),
            0f,
        )
    }

    @Test
    fun fastReleaseFromAnAnchorLeavesThatAnchor() {
        assertEquals(
            200f,
            settleTarget(position = 100f, velocity = 1500f, anchors = defaultAnchors, velocityThreshold = threshold),
            0f,
        )
        assertEquals(
            0f,
            settleTarget(position = 100f, velocity = -1500f, anchors = defaultAnchors, velocityThreshold = threshold),
            0f,
        )
    }

    @Test
    fun fastReleaseAtTheEndStaysOnTheEndAnchor() {
        assertEquals(
            200f,
            settleTarget(position = 200f, velocity = 1500f, anchors = defaultAnchors, velocityThreshold = threshold),
            0f,
        )
        assertEquals(
            0f,
            settleTarget(position = 0f, velocity = -1500f, anchors = defaultAnchors, velocityThreshold = threshold),
            0f,
        )
    }

    @Test
    fun velocityThresholdIsInclusive() {
        assertEquals(
            0f,
            settleTarget(position = 40f, velocity = 999f, anchors = defaultAnchors, velocityThreshold = threshold),
            0f,
        )
        assertEquals(
            100f,
            settleTarget(position = 40f, velocity = 1000f, anchors = defaultAnchors, velocityThreshold = threshold),
            0f,
        )
        assertEquals(
            100f,
            settleTarget(position = 160f, velocity = -1000f, anchors = defaultAnchors, velocityThreshold = threshold),
            0f,
        )
    }

    @Test
    fun positionOutsideTheRangeSettlesToTheNearestEnd() {
        assertEquals(
            0f,
            settleTarget(position = -30f, velocity = 0f, anchors = defaultAnchors, velocityThreshold = threshold),
            0f,
        )
        assertEquals(
            200f,
            settleTarget(position = 250f, velocity = 0f, anchors = defaultAnchors, velocityThreshold = threshold),
            0f,
        )
    }

    @Test
    fun singleAnchorAlwaysWins() {
        val singleAnchor = listOf(50f)
        assertEquals(
            50f,
            settleTarget(position = 0f, velocity = 0f, anchors = singleAnchor, velocityThreshold = threshold),
            0f,
        )
        assertEquals(
            50f,
            settleTarget(position = 90f, velocity = 5000f, anchors = singleAnchor, velocityThreshold = threshold),
            0f,
        )
        assertEquals(
            50f,
            settleTarget(position = 50f, velocity = -5000f, anchors = singleAnchor, velocityThreshold = threshold),
            0f,
        )
    }

    @Test
    fun emptyAnchorsKeepThePosition() {
        val emptyAnchors = emptyList<Float>()
        assertEquals(
            30f,
            settleTarget(position = 30f, velocity = 0f, anchors = emptyAnchors, velocityThreshold = threshold),
            0f,
        )
        assertEquals(
            30f,
            settleTarget(position = 30f, velocity = 5000f, anchors = emptyAnchors, velocityThreshold = threshold),
            0f,
        )
    }

    @Test
    fun clampKeepsPositionInsideFirstAndLastAnchor() {
        val anchors = listOf(0f, 100f)
        assertEquals(0f, clampToAnchors(-5f, anchors), 0f)
        assertEquals(50f, clampToAnchors(50f, anchors), 0f)
        assertEquals(100f, clampToAnchors(120f, anchors), 0f)
        assertEquals(7f, clampToAnchors(7f, emptyList()), 0f)
    }
}
