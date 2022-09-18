package com.shared.ksp_compiler_shared.uiState.generator

import com.google.devtools.ksp.symbol.ClassKind
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.shared.ksp_compiler_shared.uiState.ksp.overrideAnnotations
import com.shared.ksp_compiler_shared.uiState.ksp.overrideModifiers
import com.shared.ksp_compiler_shared.uiState.ksp.overridePrimaryConstructor
import com.shared.ksp_compiler_shared.uiState.ksp.superClassName
import com.shared.ksp_compiler_shared.uiState.model.ExtensiveModelBag
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ksp.toClassName

/**
 *
 * A sealed sub-classes and interfaces specifications generator for each [parent]'s sub-class.
 *
 * @property parent The annotated declaration with the [ExtensiveSealed] annotation.
 * @property className The full-package name of the target class.
 * @property extensiveModelBag The compile-time information from the [ExtensiveModel] annotation.
 */
internal class SealedSubClassGenerator(
    private val parent: KSClassDeclaration,
    private val className: ClassName,
    private val extensiveModelBag: ExtensiveModelBag
) {

    private val sealedClasses: List<KSClassDeclaration> = parent.getSealedSubclasses().toList()

    fun generate(): List<TypeSpec> {
        val typeSpecs = mutableListOf<TypeSpec>()
        sealedClasses.forEach {
            val sealedSpec = when (it.classKind) {
                ClassKind.CLASS -> generateSealedClassTypeSpec(it)
                ClassKind.INTERFACE -> generateSealedInterfaceTypeSpec(it)
                ClassKind.OBJECT -> generateSealedObjectTypeSpec(it)
                else -> error("The ${it.simpleName} class must be one of sealed class/interface or object class.")
            }
            typeSpecs.add(sealedSpec)
        }
        return typeSpecs
    }

    private fun generateSealedClassTypeSpec(declaration: KSClassDeclaration): TypeSpec {
        val propertyGenerator =
            ExtensiveConstructorPropertyGenerator(
                declaration = declaration,
                extensiveModelBag = extensiveModelBag
            )
        return TypeSpec.classBuilder(declaration.toClassName())
            .overridePrimaryConstructor(propertyGenerator.generate())
            .addExtensiveSpecs(declaration)
            .build()
    }

    private fun generateSealedInterfaceTypeSpec(declaration: KSClassDeclaration): TypeSpec {
        return TypeSpec.interfaceBuilder(declaration.toClassName())
            .addExtensiveSpecs(declaration)
            .build()
    }

    private fun generateSealedObjectTypeSpec(declaration: KSClassDeclaration): TypeSpec {
        return TypeSpec.objectBuilder(declaration.toClassName())
            .addExtensiveSpecs(declaration)
            .build()
    }

    private fun TypeSpec.Builder.addExtensiveSpecs(declaration: KSClassDeclaration): TypeSpec.Builder =
        apply {
            overrideAnnotations(declaration)
            overrideModifiers(declaration)
            superClassName(parent, className)
        }
}
