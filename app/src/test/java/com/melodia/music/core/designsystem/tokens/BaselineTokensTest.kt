package com.melodia.music.core.designsystem.tokens

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class BaselineTokensTest {
    @Test
    fun baselineIdsAreUnique() {
        val ids = BaselineTokens.catalog.definitions.map { it.id.value }
        assertEquals(30, ids.size)
        assertEquals(30, ids.distinct().size)
    }

    @Test
    fun baselineIdsMatchIdFormat() {
        val regex = Regex("^[a-z]+\\.[a-zA-Z0-9]+$")
        for (def in BaselineTokens.catalog.definitions) {
            assertTrue(
                "ID ${def.id.value} does not match format ^[a-z]+\\.[a-zA-Z0-9]+$",
                regex.matches(def.id.value),
            )
        }
    }

    @Test
    fun baselineValuesMatchTheirType() {
        for (def in BaselineTokens.catalog.definitions) {
            assertTrue(
                "Value does not match type for ${def.id.value}",
                def.type.accepts(def.baseline),
            )
        }
    }

    @Test
    fun baselineShapeScaleMatchesVerifiedValues() {
        assertEquals(
            TokenValue.Dimension(dp = 4f),
            BaselineTokens.catalog.find(TokenIds.shapeExtraSmall)?.baseline,
        )
        assertEquals(
            TokenValue.Dimension(dp = 8f),
            BaselineTokens.catalog.find(TokenIds.shapeSmall)?.baseline,
        )
        assertEquals(
            TokenValue.Dimension(dp = 12f),
            BaselineTokens.catalog.find(TokenIds.shapeMedium)?.baseline,
        )
        assertEquals(
            TokenValue.Dimension(dp = 16f),
            BaselineTokens.catalog.find(TokenIds.shapeLarge)?.baseline,
        )
        assertEquals(
            TokenValue.Dimension(dp = 28f),
            BaselineTokens.catalog.find(TokenIds.shapeExtraLarge)?.baseline,
        )
    }

    @Test
    fun baselineTouchTargetIs48dp() {
        assertEquals(
            TokenValue.Dimension(dp = 48f),
            BaselineTokens.catalog.find(TokenIds.sizeTouchTarget)?.baseline,
        )
    }

    @Test
    fun baselineDimensionsAreNotNegative() {
        for (def in BaselineTokens.catalog.definitions) {
            val baseline = def.baseline
            if (baseline is TokenValue.Dimension) {
                assertTrue("dp is negative for ${def.id.value}", baseline.dp >= 0f)
            }
        }
    }

    @Test
    fun baselineMotionSpringsMatchExpressiveValues() {
        assertEquals(
            TokenValue.Spring(stiffness = 800f, dampingRatio = 0.6f),
            BaselineTokens.catalog.find(TokenIds.motionSpatialFast)?.baseline,
        )
        assertEquals(
            TokenValue.Spring(stiffness = 380f, dampingRatio = 0.8f),
            BaselineTokens.catalog.find(TokenIds.motionSpatialDefault)?.baseline,
        )
        assertEquals(
            TokenValue.Spring(stiffness = 200f, dampingRatio = 0.8f),
            BaselineTokens.catalog.find(TokenIds.motionSpatialSlow)?.baseline,
        )
        assertEquals(
            TokenValue.Spring(stiffness = 3800f, dampingRatio = 1.0f),
            BaselineTokens.catalog.find(TokenIds.motionEffectsFast)?.baseline,
        )
        assertEquals(
            TokenValue.Spring(stiffness = 1600f, dampingRatio = 1.0f),
            BaselineTokens.catalog.find(TokenIds.motionEffectsDefault)?.baseline,
        )
        assertEquals(
            TokenValue.Spring(stiffness = 800f, dampingRatio = 1.0f),
            BaselineTokens.catalog.find(TokenIds.motionEffectsSlow)?.baseline,
        )
    }
}
