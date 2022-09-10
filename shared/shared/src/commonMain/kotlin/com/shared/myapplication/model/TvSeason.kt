package com.shared.myapplication.model

import com.shared.util.Immutable

@Immutable
data class TvSeason(
    val id: String,
    val tvShowId: String,
    val name: String,
    val overview: String,
    val seasonNumber: Int,
    val episodeCount: Int
)
