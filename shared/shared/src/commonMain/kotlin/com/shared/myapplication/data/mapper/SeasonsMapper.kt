package com.shared.myapplication.data.mapper

import com.shared.myapplication.data.service.model.ShowDetailResponse
import com.shared.myapplication.model.TvSeason
import com.thomaskioko.tvmaniac.datasource.cache.Season

fun ShowDetailResponse.toTvSeasonList(): List<TvSeason> {
    return seasons.map { season ->
        TvSeason(
            id = season.id,
            tvShowId = id,
            name = season.name,
            overview = season.overview,
            seasonNumber = season.seasonNumber,
            episodeCount = season.episodeCount
        )
    }
}

fun ShowDetailResponse.toSeasonCacheList(): List<Season> {
    return seasons.map { seasonResponse ->
        Season(
            id = seasonResponse.id,
            tv_show_id = id,
            season_number = seasonResponse.seasonNumber.toLong(),
            name = seasonResponse.name,
            overview = seasonResponse.overview,
            epiosode_count = seasonResponse.episodeCount.toLong()
        )
    }
}
