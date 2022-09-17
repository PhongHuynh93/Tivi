package com.shared.myapplication.data.mapper

import com.shared.myapplication.model.TvFollowedShow
import com.shared.myapplication.model.TvShow
import com.thomaskioko.tvmaniac.datasource.cache.FollowedShow
import com.thomaskioko.tvmaniac.datasource.cache.Show
import kotlinx.collections.immutable.toImmutableList

fun List<Show>.toTvShowList(): List<TvShow> {
    return map { it.toTvShow() }
}

fun Show.toTvShow(): TvShow {
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
        numberOfSeasons = number_of_seasons,
        numberOfEpisodes = number_of_episodes
    )
}

fun FollowedShow.toTvFollowedShow(): TvFollowedShow {
    return TvFollowedShow(
        showId = id,
        following = following
    )
}