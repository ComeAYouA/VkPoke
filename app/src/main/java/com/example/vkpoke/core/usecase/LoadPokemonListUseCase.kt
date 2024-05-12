package com.example.vkpoke.core.usecase

import com.example.vkpoke.api.PokemonApi
import com.example.vkpoke.core.model.PokemonListItem
import com.example.vkpoke.core.model.Result
import com.example.vkpoke.core.utils.NetworkExceptionConverter.convertToString
import javax.inject.Inject

class LoadPokemonListUseCase @Inject constructor(
    private val pokemonApi: PokemonApi
) {
    suspend operator fun invoke(): Result<List<PokemonListItem>>{
        return try {
            val pokemonList = pokemonApi.getPokemonList()
            Result.Success(pokemonList.results)
        }catch (e: Exception){
            Result.Error(message = e.convertToString())
        }
    }
}