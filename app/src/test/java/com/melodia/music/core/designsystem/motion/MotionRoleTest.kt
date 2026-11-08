package com.melodia.music.core.designsystem.motion

import com.melodia.music.core.designsystem.tokens.BaselineTokens
import com.melodia.music.core.designsystem.tokens.TokenType
import com.melodia.music.core.designsystem.tokens.TokenValue
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class MotionRoleTest {
    @Test
    fun everyRoleMapsToAnExistingSpringToken() {
        for (role in MotionRole.entries) {
            val def = BaselineTokens.catalog.find(role.tokenId)
            assertNotNull("Role $role tokenId ${role.tokenId} not found in catalog", def)
            assertEquals("Role $role must have TokenType.MOTION", TokenType.MOTION, def?.type)
            assertTrue("Role $role baseline must be TokenValue.Spring", def?.baseline is TokenValue.Spring)
        }
    }

    @Test
    fun roleTokenIdsAreUnique() {
        val tokenIds = MotionRole.entries.map { it.tokenId }
        assertEquals(6, tokenIds.size)
        assertEquals(6, tokenIds.distinct().size)
    }

    @Test
    fun effectsRolesNeverOvershoot() {
        val effectsRoles = listOf(MotionRole.EFFECTS_FAST, MotionRole.EFFECTS_DEFAULT, MotionRole.EFFECTS_SLOW)
        for (role in effectsRoles) {
            val spring = BaselineTokens.catalog.find(role.tokenId)?.baseline as TokenValue.Spring
            assertEquals(1.0f, spring.dampingRatio, 0f)
        }
    }

    @Test
    fun spatialRolesGetStifferAsTheyGetFaster() {
        val fast = BaselineTokens.catalog.find(MotionRole.SPATIAL_FAST.tokenId)?.baseline as TokenValue.Spring
        val default = BaselineTokens.catalog.find(MotionRole.SPATIAL_DEFAULT.tokenId)?.baseline as TokenValue.Spring
        val slow = BaselineTokens.catalog.find(MotionRole.SPATIAL_SLOW.tokenId)?.baseline as TokenValue.Spring

        assertTrue(fast.stiffness > default.stiffness)
        assertTrue(default.stiffness > slow.stiffness)
    }
}
