package com.example.vkpoke.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonListItem(
    @SerialName("name")
    val name: String,
    @SerialName("url")
    val url: String
){
    val id: String
        get() = url.split("/").let { it[it.lastIndex - 1] }
}