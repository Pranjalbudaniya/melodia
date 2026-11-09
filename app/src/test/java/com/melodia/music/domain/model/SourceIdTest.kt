package com.melodia.music.domain.model

import org.junit.Assert.assertEquals
import org.junit.Test

class SourceIdTest {
    @Test
    fun localSourceIdValueIsLocal() {
        assertEquals("local", SourceId.LOCAL.value)
    }
}
