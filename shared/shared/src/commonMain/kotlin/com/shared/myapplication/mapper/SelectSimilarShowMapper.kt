package com.shared.myapplication.mapper

import com.shared.myapplication.model.TvShow
import com.thomaskioko.tvmaniac.datasource.cache.SelectSimilarShows
import kotlinx.collections.immutable.toImmutableList

fun List<SelectSimilarShows>.toTvShowList(): List<TvShow> {
    return map { it.toTvShow() }
}

fun SelectSimilarShows.toTvShow(): TvShow {
    return TvShow(
        id = id,
        title = title,
        overview = description,
        language = language,
        posterImageUrl = poster_image_url,
        backdropImageUrl = backdrop_image_url,
        votes = votes,
        averageVotes = vote_average,
        genreIds = genre_ids.toImmutableList(),
        year = year,
        status = status,
        following = following
    )
}
