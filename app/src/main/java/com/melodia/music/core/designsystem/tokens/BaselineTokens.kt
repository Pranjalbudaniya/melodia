package com.melodia.music.core.designsystem.tokens

object TokenIds {
    val shapeExtraSmall = TokenId("shape.extraSmall")
    val shapeSmall = TokenId("shape.small")
    val shapeMedium = TokenId("shape.medium")
    val shapeLarge = TokenId("shape.large")
    val shapeExtraLarge = TokenId("shape.extraLarge")

    val spacingXs = TokenId("spacing.xs")
    val spacingSm = TokenId("spacing.sm")
    val spacingMd = TokenId("spacing.md")
    val spacingLg = TokenId("spacing.lg")
    val spacingXl = TokenId("spacing.xl")

    val sizeTouchTarget = TokenId("size.touchTarget")

    val strokeThin = TokenId("stroke.thin")
    val strokeMedium = TokenId("stroke.medium")

    val elevationLevel0 = TokenId("elevation.level0")
    val elevationLevel1 = TokenId("elevation.level1")
    val elevationLevel2 = TokenId("elevation.level2")
    val elevationLevel3 = TokenId("elevation.level3")
    val elevationLevel4 = TokenId("elevation.level4")
    val elevationLevel5 = TokenId("elevation.level5")

    val fontDisplay = TokenId("font.display")
    val fontTitle = TokenId("font.title")
    val fontBody = TokenId("font.body")
    val fontLyrics = TokenId("font.lyrics")

    val surfaceStyle = TokenId("surface.style")

    val motionSpatialFast = TokenId("motion.spatialFast")
    val motionSpatialDefault = TokenId("motion.spatialDefault")
    val motionSpatialSlow = TokenId("motion.spatialSlow")
    val motionEffectsFast = TokenId("motion.effectsFast")
    val motionEffectsDefault = TokenId("motion.effectsDefault")
    val motionEffectsSlow = TokenId("motion.effectsSlow")
}

object BaselineTokens {
    val catalog: TokenCatalog =
        TokenCatalog(
            listOf(
                TokenDefinition(TokenIds.shapeExtraSmall, TokenType.SHAPE, TokenValue.Dimension(dp = 4f)),
                TokenDefinition(TokenIds.shapeSmall, TokenType.SHAPE, TokenValue.Dimension(dp = 8f)),
                TokenDefinition(TokenIds.shapeMedium, TokenType.SHAPE, TokenValue.Dimension(dp = 12f)),
                TokenDefinition(TokenIds.shapeLarge, TokenType.SHAPE, TokenValue.Dimension(dp = 16f)),
                TokenDefinition(TokenIds.shapeExtraLarge, TokenType.SHAPE, TokenValue.Dimension(dp = 28f)),
                TokenDefinition(TokenIds.spacingXs, TokenType.SPACING, TokenValue.Dimension(dp = 4f)),
                TokenDefinition(TokenIds.spacingSm, TokenType.SPACING, TokenValue.Dimension(dp = 8f)),
                TokenDefinition(TokenIds.spacingMd, TokenType.SPACING, TokenValue.Dimension(dp = 16f)),
                TokenDefinition(TokenIds.spacingLg, TokenType.SPACING, TokenValue.Dimension(dp = 24f)),
                TokenDefinition(TokenIds.spacingXl, TokenType.SPACING, TokenValue.Dimension(dp = 32f)),
                TokenDefinition(TokenIds.sizeTouchTarget, TokenType.SIZE, TokenValue.Dimension(dp = 48f)),
                TokenDefinition(TokenIds.strokeThin, TokenType.STROKE, TokenValue.Dimension(dp = 1f)),
                TokenDefinition(TokenIds.strokeMedium, TokenType.STROKE, TokenValue.Dimension(dp = 2f)),
                TokenDefinition(TokenIds.elevationLevel0, TokenType.ELEVATION, TokenValue.Dimension(dp = 0f)),
                TokenDefinition(TokenIds.elevationLevel1, TokenType.ELEVATION, TokenValue.Dimension(dp = 1f)),
                TokenDefinition(TokenIds.elevationLevel2, TokenType.ELEVATION, TokenValue.Dimension(dp = 3f)),
                TokenDefinition(TokenIds.elevationLevel3, TokenType.ELEVATION, TokenValue.Dimension(dp = 6f)),
                TokenDefinition(TokenIds.elevationLevel4, TokenType.ELEVATION, TokenValue.Dimension(dp = 8f)),
                TokenDefinition(TokenIds.elevationLevel5, TokenType.ELEVATION, TokenValue.Dimension(dp = 12f)),
                TokenDefinition(TokenIds.fontDisplay, TokenType.FONT, TokenValue.Font("roboto-flex")),
                TokenDefinition(TokenIds.fontTitle, TokenType.FONT, TokenValue.Font("roboto-flex")),
                TokenDefinition(TokenIds.fontBody, TokenType.FONT, TokenValue.Font("roboto-flex")),
                TokenDefinition(TokenIds.fontLyrics, TokenType.FONT, TokenValue.Font("roboto-flex")),
                TokenDefinition(TokenIds.surfaceStyle, TokenType.SURFACE, TokenValue.Surface(SurfaceStyle.SOLID)),
                TokenDefinition(
                    TokenIds.motionSpatialFast,
                    TokenType.MOTION,
                    TokenValue.Spring(stiffness = 800f, dampingRatio = 0.6f),
                ),
                TokenDefinition(
                    TokenIds.motionSpatialDefault,
                    TokenType.MOTION,
                    TokenValue.Spring(stiffness = 380f, dampingRatio = 0.8f),
                ),
                TokenDefinition(
                    TokenIds.motionSpatialSlow,
                    TokenType.MOTION,
                    TokenValue.Spring(stiffness = 200f, dampingRatio = 0.8f),
                ),
                TokenDefinition(
                    TokenIds.motionEffectsFast,
                    TokenType.MOTION,
                    TokenValue.Spring(stiffness = 3800f, dampingRatio = 1.0f),
                ),
                TokenDefinition(
                    TokenIds.motionEffectsDefault,
                    TokenType.MOTION,
                    TokenValue.Spring(stiffness = 1600f, dampingRatio = 1.0f),
                ),
                TokenDefinition(
                    TokenIds.motionEffectsSlow,
                    TokenType.MOTION,
                    TokenValue.Spring(stiffness = 800f, dampingRatio = 1.0f),
                ),
            ),
        )
}
