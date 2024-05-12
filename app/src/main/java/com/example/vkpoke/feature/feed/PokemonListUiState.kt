package com.example.vkpoke.feature.feed

import com.example.vkpoke.core.model.PokemonListItem

sealed interface PokemonListUiState {
    object Loading: PokemonListUiState
    data class Success(
        val data: List<PokemonListItem>
    ): PokemonListUiState
    data class Error(
        val message: String
    ): PokemonListUiState
}