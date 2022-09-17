package com.shared.myapplication.data.feature.genre

import com.shared.myapplication.data.mapper.toTvGenreList
import com.shared.myapplication.data.service.tmdb.TmdbService
import com.shared.myapplication.model.TvGenre
import com.thomaskioko.tvmaniac.datasource.cache.Genre
import org.koin.core.annotation.Single

interface GenreRepository {

    suspend fun getGenres(genreIds: List<String>): List<TvGenre>
}

@Single
class GenreRepositoryImpl(
    private val apiService: TmdbService,
    private val genreCache: GenreCache,
) : GenreRepository {

    override suspend fun getGenres(genreIds: List<String>): List<TvGenre> {
        return apiService.getAllGenres().genres.map { response ->
            Genre(
                id = response.id,
                name = response.name
            )
        }.also {
            genreCache.insert(it)
        }.toTvGenreList(genreIds)
    }
}
