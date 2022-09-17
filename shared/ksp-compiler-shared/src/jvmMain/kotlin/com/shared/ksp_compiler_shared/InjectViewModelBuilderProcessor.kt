package com.shared.ksp_compiler_shared

import com.google.devtools.ksp.closestClassDeclaration
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.validate
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.writeTo
import kotlin.reflect.KClass

class BuilderProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) : SymbolProcessor {

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val factories = getFactories(resolver)
        val data = getElements(resolver, factories)
        data.forEach {
            genFile(it.key, it.value).writeTo(codeGenerator, Dependencies(true))
        }
        return emptyList()
    }

    private fun getFactories(resolver: Resolver): Set<ClassName> {
        return resolver.getSymbols(AutoFactory::class)
            .map { it.toClassName() }
            .toSet()
    }

    private fun genFile(key: ClassName, list: List<ClassName>): FileSpec {
        val packageName = key.packageName
        val funcName = key.simpleName + "Factory"
        val enumName = key.simpleName + "Type"

        return FileSpec.builder(packageName, funcName)
            .addType(
                TypeSpec.enumBuilder(enumName)
                    .apply {
                        list.forEach {
                            addEnumConstant(it.simpleName.uppercase())
                        }
                    }
                    .build())
            .addFunction(
                FunSpec.builder(funcName)
                    .addParameter("key", ClassName(packageName, enumName))
                    .returns(key)
                    .beginControlFlow("return when (key)")
                    .apply {
                        list.forEach {
                            addStatement("${enumName}.${it.simpleName.uppercase()} -> %T()", it)
                        }
                    }
                    .endControlFlow()
                    .build())
            .build()
    }

    private fun getElements(
        resolver: Resolver,
        factories: Set<ClassName>
    ): Map<ClassName, List<ClassName>> {
        val result = mutableMapOf<ClassName, MutableList<ClassName>>()
        factories.forEach { result[it] = mutableListOf() }
        resolver.getSymbols(AutoElement::class)
            .forEach { d ->
                d.superTypes
                    .map { it.resolve().declaration.closestClassDeclaration()?.toClassName() }
                    .filter { result.containsKey(it) }
                    .forEach { name ->
                        result[name]?.add(d.toClassName())
                    }
            }
        return result
    }
}

fun Resolver.getSymbols(cls: KClass<*>) =
    this.getSymbolsWithAnnotation(cls.qualifiedName.orEmpty())
        .filterIsInstance<KSClassDeclaration>()
        .filter(KSNode::validate)
