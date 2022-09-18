package com.shared.ksp_compiler_shared.uiState.generator

import com.google.devtools.ksp.symbol.ClassKind
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.shared.ksp_compiler_shared.uiState.ksp.overrideAnnotations
import com.shared.ksp_compiler_shared.uiState.ksp.overrideModifiers
import com.shared.ksp_compiler_shared.uiState.model.ExtensiveModelBag
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.buildCodeBlock
import com.squareup.kotlinpoet.ksp.toClassName

/**
 *
 * A sealed class and interface specifications generator for each [declaration].
 *
 * @property declaration The annotated declaration with the [ExtensiveSealed] annotation.
 * @property className The full-package name of the target class.
 * @property extensiveModelBag The compile-time information from the [ExtensiveModel] annotation.
 */
internal class ExtensiveSealedClassGenerator(
    private val declaration: KSClassDeclaration,
    private val className: ClassName,
    private val extensiveModelBag: ExtensiveModelBag
) {

    fun generate(): TypeSpec {
        return when (declaration.classKind) {
            ClassKind.CLASS -> {
                val subClassGenerator = SealedSubClassGenerator(
                    parent = declaration,
                    className = className,
                    extensiveModelBag = extensiveModelBag
                )
                buildExtensiveSealedClassTypeSpec(subClassGenerator)
            }
            ClassKind.INTERFACE -> {
                val subClassGenerator = SealedSubClassGenerator(
                    parent = declaration,
                    className = className,
                    extensiveModelBag = extensiveModelBag
                )
                buildExtensiveSealedInterfaceTypeSpec(subClassGenerator)
            }
            else -> error("The class annotated with `ExtensiveSealed` must be a sealed class or interface.")
        }
    }

    private fun buildExtensiveSealedClassTypeSpec(
        subClassGenerator: SealedSubClassGenerator
    ): TypeSpec {
        return TypeSpec.classBuilder(className)
            .addExtensiveSpecs(subClassGenerator)
            .build()
    }

    private fun buildExtensiveSealedInterfaceTypeSpec(
        subClassGenerator: SealedSubClassGenerator
    ): TypeSpec {
        return TypeSpec.interfaceBuilder(className)
            .addExtensiveSpecs(subClassGenerator)
            .build()
    }

    private fun TypeSpec.Builder.addExtensiveSpecs(
        subClassGenerator: SealedSubClassGenerator
    ): TypeSpec.Builder = apply {
        declaration.superTypes.firstOrNull()?.let {
            superclass(it.resolve().toClassName())
        }
        overrideAnnotations(declaration)
        overrideModifiers(declaration)
        addTypes(subClassGenerator.generate())
        addKdoc("An extensive sealed ${declaration.classKind.type} by [${declaration.toClassName()}].")
    }
}
