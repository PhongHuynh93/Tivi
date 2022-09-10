package com.shared.myapplication.data.feature.genre

import com.thomaskioko.tvmaniac.datasource.cache.Genre
import kotlinx.coroutines.flow.Flow

interface GenreCache {

    fun insert(genre: Genre)

    fun insert(genreList: List<Genre>)

    fun getGenreById(genreId: String): Genre

    fun getGenres(): Flow<List<Genre>>
}
