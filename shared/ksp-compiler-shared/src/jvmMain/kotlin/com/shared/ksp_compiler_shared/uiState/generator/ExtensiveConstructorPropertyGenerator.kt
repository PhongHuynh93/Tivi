package com.shared.ksp_compiler_shared.uiState.generator

import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSValueParameter
import com.shared.ksp_annotation.uiState.Extensive
import com.shared.ksp_compiler_shared.uiState.model.ConstructorProperty
import com.shared.ksp_compiler_shared.uiState.model.ExtensiveModelBag
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.ksp.toAnnotationSpec
import com.squareup.kotlinpoet.ksp.toTypeName

/**
 *
 * A primary constructor of sealed sub-classes and interfaces specifications generator.
 *
 * @property declaration The annotated declaration with the [ExtensiveSealed] annotation.
 * @property extensiveModelBag The compile-time information from the [ExtensiveModel] annotation.
 */
internal class ExtensiveConstructorPropertyGenerator(
  private val declaration: KSClassDeclaration,
  private val extensiveModelBag: ExtensiveModelBag
) {

  fun generate(): List<ConstructorProperty> {
    val constructorProperties: MutableList<ConstructorProperty> = mutableListOf()
    val constructorParameters = declaration.primaryConstructor?.parameters
    constructorParameters?.forEach { parameter ->
      val parameterSpec = ParameterSpec.builder(
        name = parameter.name?.asString() ?: let {
          val index = constructorParameters.indexOf(parameter).takeIf { it != -1 } ?: 0
          "param$index"
        },
        type = parameter.extensiveTypeName
      ).build()

      val property = PropertySpec.builder(parameterSpec.name, parameterSpec.type)
        .addAnnotations(parameter.annotations.map { it.toAnnotationSpec() }.toList())
        .initializer("%N", parameterSpec)
        .mutable(parameter.isVar)
        .build()

      val constructorProperty = ConstructorProperty(parameterSpec, property)
      constructorProperties.add(constructorProperty)
    }
    return constructorProperties
  }

  private inline val KSValueParameter.extensiveTypeName: TypeName
    get() = if (type.resolve().declaration.qualifiedName?.asString() == Extensive::class.qualifiedName) {
      extensiveModelBag.type.toTypeName()
    } else {
      type.toTypeName()
    }
}
