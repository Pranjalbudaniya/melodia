package com.melodia.music.core.designsystem.tokens

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class TokenResolverTest {
    private val colorId = TokenId("color.test")
    private val shapeId = TokenId("shape.test")
    private val surfaceId = TokenId("surface.test")

    private val baselineColor = TokenValue.Color(light = 1L, dark = 2L)
    private val baselineShape = TokenValue.Dimension(dp = 8f)
    private val baselineSurface = TokenValue.Surface(SurfaceStyle.SOLID)

    private val langColor = TokenValue.Color(light = 3L, dark = 4L)
    private val userColor = TokenValue.Color(light = 5L, dark = 6L)
    private val scopedColor = TokenValue.Color(light = 7L, dark = 8L)
    private val compColor = TokenValue.Color(light = 9L, dark = 10L)
    private val otherCompColor = TokenValue.Color(light = 11L, dark = 12L)

    private val catalog =
        TokenCatalog(
            listOf(
                TokenDefinition(colorId, TokenType.COLOR, baselineColor),
                TokenDefinition(shapeId, TokenType.SHAPE, baselineShape),
                TokenDefinition(surfaceId, TokenType.SURFACE, baselineSurface),
            ),
        )

    private val resolver = TokenResolver(catalog)

    @Test
    fun resolveUnknownIdReturnsNull() {
        val unknownId = TokenId("unknown.id")
        val layers =
            TokenLayers(
                language =
                    DesignLanguage(
                        id = "test",
                        defaults = mapOf(unknownId to langColor),
                        componentStyles = mapOf("c1" to mapOf(unknownId to otherCompColor)),
                    ),
                userTheme = mapOf(unknownId to userColor),
                scopedTheme = mapOf(unknownId to scopedColor),
                componentOverrides = mapOf("c1" to mapOf(unknownId to compColor)),
            )

        val result = resolver.resolve(unknownId, layers, componentId = "c1")

        assertNull(result)
    }

    @Test
    fun resolveNoLayersReturnsBaseline() {
        val layers = TokenLayers(language = DesignLanguage(id = "test"))

        val result = resolver.resolve(colorId, layers)

        assertEquals(baselineColor, result)
    }

    @Test
    fun resolveLanguageDefaultBeatsBaseline() {
        val layers =
            TokenLayers(
                language =
                    DesignLanguage(
                        id = "test",
                        defaults = mapOf(colorId to langColor),
                    ),
            )

        val result = resolver.resolve(colorId, layers)

        assertEquals(langColor, result)
    }

    @Test
    fun resolveUserThemeBeatsLanguageDefault() {
        val layers =
            TokenLayers(
                language =
                    DesignLanguage(
                        id = "test",
                        defaults = mapOf(colorId to langColor),
                    ),
                userTheme = mapOf(colorId to userColor),
            )

        val result = resolver.resolve(colorId, layers)

        assertEquals(userColor, result)
    }

    @Test
    fun resolveScopedThemeBeatsUserTheme() {
        val layers =
            TokenLayers(
                language = DesignLanguage(id = "test"),
                userTheme = mapOf(colorId to userColor),
                scopedTheme = mapOf(colorId to scopedColor),
            )

        val result = resolver.resolve(colorId, layers)

        assertEquals(scopedColor, result)
    }

    @Test
    fun resolveComponentOverrideBeatsScopedTheme() {
        val layers =
            TokenLayers(
                language = DesignLanguage(id = "test"),
                scopedTheme = mapOf(colorId to scopedColor),
                componentOverrides = mapOf("c1" to mapOf(colorId to compColor)),
            )

        val result = resolver.resolve(colorId, layers, componentId = "c1")

        assertEquals(compColor, result)
    }

    @Test
    fun resolveComponentOverrideForOtherComponentIsIgnored() {
        val layers =
            TokenLayers(
                language = DesignLanguage(id = "test"),
                scopedTheme = mapOf(colorId to scopedColor),
                componentOverrides = mapOf("c2" to mapOf(colorId to otherCompColor)),
            )

        val result = resolver.resolve(colorId, layers, componentId = "c1")

        assertEquals(scopedColor, result)
    }

    @Test
    fun resolveNullComponentIdIgnoresComponentLayers() {
        val layers =
            TokenLayers(
                language =
                    DesignLanguage(
                        id = "test",
                        defaults = mapOf(colorId to langColor),
                        componentStyles = mapOf("c1" to mapOf(colorId to otherCompColor)),
                    ),
                componentOverrides = mapOf("c1" to mapOf(colorId to compColor)),
            )

        val result = resolver.resolve(colorId, layers, componentId = null)

        assertEquals(langColor, result)
    }

    @Test
    fun resolveLanguageComponentStyleBeatsLanguageDefaultAndLosesToUserTheme() {
        val styleColor = TokenValue.Color(light = 9L, dark = 10L)
        val layersWithoutUser =
            TokenLayers(
                language =
                    DesignLanguage(
                        id = "test",
                        defaults = mapOf(colorId to langColor),
                        componentStyles = mapOf("button" to mapOf(colorId to styleColor)),
                    ),
            )

        assertEquals(styleColor, resolver.resolve(colorId, layersWithoutUser, componentId = "button"))
        assertEquals(langColor, resolver.resolve(colorId, layersWithoutUser, componentId = "card"))

        val layersWithUser = layersWithoutUser.copy(userTheme = mapOf(colorId to userColor))
        assertEquals(userColor, resolver.resolve(colorId, layersWithUser, componentId = "button"))
    }

    @Test
    fun resolveTypeMismatchIsSkippedAndNextLayerWins() {
        val layers =
            TokenLayers(
                language =
                    DesignLanguage(
                        id = "test",
                        defaults = mapOf(colorId to langColor),
                    ),
                userTheme = mapOf(colorId to TokenValue.Dimension(dp = 10f)),
            )

        val result = resolver.resolve(colorId, layers)

        assertEquals(langColor, result)
    }

    @Test
    fun resolveAllLayersMismatchedReturnsBaseline() {
        val mismatched = TokenValue.Dimension(dp = 10f)
        val layers =
            TokenLayers(
                language =
                    DesignLanguage(
                        id = "test",
                        defaults = mapOf(colorId to mismatched),
                        componentStyles = mapOf("c1" to mapOf(colorId to mismatched)),
                    ),
                userTheme = mapOf(colorId to mismatched),
                scopedTheme = mapOf(colorId to mismatched),
                componentOverrides = mapOf("c1" to mapOf(colorId to mismatched)),
            )

        val result = resolver.resolve(colorId, layers, componentId = "c1")

        assertEquals(baselineColor, result)
    }

    @Test
    fun acceptsEveryTokenTypeTakesExactlyOneValueKind() {
        val colorVal = TokenValue.Color(light = 1L, dark = 2L)
        val dimVal = TokenValue.Dimension(dp = 4f)
        val fontVal = TokenValue.Font(fontId = "font")
        val iconVal = TokenValue.Icon(setId = "icon")
        val springVal = TokenValue.Spring(stiffness = 100f, dampingRatio = 1f)
        val hapticVal = TokenValue.Haptic(enabled = true, strength = 0.5f)
        val surfaceVal = TokenValue.Surface(style = SurfaceStyle.SOLID)

        val allSamples = listOf(colorVal, dimVal, fontVal, iconVal, springVal, hapticVal, surfaceVal)

        for (tokenType in TokenType.entries) {
            val acceptedCount = allSamples.count { tokenType.accepts(it) }
            assertEquals("TokenType $tokenType must accept exactly 1 sample", 1, acceptedCount)
        }

        assertTrue(TokenType.COLOR.accepts(colorVal))
        assertTrue(TokenType.SHAPE.accepts(dimVal))
        assertTrue(TokenType.SIZE.accepts(dimVal))
        assertTrue(TokenType.SPACING.accepts(dimVal))
        assertTrue(TokenType.STROKE.accepts(dimVal))
        assertTrue(TokenType.ELEVATION.accepts(dimVal))
        assertTrue(TokenType.FONT.accepts(fontVal))
        assertTrue(TokenType.ICON.accepts(iconVal))
        assertTrue(TokenType.MOTION.accepts(springVal))
        assertTrue(TokenType.HAPTIC.accepts(hapticVal))
        assertTrue(TokenType.SURFACE.accepts(surfaceVal))
    }
}
