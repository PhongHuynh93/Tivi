package com.shared.myapplication.data.service.tmdb

import com.shared.myapplication.data.service.model.GenresResponse
import com.shared.myapplication.data.service.model.SeasonResponse
import com.shared.myapplication.data.service.model.ShowDetailResponse
import com.shared.myapplication.data.service.model.TrailersResponse
import com.shared.myapplication.data.service.model.TvShowsResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.koin.core.annotation.Single

@Single
class TmdbServiceImpl(
    private val httpClient: HttpClient,
) : TmdbService {

    override suspend fun getTopRatedShows(page: Int): TvShowsResponse =
        httpClient.get("3/tv/top_rated") {
            parameter("page", page)
            parameter("sort_by", "popularity.desc")
        }.body()

    override suspend fun getPopularShows(page: Int): TvShowsResponse =
        httpClient.get("3/tv/popular") {
            parameter("page", page)
            parameter("sort_by", "popularity.desc")
        }.body()

    override suspend fun getSimilarShows(showId: String): TvShowsResponse =
        httpClient.get("3/tv/$showId/recommendations")
            .body()

    override suspend fun getRecommendations(showId: String): TvShowsResponse =
        httpClient.get("3/tv/$showId/recommendations")
            .body()

    override suspend fun getTvShowDetails(showId: String): ShowDetailResponse =
        httpClient.get("3/tv/$showId")
            .body()

    override suspend fun getSeasonDetails(tvShowId: String, seasonNumber: Long): SeasonResponse =
        httpClient.get("3/tv/$tvShowId/season/$seasonNumber")
            .body()

    override suspend fun getTrendingShows(page: Int): TvShowsResponse = httpClient
        .get("3/trending/tv/week") {
            parameter("page", page)
        }.body()

    override suspend fun getAllGenres(): GenresResponse = httpClient
        .get("3/genre/tv/list")
        .body()

    override suspend fun getTrailers(showId: String): TrailersResponse = httpClient
        .get("3/tv/$showId/videos")
        .body()
}
