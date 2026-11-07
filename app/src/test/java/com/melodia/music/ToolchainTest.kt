package com.melodia.music

import org.junit.Assert.assertTrue
import org.junit.Test

class ToolchainTest {
    @Test
    fun kotlinStdlibIsAtLeast2_4() {
        assertTrue(KotlinVersion.CURRENT.isAtLeast(2, 4))
    }
}
