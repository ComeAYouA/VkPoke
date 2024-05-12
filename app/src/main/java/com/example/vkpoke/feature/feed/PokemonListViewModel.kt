package com.example.vkpoke.feature.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vkpoke.core.model.PokemonListItem
import com.example.vkpoke.core.usecase.LoadPokemonListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.vkpoke.core.model.Result
import com.example.vkpoke.core.usecase.SearchPokemonUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val loadPokemonListUseCase: LoadPokemonListUseCase,
    private val searchPokemonUseCase: SearchPokemonUseCase
): ViewModel() {
    private val _uiState: MutableStateFlow<PokemonListUiState> = MutableStateFlow(PokemonListUiState.Loading)
    private var pokemonList: List<PokemonListItem> = listOf()
    val uiState = _uiState.asStateFlow()
    init {
        loadPokemonList()
    }
    private val requestsContext = SupervisorJob() + Dispatchers.IO
    fun loadPokemonList(){
        _uiState.update {
            PokemonListUiState.Loading
        }

        viewModelScope.launch {
            val requestResult = loadPokemonListUseCase()

            _uiState.update {
                when (requestResult) {
                    is Result.Success -> {
                        pokemonList = requestResult.data

                        PokemonListUiState.Success(
                            data = requestResult.data
                        )
                    }

                    is Result.Error -> {
                        pokemonList = requestResult.data.orEmpty()

                        PokemonListUiState.Error(
                            requestResult.message
                        )
                    }
                }
            }
        }
    }

    fun onSearchQueryChanged(query: String){
        _uiState.update {
            PokemonListUiState.Loading
        }

        requestsContext.cancelChildren()

        if (query.isEmpty()){
            _uiState.update { PokemonListUiState.Success(data = pokemonList) }
            return
        }

        viewModelScope.launch(requestsContext) {
            val requestResult = searchPokemonUseCase(pokemonList, query)

            _uiState.update {
                when (requestResult) {
                    is Result.Success -> {
                        PokemonListUiState.Success(
                            data = requestResult.data
                        )
                    }

                    is Result.Error -> {
                        PokemonListUiState.Error(
                            requestResult.message
                        )
                    }
                }
            }
        }
    }

}