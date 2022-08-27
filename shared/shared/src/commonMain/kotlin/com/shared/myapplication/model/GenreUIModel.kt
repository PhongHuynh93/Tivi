package com.shared.myapplication.model

import com.shared.util.Immutable

@Immutable
data class GenreUIModel(
    val id: Int,
    var name: String
)
