package com.shared.myapplication.data.mapper

import com.shared.myapplication.model.LastAirEpisode
import com.shared.myapplication.model.TvGenre
import com.shared.myapplication.model.TvShow
import com.shared.util.network.Resource
import com.thomaskioko.tvmaniac.datasource.cache.AirEpisodesByShowId
import com.thomaskioko.tvmaniac.datasource.cache.Genre
import com.thomaskioko.tvmaniac.datasource.cache.SelectSimilarShows
import kotlinx.collections.immutable.toImmutableList

fun List<Genre>.toTvGenreList(genreIds: List<String>): List<TvGenre> =
    filter { genre ->
        genreIds.any { id -> genre.id == id }
    }.map {
        TvGenre(
            id = it.id.toInt(),
            name = it.name
        )
    }

fun List<AirEpisodesByShowId>.toLastAirEpisodeList(): List<LastAirEpisode> = map {
    LastAirEpisode(
        id = it.id,
        name = "S${it.season_number}.E${
            it.episode_number.toString()
                .padStart(2, '0')
        } • ${it.name}",
        overview = it.overview,
        airDate = it.air_date,
        episodeNumber = it.episode_number,
        seasonNumber = it.season_number,
        posterPath = it.still_path,
        voteAverage = it.vote_average,
        voteCount = it.vote_count,
        title = it.title
    )
}

fun List<SelectSimilarShows>.toSimilarShowList(): List<TvShow> = map {
    TvShow(
        id = it.id,
        title = it.title,
        overview = it.description,
        language = it.language,
        posterImageUrl = it.poster_image_url,
        backdropImageUrl = it.backdrop_image_url,
        votes = it.votes,
        averageVotes = it.vote_average,
        genreIds = it.genre_ids.toImmutableList(),
        year = it.year,
        status = it.status,
        following = it.following,
        numberOfSeasons = it.number_of_seasons,
        numberOfEpisodes = it.number_of_episodes
    )
}
