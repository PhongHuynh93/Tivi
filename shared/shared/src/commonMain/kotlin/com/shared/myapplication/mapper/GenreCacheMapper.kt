package com.shared.myapplication.mapper

import com.shared.myapplication.model.GenreUIModel
import com.thomaskioko.tvmaniac.datasource.cache.Genre

fun List<Genre>.toGenreModelList(): List<GenreUIModel> {
    return map {
        GenreUIModel(
            id = it.id.toInt(),
            name = it.name
        )
    }
}
