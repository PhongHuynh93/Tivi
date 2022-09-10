package com.shared.myapplication.data.mapper

import com.thomaskioko.tvmaniac.datasource.cache.Genre

fun List<Genre>.toGenreModelList(): List<com.shared.myapplication.model.TvGenre> {
    return map {
        com.shared.myapplication.model.TvGenre(
            id = it.id.toInt(),
            name = it.name
        )
    }
}
