package com.melodia.music.core.designsystem.motion

import com.melodia.music.core.designsystem.tokens.TokenValue
import org.junit.Assert.assertEquals
import org.junit.Test

class SpringSpecsTest {
    @Test
    fun toSpringSpecCopiesDampingAndStiffness() {
        val spring = TokenValue.Spring(stiffness = 800f, dampingRatio = 0.6f)
        val spec = spring.toSpringSpec<Float>()
        assertEquals(0.6f, spec.dampingRatio, 0f)
        assertEquals(800f, spec.stiffness, 0f)
    }

    @Test
    fun reducedRaisesLowDampingToOne() {
        val spring = TokenValue.Spring(stiffness = 800f, dampingRatio = 0.6f)
        val reduced = spring.reduced()
        assertEquals(1.0f, reduced.dampingRatio, 0f)
        assertEquals(800f, reduced.stiffness, 0f)
    }

    @Test
    fun reducedKeepsDampingAtOrAboveOneAndStiffness() {
        val s1 = TokenValue.Spring(stiffness = 1600f, dampingRatio = 1.0f)
        val r1 = s1.reduced()
        assertEquals(1.0f, r1.dampingRatio, 0f)
        assertEquals(1600f, r1.stiffness, 0f)

        val s2 = TokenValue.Spring(stiffness = 300f, dampingRatio = 1.5f)
        val r2 = s2.reduced()
        assertEquals(1.5f, r2.dampingRatio, 0f)
        assertEquals(300f, r2.stiffness, 0f)
    }
}
