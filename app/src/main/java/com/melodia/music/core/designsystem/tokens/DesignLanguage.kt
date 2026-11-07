package com.melodia.music.core.designsystem.tokens

data class DesignLanguage(
    val id: String,
    val defaults: Map<TokenId, TokenValue> = emptyMap(),
    val componentStyles: Map<String, Map<TokenId, TokenValue>> = emptyMap(),
)
