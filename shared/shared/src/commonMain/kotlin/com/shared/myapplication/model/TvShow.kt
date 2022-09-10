package com.shared.myapplication.model

import com.shared.util.Immutable
import com.shared.util.Parcelable
import com.shared.util.Parcelize
import kotlinx.collections.immutable.ImmutableList

@Parcelize
@Immutable
data class TvShow(
    val id: String,
    val title: String,
    val overview: String,
    val language: String,
    val posterImageUrl: String,
    val backdropImageUrl: String,
    val year: String,
    val status: String?,
    val votes: Long,
    val numberOfSeasons: Long?,
    val numberOfEpisodes: Long?,
    val averageVotes: Double,
    val following: Boolean,
    val genreIds: List<String>,
    // TODO: how to parcelize the immutable list
//    val genreIds: ImmutableList<String>,
) : Parcelable
