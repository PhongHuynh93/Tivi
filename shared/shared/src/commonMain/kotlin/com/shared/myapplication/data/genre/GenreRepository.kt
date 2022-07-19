package com.shared.myapplication.data.genre

import com.shared.util.network.Resource
import com.thomaskioko.tvmaniac.datasource.cache.Genre
import kotlinx.coroutines.flow.Flow

interface GenreRepository {

    fun observeGenres(): Flow<Resource<List<Genre>>>
}
