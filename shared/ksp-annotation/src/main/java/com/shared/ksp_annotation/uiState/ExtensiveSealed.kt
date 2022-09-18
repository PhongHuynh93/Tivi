package com.shared.ksp_annotation.uiState

import com.shared.ksp_annotation.uiState.ExtensiveModel

/**
 * This is the main trigger of the Kotlin Symbol Processor to run a sealed-extensive processor on compile time.
 * [ExtensiveSealed] must be annotated to sealed classes or interfaces, which should be a general model
 * to generate extensive sealed classes and interfaces.
 *
 * [ExtensiveSealed] receives an array of [ExtensiveModel] annotations, which includes the extensive model
 * type([ExtensiveModel.type]) and name([ExtensiveModel.name]). For the more information about [ExtensiveModel],
 * @see [ExtensiveModel].
 *
 * ```
 * @ExtensiveSealed(models = [ExtensiveModel(type = PokemonExtensive::class), ExtensiveModel(type = AnotherModel::class)])
 * sealed interface UIState {
 *   data class Success(val data: Extensive) : UIState
 *   object Loading : UIState
 *   object Error : UIState
 * }
 * ```
 *
 * The SealedX compiler will generate two types of classes for `PokemonExtensive` and `AnotherModel`.
 *
 * For the PokemonExtensive type:
 *
 * ```
 * sealed interface PokemonExtensiveUIState {
 *   data class Success(val data: PokemonExtensive) : PokemonExtensiveUIState
 *   object Loading : PokemonExtensiveUIState
 *   object Error : PokemonExtensiveUIState
 * }
 * ```
 *
 * You can also change the prefix of the generated class name by giving the [ExtensiveModel.name].
 *
 *```
 * @ExtensiveSealed(models = [ExtensiveModel(type = PokemonExtensive::class, name="Pokemon")])
 * sealed interface UIState {
 *   data class Success(val data: Extensive) : UIState
 *   object Loading : UIState
 *   object Error : UIState
 * }
 *```
 *
 * Then it will be generated like example below:
 *
 * ```
 * sealed interface PokemonUIState {
 *   data class Success(val data: PokemonExtensive) : PokemonUIState
 *   object Loading : PokemonUIState
 *   object Error : PokemonUIState
 * }
 * ```
 */
@MustBeDocumented
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
public annotation class ExtensiveSealed(
  val models: Array<ExtensiveModel> = []
) {

  public companion object {
    /** The name of the `models` parameter. */
    public const val PARAM_MODELS: String = "models"
  }
}
