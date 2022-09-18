package com.shared.ksp_annotation.uiState

import kotlin.reflect.KClass

/**
 * This contains information on extensive models like which model type and the name of the class.
 * Generally, the simple name of the [type] will be the model name, but you can overwrite by giving
 * the [name] value manually. If you give the [name] value manually, make sure that the model name will be
 * capitalized.
 */
@MustBeDocumented
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
public annotation class ExtensiveModel(
  val type: KClass<*>,
  val name: String = ""
) {

  public companion object {
    /** The name of the `type` parameter. */
    public const val PARAM_TYPE: String = "type"

    /** The name of the `name` parameter. */
    public const val PARAM_NAME: String = "name"
  }
}
