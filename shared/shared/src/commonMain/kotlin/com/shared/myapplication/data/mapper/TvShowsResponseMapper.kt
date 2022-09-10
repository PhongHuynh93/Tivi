package com.shared.myapplication.data.mapper

import com.shared.myapplication.data.service.model.GenreResponse
import com.shared.myapplication.data.service.model.ShowDetailResponse
import com.shared.myapplication.data.service.model.ShowResponse
import com.shared.util.StringUtil.formatPosterPath
import com.thomaskioko.tvmaniac.datasource.cache.Show
import kotlinx.datetime.toLocalDate

fun ShowResponse.toShow(): Show {
    return Show(
        id = id,
        title = name,
        description = overview,
        language = originalLanguage,
        poster_image_url = formatPosterPath(posterPath),
        backdrop_image_url = backdropPath.toImageUrl(posterPath),
        votes = voteCount.toLong(),
        vote_average = voteAverage,
        genre_ids = genreIds,
        year = formatDate(firstAirDate),
        status = "",
        popularity = popularity,
        following = false,
        number_of_seasons = numberOfSeasons?.toLong(),
        number_of_episodes = numberOfEpisodes?.toLong()
    )
}

fun ShowDetailResponse.toShow(): Show {
    return Show(
        id = id,
        title = name,
        description = overview,
        language = originalLanguage,
        poster_image_url = formatPosterPath(posterPath),
        backdrop_image_url = backdropPath.toImageUrl(posterPath),
        votes = voteCount.toLong(),
        vote_average = voteAverage,
        genre_ids = genres.toGenreIds(),
        year = formatDate(firstAirDate),
        status = status,
        popularity = popularity,
        following = false,
        number_of_seasons = numberOfSeasons.toLong(),
        number_of_episodes = numberOfEpisodes.toLong()
    )
}

fun List<GenreResponse>.toGenreIds(): List<String> = map { it.id }

private fun formatDate(dateString: String): String {
    return if (dateString.isNotBlank() && !dateString.contains("N/A"))
        dateString.toLocalDate().year.toString()
    else
        dateString
}

private fun String?.toImageUrl(posterPath: String?) =
    if (this.isNullOrEmpty()) formatPosterPath(posterPath)
    else formatPosterPath(this)
