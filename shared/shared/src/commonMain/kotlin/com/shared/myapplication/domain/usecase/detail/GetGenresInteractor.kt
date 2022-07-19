package com.shared.myapplication.domain.usecase.detail

import com.shared.myapplication.data.genre.GenreRepository
import com.shared.myapplication.mapper.toGenreModelList
import com.shared.myapplication.model.GenreUIModel
import com.shared.util.FlowInteractor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetGenresInteractor constructor(
    private val dispatcher: CoroutineDispatcher,
    private val repository: GenreRepository,
) : FlowInteractor<Unit, List<GenreUIModel>>(dispatcher) {

    override fun run(params: Unit): Flow<List<GenreUIModel>> = repository.observeGenres()
        .map { it.data?.toGenreModelList() ?: emptyList() }
}
