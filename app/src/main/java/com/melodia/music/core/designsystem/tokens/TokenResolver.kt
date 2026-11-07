package com.melodia.music.core.designsystem.tokens

data class TokenLayers(
    val language: DesignLanguage,
    val userTheme: Map<TokenId, TokenValue> = emptyMap(),
    val scopedTheme: Map<TokenId, TokenValue> = emptyMap(),
    val componentOverrides: Map<String, Map<TokenId, TokenValue>> = emptyMap(),
)

class TokenResolver(
    private val catalog: TokenCatalog,
) {
    fun resolve(
        id: TokenId,
        layers: TokenLayers,
        componentId: String? = null,
    ): TokenValue? {
        val definition = catalog.find(id) ?: return null

        val candidate =
            findCandidate(definition, componentId?.let { layers.componentOverrides[it]?.get(id) })
                ?: findCandidate(definition, layers.scopedTheme[id])
                ?: findCandidate(definition, layers.userTheme[id])
                ?: findCandidate(definition, componentId?.let { layers.language.componentStyles[it]?.get(id) })
                ?: findCandidate(definition, layers.language.defaults[id])

        return candidate ?: definition.baseline
    }

    private fun findCandidate(
        definition: TokenDefinition,
        candidate: TokenValue?,
    ): TokenValue? = candidate?.takeIf { definition.type.accepts(it) }
}
