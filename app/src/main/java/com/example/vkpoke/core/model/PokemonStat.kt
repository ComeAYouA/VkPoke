package com.example.vkpoke.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonStat(
    @SerialName("base_stat")
    val baseStat: Int,
    @SerialName("effort")
    val effort: Int,
    @SerialName("stat")
    val stat: Stat
)

@Serializable
data class Stat(
    @SerialName("name")
    val name: String
)

