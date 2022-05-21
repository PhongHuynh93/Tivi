package com.shared.myapplication.mapper

import com.shared.myapplication.data.discover.remote.model.LastEpisodeToAir
import com.shared.myapplication.data.discover.remote.model.NextEpisodeToAir
import com.shared.myapplication.data.discover.remote.model.ShowResponse
import com.shared.util.DateUtil.formatDateString
import com.shared.util.StringUtil
import com.thomaskioko.tvmaniac.datasource.cache.Last_episode
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

fun NextEpisodeToAir.toAirEp(tvShowId: Long) = Last_episode(
    id = id!!.toLong(),
    show_id = tvShowId,
    name = name,
    overview = if (!overview.isNullOrEmpty()) overview!! else "TBA",
    air_date = formatDateString(dateString = airDate),
    episode_number = episodeNumber!!.toLong(),
    season_number = seasonNumber!!.toLong(),
    still_path = stillPath,
    vote_average = voteAverage,
    vote_count = voteCount?.toLong(),
    title = "Upcoming"
)

fun LastEpisodeToAir.toAirEp(tvShowId: Long) = Last_episode(
    id = id!!.toLong(),
    show_id = tvShowId,
    name = name,
    overview = if (!overview.isNullOrEmpty()) overview!! else "TBA",
    air_date = formatDateString(dateString = airDate),
    episode_number = episodeNumber!!.toLong(),
    season_number = seasonNumber!!.toLong(),
    still_path = stillPath,
    vote_average = voteAverage,
    vote_count = voteCount?.toLong(),
    title = "Latest Release"
)
