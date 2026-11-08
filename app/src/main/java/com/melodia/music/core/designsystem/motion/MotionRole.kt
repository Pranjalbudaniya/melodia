package com.melodia.music.core.designsystem.motion

import com.melodia.music.core.designsystem.tokens.TokenId
import com.melodia.music.core.designsystem.tokens.TokenIds

/**
 * Spatial roles move position, size or shape and may overshoot;
 * effects roles change alpha or color and never overshoot.
 */
enum class MotionRole(
    val tokenId: TokenId,
) {
    SPATIAL_FAST(TokenIds.motionSpatialFast),
    SPATIAL_DEFAULT(TokenIds.motionSpatialDefault),
    SPATIAL_SLOW(TokenIds.motionSpatialSlow),
    EFFECTS_FAST(TokenIds.motionEffectsFast),
    EFFECTS_DEFAULT(TokenIds.motionEffectsDefault),
    EFFECTS_SLOW(TokenIds.motionEffectsSlow),
}
