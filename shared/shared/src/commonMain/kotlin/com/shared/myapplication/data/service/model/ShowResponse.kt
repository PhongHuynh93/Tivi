package com.shared.myapplication.data.service.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ShowResponse(
    @SerialName("backdrop_path") val backdropPath: String?,
    @SerialName("first_air_date") val firstAirDate: String = "N/A",
    @SerialName("genre_ids") val genreIds: List<String>,
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("origin_country") val originCountry: List<String>,
    @SerialName("original_language") val originalLanguage: String,
    @SerialName("original_name") val originalName: String,
    @SerialName("overview") val overview: String,
    @SerialName("popularity") val popularity: Double,
    @SerialName("poster_path") val posterPath: String?,
    @SerialName("vote_average") val voteAverage: Double,
    @SerialName("vote_count") val voteCount: Int,
    @SerialName("number_of_episodes") var numberOfEpisodes: Int? = null,
    @SerialName("number_of_seasons") var numberOfSeasons: Int? = null,
)
