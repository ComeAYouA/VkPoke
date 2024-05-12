package com.example.vkpoke.core.usecase

import android.util.Log
import com.example.vkpoke.api.PokemonApi
import com.example.vkpoke.core.model.PokemonDetails
import javax.inject.Inject
import com.example.vkpoke.core.model.Result
import com.example.vkpoke.core.utils.NetworkExceptionConverter.convertToString

class LoadPokemonDetailsUseCase @Inject constructor(
    private val pokemonApi: PokemonApi
) {
    suspend operator fun invoke(pokemonId: String): Result<PokemonDetails>{
        return try {
            val pokemonDetails = pokemonApi.getPokemonDetails(pokemonId)

            Log.d("myTag", pokemonDetails.toString())
            Result.Success(data = pokemonDetails)
        }catch (e: Exception){
            Result.Error(message = e.convertToString())
        }
    }
}