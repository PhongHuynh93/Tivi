package com.shared.ksp_compiler_shared.uiState.model

import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.PropertySpec

/**
 *
 * A wrapper class to contain constructor properties of the data class.
 *
 * @property parameterSpec The parameter specifications to construct the data class.
 * @property propertySpec The property specifications to construct the data class.
 */
internal data class ConstructorProperty(
    val parameterSpec: ParameterSpec,
    val propertySpec: PropertySpec
)
