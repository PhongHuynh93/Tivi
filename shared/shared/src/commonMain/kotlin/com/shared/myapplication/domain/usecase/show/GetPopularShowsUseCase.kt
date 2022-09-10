package com.shared.myapplication.domain.usecase.show

import com.shared.myapplication.data.feature.discover.DiscoverRepository
import com.shared.myapplication.model.ShowCategory
import com.shared.myapplication.model.TvShow
import com.shared.util.UseCase
import kotlinx.coroutines.CoroutineDispatcher

class GetPopularShowsParam()
class GetPopularShowsUseCase constructor(
    dispatcher: CoroutineDispatcher,
    private val repository: DiscoverRepository
) : UseCase<GetPopularShowsParam, List<TvShow>>(dispatcher) {

    override suspend fun execute(parameters: GetPopularShowsParam): List<TvShow> {
        return repository.getShowsByCategoryID(ShowCategory.POPULAR.type)
    }

}
