package com.shared.myapplication.data.mapper

import com.shared.myapplication.data.service.model.LastEpisodeToAir
import com.shared.myapplication.data.service.model.NextEpisodeToAir
import com.shared.util.DateUtil.formatDateString
import com.thomaskioko.tvmaniac.datasource.cache.Last_episode
import com.thomaskioko.tvmaniac.datasource.cache.SelectShows
import com.thomaskioko.tvmaniac.datasource.cache.Show

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

fun NextEpisodeToAir.toAirEp(tvShowId: String) = Last_episode(
    id = id,
    show_id = tvShowId,
    name = name,
    overview = if (!overview.isNullOrEmpty()) overview else "TBA",
    air_date = formatDateString(dateString = airDate),
    episode_number = episodeNumber!!.toLong(),
    season_number = seasonNumber!!.toLong(),
    still_path = stillPath,
    vote_average = voteAverage,
    vote_count = voteCount?.toLong(),
    title = "Upcoming"
)

fun LastEpisodeToAir.toAirEp(tvShowId: String) = Last_episode(
    id = id,
    show_id = tvShowId,
    name = name,
    overview = if (!overview.isNullOrEmpty()) overview else "TBA",
    air_date = formatDateString(dateString = airDate),
    episode_number = episodeNumber!!.toLong(),
    season_number = seasonNumber!!.toLong(),
    still_path = stillPath,
    vote_average = voteAverage,
    vote_count = voteCount?.toLong(),
    title = "Latest Release"
)
