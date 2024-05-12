package com.example.vkpoke.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TypeSlot(
    @SerialName("slot")
    val slot: Int,
    @SerialName("type")
    val type: Type
)

@Serializable
data class Type(
    @SerialName("name")
    val name: String,
    @SerialName("url")
    val url: String
)
