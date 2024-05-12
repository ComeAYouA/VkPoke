package com.example.vkpoke.api

import com.example.vkpoke.core.model.PokemonDetails
import com.example.vkpoke.core.model.PokemonListResponse
import retrofit2.http.GET
import retrofit2.http.Path


interface PokemonApi {
    @GET("pokemon?limit=100000&offset=0")
    suspend fun getPokemonList(): PokemonListResponse
    @GET("pokemon/{id}")
    suspend fun getPokemonDetails(
        @Path("id") id: String
    ): PokemonDetails
}