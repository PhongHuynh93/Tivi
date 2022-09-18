package com.shared.ksp_annotation.uiState

/**
 * An interface that can be used to represent extensive types of the sealed classes and interfaces
 * that are annotated with [ExtensiveSealed].
 *
 * This interface type will be replaced by the [ExtensiveModel.type] in generated codes on compile time.
 * [Extensive] can be used to represent the data type for the primary constructor in the data class.
 *
 * ```
 * @ExtensiveSealed(
 *   models = [
 *     ExtensiveModel(type = PokemonExtensive::class)
 *   ]
 * )
 * sealed interface UIState {
 *   data class Success(val data: Extensive) : UIState
 *   object Loading : UIState
 *   object Error : UIState
 * }
 * ```
 *
 * The [Extensive] in the `Success` data class will be replaced with `PokemonExtensive` in the new
 * generated class.
 *
 * ```
 * data class Success(val data: PokemonExtensive) : PokemonExtensiveUIState
 * ```
 */
public interface Extensive
