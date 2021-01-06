package me.dicoding.bajp.reel.core.data.network.json

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenresJson(
    @SerialName("id") val id: Long,
    @SerialName("name") val name: String
)
