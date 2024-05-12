package com.example.vkpoke.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonListResponse(
    @SerialName("results")
    val results: List<PokemonListItem>
)
