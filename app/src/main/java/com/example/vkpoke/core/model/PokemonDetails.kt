package com.example.vkpoke.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonDetails(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("height")
    val height: Int,
    @SerialName("weight")
    val weight : Int,
    @SerialName("stats")
    val stats: List<PokemonStat>,
    @SerialName("types")
    val types: List<TypeSlot>
)