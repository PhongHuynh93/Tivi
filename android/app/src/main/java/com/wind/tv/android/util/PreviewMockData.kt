package com.wind.tv.android.util

import com.shared.myapplication.model.GenreUIModel
import com.shared.myapplication.model.LastAirEpisode
import com.shared.myapplication.model.SeasonUiModel
import com.shared.myapplication.model.TvShow
import com.shared.myapplication.viewmodel.detail.ShowDetailViewState
import kotlinx.collections.immutable.persistentListOf

val detailUiState = ShowDetailViewState.Success(
    tvShow = TvShow(
        id = 84958,
        title = "Loki",
        overview = "After stealing the Tesseract during the events of “Avengers: Endgame,” " +
            "an alternate version of Loki is brought to the mysterious Time Variance " +
            "Authority, a bureaucratic organization that exists outside of time and " +
            "space and monitors the timeline. They give Loki a choice: face being " +
            "erased from existence due to being a “time variant”or help fix " +
            "the timeline and stop a greater threat.",
        posterImageUrl = "/kEl2t3OhXc3Zb9FBh1AuYzRTgZp.jpg",
        backdropImageUrl = "/kEl2t3OhXc3Zb9FBh1AuYzRTgZp.jpg",
        language = "en",
        votes = 4958,
        averageVotes = 8.1,
        genreIds = persistentListOf(18, 10765),
        status = "Returning Series",
        year = "2024"
    ),
    tvSeasonUiModels = getTvSeasons(),
    genreUIList = getGenres(),
    lastAirEpList = getEpisodeList(),
)

private fun getTvSeasons() = persistentListOf(
    SeasonUiModel(
        seasonId = 114355,
        tvShowId = 84958,
        name = "Season 1",
        overview = "After stealing the Tesseract during the events of “Avengers: Endgame,” " +
            "an alternate version of Loki is brought to the mysterious Time Variance " +
            "Authority, a bureaucratic organization that exists outside of time and " +
            "space and monitors the timeline. They give Loki a choice: face being " +
            "erased from existence due to being a “time variant”or help fix " +
            "the timeline and stop a greater threat.",
        seasonNumber = 1,
        episodeCount = 6
    )
)

fun getGenres() = persistentListOf(
    GenreUIModel(
        id = 18,
        name = "Sci-Fi"
    ),
    GenreUIModel(
        id = 10765,
        name = "Action"
    )
)

fun getEpisodeList() = persistentListOf(
    LastAirEpisode(
        id = 2534997,
        name = "Glorious Purpose",
        overview = "After stealing the Tesseract in Avengers: Endgame, Loki lands before the Time" +
            " Variance Authority.",
        posterPath = "https://image.tmdb.org/t/p/original/gxh0k3aADsYkt9tgkfm2kGn2qQj.jpg",
        voteCount = 42,
        voteAverage = 6.429,
        seasonNumber = 1,
        episodeNumber = 1,
        airDate = "Wed, Apr 7, 2021",
        title = "Latest"
    ),
    LastAirEpisode(
        id = 2927202,
        name = "The Variant",
        overview = "Mobius puts Loki to work, but not everyone at TVA is thrilled about the God of " +
            "Mischief's presence.",
        posterPath = "https://image.tmdb.org/t/p/original/gqpcfkdmSsm6xiX2EsLkwUvA8g8.jpg",
        voteCount = 23,
        voteAverage = 7.6,
        seasonNumber = 1,
        episodeNumber = 1,
        airDate = "Wed, Apr 13, 2021",
        title = "Upcoming"
    )
)

val showList = persistentListOf(
    TvShow(
        id = 84958,
        title = "Loki",
        overview = "After stealing the Tesseract during the events of “Avengers: Endgame,” " +
            "an alternate version of Loki is brought to the mysterious Time Variance " +
            "Authority, a bureaucratic organization that exists outside of time and " +
            "space and monitors the timeline. They give Loki a choice: face being " +
            "erased from existence due to being a “time variant”or help fix " +
            "the timeline and stop a greater threat.",
        posterImageUrl = "/kEl2t3OhXc3Zb9FBh1AuYzRTgZp.jpg",
        backdropImageUrl = "/kEl2t3OhXc3Zb9FBh1AuYzRTgZp.jpg",
        language = "en",
        votes = 4958,
        averageVotes = 8.1,
        genreIds = persistentListOf(18, 10765),
        status = "Returning Series",
        year = "2024"
    ),
    TvShow(
        id = 84958,
        title = "Loki",
        overview = "After stealing the Tesseract during the events of “Avengers: Endgame,” " +
            "an alternate version of Loki is brought to the mysterious Time Variance " +
            "Authority, a bureaucratic organization that exists outside of time and " +
            "space and monitors the timeline. They give Loki a choice: face being " +
            "erased from existence due to being a “time variant”or help fix " +
            "the timeline and stop a greater threat.",
        posterImageUrl = "/kEl2t3OhXc3Zb9FBh1AuYzRTgZp.jpg",
        backdropImageUrl = "/kEl2t3OhXc3Zb9FBh1AuYzRTgZp.jpg",
        language = "en",
        votes = 4958,
        averageVotes = 8.1,
        genreIds = persistentListOf(18, 10765),
        status = "Returning Series",
        year = "2024"
    )
)
