package com.melodia.music.core.designsystem.tokens

@JvmInline
value class TokenId(
    val value: String,
)

enum class SurfaceStyle {
    SOLID,
    TONAL,
    FROSTED,
    LIQUID,
}

enum class TokenType {
    COLOR,
    SHAPE,
    SIZE,
    SPACING,
    STROKE,
    ELEVATION,
    FONT,
    ICON,
    MOTION,
    HAPTIC,
    SURFACE,
    ;

    fun accepts(value: TokenValue): Boolean =
        when (this) {
            COLOR -> value is TokenValue.Color
            SHAPE, SIZE, SPACING, STROKE, ELEVATION -> value is TokenValue.Dimension
            FONT -> value is TokenValue.Font
            ICON -> value is TokenValue.Icon
            MOTION -> value is TokenValue.Spring
            HAPTIC -> value is TokenValue.Haptic
            SURFACE -> value is TokenValue.Surface
        }
}

sealed interface TokenValue {
    data class Color(
        val light: Long,
        val dark: Long,
    ) : TokenValue

    data class Dimension(
        val dp: Float,
    ) : TokenValue

    data class Font(
        val fontId: String,
    ) : TokenValue

    data class Icon(
        val setId: String,
    ) : TokenValue

    data class Spring(
        val stiffness: Float,
        val dampingRatio: Float,
    ) : TokenValue

    data class Haptic(
        val enabled: Boolean,
        val strength: Float,
    ) : TokenValue

    data class Surface(
        val style: SurfaceStyle,
    ) : TokenValue
}

data class TokenDefinition(
    val id: TokenId,
    val type: TokenType,
    val baseline: TokenValue,
)

class TokenCatalog(
    val definitions: List<TokenDefinition>,
) {
    fun find(id: TokenId): TokenDefinition? = definitions.firstOrNull { it.id == id }
}
