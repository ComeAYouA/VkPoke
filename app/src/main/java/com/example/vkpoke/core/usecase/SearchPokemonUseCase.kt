package com.example.vkpoke.core.usecase

import com.example.vkpoke.core.model.PokemonListItem
import com.example.vkpoke.core.model.Result
import com.example.vkpoke.core.utils.NetworkExceptionConverter.convertToString
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class SearchPokemonUseCase @Inject constructor() {
    suspend operator fun invoke(
        data: List<PokemonListItem>,
        query: String,
        ): Result<List<PokemonListItem>>{
        return try {
            val resultList: Deferred<List<PokemonListItem>>
            coroutineScope {
                resultList = async { data.filter { it.name.contains(query, ignoreCase = true) } }
            }

            Result.Success(data = resultList.await())
        }catch (e: CancellationException){
            Result.Success(data = data)
        }catch (e: Exception){
            Result.Error(message = e.convertToString())
        }
    }
}