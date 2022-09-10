package com.shared.myapplication.data.service.tmdb

import com.shared.myapplication.data.service.model.GenresResponse
import com.shared.myapplication.data.service.model.SeasonResponse
import com.shared.myapplication.data.service.model.ShowDetailResponse
import com.shared.myapplication.data.service.model.TrailersResponse
import com.shared.myapplication.data.service.model.TvShowsResponse

interface TmdbService {

    suspend fun getTopRatedShows(page: Int): TvShowsResponse

    suspend fun getPopularShows(page: Int): TvShowsResponse

    suspend fun getSimilarShows(showId: String): TvShowsResponse

    suspend fun getRecommendations(showId: String): TvShowsResponse

    suspend fun getTvShowDetails(showId: String): ShowDetailResponse

    suspend fun getSeasonDetails(tvShowId: String, seasonNumber: Long): SeasonResponse

    suspend fun getTrendingShows(page: Int): TvShowsResponse

    suspend fun getAllGenres(): GenresResponse

    suspend fun getTrailers(showId: String): TrailersResponse
}
