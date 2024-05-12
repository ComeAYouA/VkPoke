package com.example.vkpoke.feature.details

import com.example.vkpoke.core.model.PokemonDetails

sealed interface PokemonDetailsUiState {
    object Loading: PokemonDetailsUiState
    data class Success(
        val data: PokemonDetails
    ): PokemonDetailsUiState
    data class Error(
        val message: String
    ): PokemonDetailsUiState
}