package com.shared.myapplication.model

import com.shared.util.Immutable

@Immutable
data class TvFollowedShow(
    val showId: String,
    val following: Boolean
)