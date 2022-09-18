package com.shared.ksp_compiler_shared.uiState.declaration

import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.shared.ksp_annotation.uiState.ExtensiveModel
import com.shared.ksp_annotation.uiState.ExtensiveSealed
import com.shared.ksp_compiler_shared.uiState.model.ExtensiveModelBag
import com.google.devtools.ksp.symbol.impl.binary.KSAnnotationDescriptorImpl

/**
 *
 * A declaration of the [ExtensiveSealed] annotated sealed classes/interfaces.
 * This contains the [KSClassDeclaration] of the sealed classes/interfaces that are annotated
 * with [ExtensiveSealed] and the compile-time value of the arguments.
 *
 * This class also extracts the values of arguments and put them together into the list of [ExtensiveModelBag]
 * that could be used in generators.
 *
 * @property declaration The sealed classes/interfaces declaration that are annotated with [ExtensiveSealed].
 */
internal class ExtensiveDeclaration(
    val declaration: KSClassDeclaration
) {

    val models: List<ExtensiveModelBag>

    init {
        // Extracts `models` from the `ExtensiveSealed` annotation.
        // TODO: replace with getAnnotationsByType method; https://github.com/google/ksp/issues/888
        val arguments = declaration.annotations.first {
            it.annotationType.resolve().declaration.qualifiedName?.asString() == ExtensiveSealed::class.qualifiedName
        }.arguments.first { it.name?.asString() == ExtensiveSealed.PARAM_MODELS }.value

        // Extract a list of KSType from the class type of the array of `ExtensiveModel`.
        val modelsKSTypesDescriptor =
            (arguments as ArrayList<*>).map { it as KSAnnotationDescriptorImpl }
        models = modelsKSTypesDescriptor.map { kSAnnotationDescriptor ->
            val name = kSAnnotationDescriptor.arguments.first { KSValueArgument ->
                KSValueArgument.name?.asString() == ExtensiveModel.PARAM_NAME
            }.value as String

            val type = kSAnnotationDescriptor.arguments.first { KSValueArgument ->
                KSValueArgument.name?.asString() == ExtensiveModel.PARAM_TYPE
            }.value as KSType

            ExtensiveModelBag(name = name, type = type)
        }.distinct()
    }
}
