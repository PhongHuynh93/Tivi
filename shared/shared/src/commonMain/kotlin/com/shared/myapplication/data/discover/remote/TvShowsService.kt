package com.shared.myapplication.data.discover.remote

import com.shared.myapplication.data.discover.remote.model.GenresResponse
import com.shared.myapplication.data.discover.remote.model.SeasonResponse
import com.shared.myapplication.data.discover.remote.model.ShowDetailResponse
import com.shared.myapplication.data.discover.remote.model.TrailersResponse
import com.shared.myapplication.data.discover.remote.model.TvShowsResponse

interface TvShowsService {

    suspend fun getTopRatedShows(page: Int): TvShowsResponse

    suspend fun getPopularShows(page: Int): TvShowsResponse

    suspend fun getSimilarShows(showId: Long): TvShowsResponse

    suspend fun getRecommendations(showId: Long): TvShowsResponse

    suspend fun getTvShowDetails(showId: Long): ShowDetailResponse

    suspend fun getSeasonDetails(tvShowId: Long, seasonNumber: Long): SeasonResponse

    suspend fun getTrendingShows(page: Int): TvShowsResponse

    suspend fun getAllGenres(): GenresResponse

    suspend fun getTrailers(showId: Int): TrailersResponse
}
