package com.shared.myapplication.model

import com.shared.util.Immutable

@Immutable
data class TvGenre(
    val id: Int,
    var name: String
)
