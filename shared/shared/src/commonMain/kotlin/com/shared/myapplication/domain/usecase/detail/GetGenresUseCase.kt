package com.shared.myapplication.domain.usecase.detail

import com.shared.myapplication.data.feature.genre.GenreRepository
import com.shared.myapplication.data.mapper.toGenreModelList
import com.shared.myapplication.model.TvGenre
import com.shared.myapplication.model.TvShow
import com.shared.util.FlowInteractor
import com.shared.util.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetGenresParam(val tvShow: TvShow)
class GetGenresUseCase constructor(
    dispatcher: CoroutineDispatcher,
    private val repository: GenreRepository,
) : UseCase<GetGenresParam, List<TvGenre>>(dispatcher) {

    override suspend fun execute(parameters: GetGenresParam): List<TvGenre> {
        return repository.getGenres(parameters.tvShow.genreIds)
    }
}
