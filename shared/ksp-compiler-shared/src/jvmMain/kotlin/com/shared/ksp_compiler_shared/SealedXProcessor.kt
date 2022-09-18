package com.shared.ksp_compiler_shared

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFile
import com.google.devtools.ksp.validate
import com.shared.ksp_annotation.uiState.ExtensiveSealed
import com.shared.ksp_compiler_shared.uiState.declaration.ExtensiveDeclaration
import com.shared.ksp_compiler_shared.uiState.validateModifierContainsSealed
import com.shared.ksp_compiler_shared.uiState.generator.ExtensiveFileGenerator
import com.shared.ksp_compiler_shared.uiState.model.modelName
import com.shared.ksp_compiler_shared.uiState.proguards.ProguardConfig
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.writeTo

/**
 *
 * A Kotlin Symbol Processor to run a sealed-extensive processor on compile time.
 * This is the main entry point of the symbol processor to generate decent sealed classes.
 *
 * SealedXProcessor visits all sealed classes and interfaces that are annotated with [ExtensiveSealed]
 * annotation and writes extended sealed classes depending on its metadata on compile time.
 *
 * The primary responsibility of this symbol processor is to reduce duplicated sealed classes
 * based on the same general models. So you don't need to write similar sealed classes and interfaces
 * for the same formats repeatedly.
 */
public class SealedXProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) : SymbolProcessor {

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val extensiveDeclarations = getDeclarationsAnnotatedWithExtensive(resolver)

        if (!extensiveDeclarations.iterator().hasNext()) return emptyList()

        extensiveDeclarations
            .filter { it.validate() && it.validateModifierContainsSealed(logger) }
            .forEach {
                val extensiveDeclaration = ExtensiveDeclaration(it)
                processSealedExtensive(extensiveDeclaration)
                generateExtensiveProguard(extensiveDeclaration)
            }

        return emptyList()
    }

    private fun processSealedExtensive(extensiveDeclaration: ExtensiveDeclaration) {
        val extensive = extensiveDeclaration.declaration
        logger.info("Processing ${extensive.simpleName.asString()}", extensive)

        val dependencySource = extensive.containingFile
        val sources = if (dependencySource != null) arrayOf(dependencySource) else arrayOf()

        val extensiveFileSpecs = generateProcessExtensiveSpecs(extensiveDeclaration)
        extensiveFileSpecs.forEach {
            it.writeTo(
                codeGenerator = codeGenerator,
                dependencies = Dependencies(aggregating = true, sources = sources)
            )
        }
    }

    private fun generateProcessExtensiveSpecs(extensiveDeclaration: ExtensiveDeclaration): List<FileSpec> {
        return extensiveDeclaration.models.map {
            ExtensiveFileGenerator(
                declaration = extensiveDeclaration.declaration,
                extensiveModelBag = it
            ).generate()
        }
    }

    private fun generateExtensiveProguard(extensiveDeclaration: ExtensiveDeclaration) {
        val extensive = extensiveDeclaration.declaration
        extensiveDeclaration.models.forEach {
            val extensiveName = "${it.modelName}${extensive.simpleName.asString()}"
            val proguardConfig = ProguardConfig(
                targetClass = extensive.toClassName(),
                targetClassKind = extensive.classKind,
                extensiveName = extensiveName,
                extensiveConstructorParams = extensive.primaryConstructor?.parameters?.map { parameter ->
                    parameter.type.resolve().toClassName().reflectionName()
                } ?: emptyList()
            )
            proguardConfig.writeTo(codeGenerator, extensive.containingFile)
        }
    }

    private fun getDeclarationsAnnotatedWithExtensive(resolver: Resolver): Sequence<KSClassDeclaration> =
        resolver.getSymbolsWithAnnotation(ExtensiveSealed::class.java.name)
            .filterIsInstance<KSClassDeclaration>()
            .distinct()

    private fun ProguardConfig.writeTo(codeGenerator: CodeGenerator, originatingKSFile: KSFile?) {
        codeGenerator
            .createNewFile(
                Dependencies(
                    false,
                    sources = originatingKSFile?.let { arrayOf(it) } ?: emptyArray()),
                packageName = "",
                fileName = outputFile,
                extensionName = ""
            )
            .bufferedWriter()
            .use(::writeTo)
    }
}
