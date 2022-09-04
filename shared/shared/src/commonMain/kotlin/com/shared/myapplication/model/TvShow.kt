package com.shared.myapplication.model

import com.shared.util.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class TvShow(
    val id: Long = 0,
    val title: String = "",
    val overview: String = "",
    val language: String = "",
    val posterImageUrl: String = "",
    val backdropImageUrl: String = "",
    val year: String = "",
    val status: String? = null,
    val votes: Long = 0,
    val numberOfSeasons: Int? = null,
    val numberOfEpisodes: Int? = null,
    val averageVotes: Double = 0.0,
    val following: Boolean = false,
    val genreIds: ImmutableList<Int> = persistentListOf(),
) {
    companion object {
        val EMPTY_SHOW = TvShow()
    }
}


