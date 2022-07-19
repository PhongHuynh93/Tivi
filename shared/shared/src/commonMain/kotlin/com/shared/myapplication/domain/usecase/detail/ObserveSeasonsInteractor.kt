package com.shared.myapplication.domain.usecase.detail

import com.shared.myapplication.data.seasons.SeasonsRepository
import com.shared.myapplication.model.SeasonUiModel
import com.shared.util.FlowInteractor
import com.thomaskioko.tvmaniac.datasource.cache.SelectSeasonsByShowId
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class ObserveSeasonsInteractor constructor(
    private val dispatcher: CoroutineDispatcher,
    private val repository: SeasonsRepository,
) : FlowInteractor<Long, List<SeasonUiModel>>(dispatcher) {

    override fun run(params: Long): Flow<List<SeasonUiModel>> =
        repository.observeShowSeasons(params)
            .map { it.data?.toSeasonsEntityList() ?: emptyList() }
            .distinctUntilChanged()
}

fun List<SelectSeasonsByShowId>.toSeasonsEntityList(): List<SeasonUiModel> {
    return map { it.toSeasonsEntity() }
}

fun SelectSeasonsByShowId.toSeasonsEntity(): SeasonUiModel {
    return SeasonUiModel(
        seasonId = id,
        tvShowId = tv_show_id,
        name = name,
        overview = overview,
        seasonNumber = season_number,
        episodeCount = epiosode_count.toInt()
    )
}
