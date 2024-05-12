package com.example.vkpoke.feature.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vkpoke.core.model.Result
import com.example.vkpoke.core.usecase.LoadPokemonDetailsUseCase
import com.example.vkpoke.feature.details.navigation.PokemonDetailsArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val loadPokemonDetailsUseCase: LoadPokemonDetailsUseCase
): ViewModel() {
    private val pokemonDetailsArgs: PokemonDetailsArgs = PokemonDetailsArgs(savedStateHandle)
    private val pokemonId = pokemonDetailsArgs.pokemonId

    private val _uiState: MutableStateFlow<PokemonDetailsUiState> = MutableStateFlow(PokemonDetailsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        loadPokemonDetails()
    }

    fun loadPokemonDetails(){
        _uiState.update {
            PokemonDetailsUiState.Loading
        }

        viewModelScope.launch {
            val requestResult = loadPokemonDetailsUseCase(pokemonId)

            _uiState.update {
                when (requestResult) {
                    is Result.Success -> {
                        PokemonDetailsUiState.Success(
                            data = requestResult.data
                        )
                    }

                    is Result.Error -> {
                        PokemonDetailsUiState.Error(
                            requestResult.message
                        )
                    }
                }
            }
        }
    }
}