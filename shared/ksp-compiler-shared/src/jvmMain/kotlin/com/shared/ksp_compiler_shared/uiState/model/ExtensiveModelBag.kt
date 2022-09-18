package com.shared.ksp_compiler_shared.uiState.model

import com.google.devtools.ksp.symbol.KSType

/**
 *
 * ExtensiveModelBag is a data wrapper class to contains [ExtensiveModel]'s metadata.
 *
 * @param name The name value from the [ExtensiveModel].
 * @param type The [KSType] of the type from the [ExtensiveModel].
 */
internal data class ExtensiveModelBag(
    val name: String,
    val type: KSType
)
