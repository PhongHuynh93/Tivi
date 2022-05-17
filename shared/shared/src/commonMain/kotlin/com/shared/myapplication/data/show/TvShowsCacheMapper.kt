package com.shared.myapplication.data.show

import com.shared.myapplication.data.discover.remote.model.ShowResponse
import com.shared.util.StringUtil
import com.thomaskioko.tvmaniac.datasource.cache.SelectShows
import com.thomaskioko.tvmaniac.datasource.cache.Show
import kotlinx.datetime.toLocalDate

fun List<SelectShows>.toShowList(): List<Show> {
    return map { it.toShow() }
}

fun SelectShows.toShow(): Show {
    return Show(
        id = id,
        title = title,
        description = description,
        language = language,
        poster_image_url = poster_image_url,
        backdrop_image_url = backdrop_image_url,
        votes = votes,
        vote_average = vote_average,
        genre_ids = genre_ids,
        year = year,
        status = status,
        following = following,
        popularity = popularity,
        number_of_episodes = number_of_episodes,
        number_of_seasons = number_of_seasons
    )
}

fun ShowResponse.toShow(): Show {
    return Show(
        id = id.toLong(),
        title = name,
        description = overview,
        language = originalLanguage,
        poster_image_url = StringUtil.formatPosterPath(posterPath),
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

// TODO:: Move to common module
private fun formatDate(dateString: String): String {
    return if (dateString.isNotBlank() && !dateString.contains("N/A"))
        dateString.toLocalDate().year.toString()
    else
        dateString
}

private fun String?.toImageUrl(posterPath: String?) =
    if (this.isNullOrEmpty()) StringUtil.formatPosterPath(posterPath)
    else StringUtil.formatPosterPath(this)
