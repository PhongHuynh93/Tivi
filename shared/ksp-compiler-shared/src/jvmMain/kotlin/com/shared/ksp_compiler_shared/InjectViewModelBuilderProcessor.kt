package com.shared.ksp_compiler_shared

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.validate
import com.shared.ksp_annotation.KMPViewModel
import com.shared.ksp_annotation.ViewModelModule
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.MemberName
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.writeTo
import kotlin.reflect.KClass

class InjectViewModelBuilderProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) : SymbolProcessor {

    private val sealedXProcessor = SealedXProcessor(codeGenerator, logger)

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val viewModelModules = resolver.getSymbols(ViewModelModule::class)
            .map { it.toClassName() }
            .toList()
        if (viewModelModules.isNotEmpty()) {
            val viewModels = resolver.getSymbols(KMPViewModel::class)
                .map { it.toClassName() }
                .toList()
            // gen one file
            genFile(viewModelModules.first(), viewModels).writeTo(codeGenerator, Dependencies(true))
        }

        sealedXProcessor.process(resolver)

        return emptyList()
    }

    private fun genFile(module: ClassName, vms: List<ClassName>): FileSpec {
        val packageName = module.packageName
        val className = module.simpleName
        val funcName = className.replaceFirstChar { it.lowercase() }

        return FileSpec.builder(packageName, className)
            .addFunction(
                FunSpec.builder(funcName)
                    .returns(org.koin.core.module.Module::class)
                    .beginControlFlow("return %M", MemberName("org.koin.dsl", "module"))
                    .apply {
                        vms.forEach {
                            addStatement(
                                "%M { %T() }",
                                MemberName("com.shared.util", "viewModelDefinition"),
                                it
                            )
                        }
                    }
                    .endControlFlow()
                    .build()
            )
            .build()
    }

}

fun Resolver.getSymbols(cls: KClass<*>) =
    this.getSymbolsWithAnnotation(cls.qualifiedName.orEmpty())
        .filterIsInstance<KSClassDeclaration>()
