package com.shared.myapplication.data.service.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreResponse(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String
)
