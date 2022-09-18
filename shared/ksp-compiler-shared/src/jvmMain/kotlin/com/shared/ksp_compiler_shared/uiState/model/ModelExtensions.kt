package com.shared.ksp_compiler_shared.uiState.model

import com.shared.ksp_compiler_shared.uiState.ksp.name
import java.util.Locale

/**
 *
 * Returns a model name of a declaration from the [ExtensiveModelBag].
 * If the bag includes valid [name] value, this property returns [name].
 * If the [name] value is not valid, this property returns name of the type.
 */
internal inline val ExtensiveModelBag.modelName: String
    get() = (name.ifEmpty { type.name }).replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(
            Locale.ROOT
        ) else it.toString()
    }
