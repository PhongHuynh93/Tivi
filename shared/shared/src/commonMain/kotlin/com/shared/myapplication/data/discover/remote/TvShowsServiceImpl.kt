package com.shared.myapplication.data.discover.remote

import com.shared.myapplication.data.discover.remote.model.GenresResponse
import com.shared.myapplication.data.discover.remote.model.SeasonResponse
import com.shared.myapplication.data.discover.remote.model.ShowDetailResponse
import com.shared.myapplication.data.discover.remote.model.TrailersResponse
import com.shared.myapplication.data.discover.remote.model.TvShowsResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class TvShowsServiceImpl(
    private val httpClient: HttpClient,
) : TvShowsService {

    override suspend fun getTopRatedShows(page: Int): TvShowsResponse {
        return httpClient.get("3/tv/top_rated") {
            parameter("page", page)
            parameter("sort_by", "popularity.desc")
        }.body()
    }

    override suspend fun getPopularShows(page: Int): TvShowsResponse {
        return httpClient.get("3/tv/popular") {
            parameter("page", page)
            parameter("sort_by", "popularity.desc")
        }.body()
    }

    override suspend fun getSimilarShows(showId: Long): TvShowsResponse {
        return httpClient.get("3/tv/$showId/recommendations").body()
    }

    override suspend fun getRecommendations(showId: Long): TvShowsResponse {
        return httpClient.get("3/tv/$showId/recommendations").body()
    }

    override suspend fun getTvShowDetails(showId: Long): ShowDetailResponse {
        return httpClient.get("3/tv/$showId").body()
    }

    override suspend fun getSeasonDetails(tvShowId: Long, seasonNumber: Long): SeasonResponse {
        return httpClient.get("3/tv/$tvShowId/season/$seasonNumber").body()
    }

    override suspend fun getTrendingShows(page: Int): TvShowsResponse {
        return httpClient.get("3/trending/tv/week") {
            parameter("page", page)
        }.body()
    }

    override suspend fun getAllGenres(): GenresResponse {
        return httpClient.get("3/genre/tv/list").body()
    }

    override suspend fun getTrailers(showId: Int): TrailersResponse {
        return httpClient.get("3/tv/$showId/videos").body()
    }
}
