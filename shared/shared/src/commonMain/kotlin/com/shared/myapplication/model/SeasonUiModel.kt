package com.shared.myapplication.model

data class SeasonUiModel(
    val seasonId: Long,
    val tvShowId: Long,
    val name: String,
    val overview: String,
    val seasonNumber: Long,
    val episodeCount: Int
)
