package com.shared.myapplication.domain.usecase.show

import com.shared.myapplication.data.feature.discover.DiscoverRepository
import com.shared.myapplication.model.ShowCategory
import com.shared.myapplication.model.TvShow
import com.shared.util.UseCase
import kotlinx.coroutines.CoroutineDispatcher

class GetTopRatedShowsParam()
class GetTopRatedShowsUseCase constructor(
    dispatcher: CoroutineDispatcher,
    private val repository: DiscoverRepository
) : UseCase<GetTopRatedShowsParam, List<TvShow>>(dispatcher) {

    override suspend fun execute(parameters: GetTopRatedShowsParam): List<TvShow> {
        return repository.getShowsByCategoryID(ShowCategory.TOP_RATED.type)
    }

}
