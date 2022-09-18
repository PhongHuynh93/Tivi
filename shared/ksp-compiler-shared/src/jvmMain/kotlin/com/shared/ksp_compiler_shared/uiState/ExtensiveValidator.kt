package com.shared.ksp_compiler_shared.uiState

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.Modifier
import com.shared.ksp_annotation.uiState.ExtensiveSealed

/**
 *
 * Validate [KSClassDeclaration] that is annotated with [ExtensiveSealed] is a sealed class/interface.
 *
 * @param logger A [KSPLogger] to log processor status.
 */
internal fun KSClassDeclaration.validateModifierContainsSealed(logger: KSPLogger): Boolean {
    if (!modifiers.contains(Modifier.SEALED)) {
        logger.error(
            "${ExtensiveSealed::class.simpleName} can't be attached to ${classKind.type}. " +
                "You can only attach to the sealed class or sealed interface "
        )
        return false
    }
    return true
}

public fun KSPLogger.anothers() {
}
