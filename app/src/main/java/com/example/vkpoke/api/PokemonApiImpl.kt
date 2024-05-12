package com.example.vkpoke.api

import com.example.vkpoke.core.model.PokemonDetails
import com.example.vkpoke.core.model.PokemonListResponse
import retrofit2.Retrofit
import javax.inject.Inject

class PokemonApiImpl @Inject constructor(
    private val retrofit: Retrofit
): PokemonApi {
    private val api = retrofit.create(PokemonApi::class.java)
    override suspend fun getPokemonList(): PokemonListResponse = api.getPokemonList()
    override suspend fun getPokemonDetails(id: String): PokemonDetails = api.getPokemonDetails(id)
}