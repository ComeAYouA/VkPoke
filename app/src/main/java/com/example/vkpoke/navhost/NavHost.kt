package com.example.vkpoke.navhost

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.example.vkpoke.feature.details.navigation.navigateToPokemonDetails
import com.example.vkpoke.feature.details.navigation.pokemonDetailsScreen
import com.example.vkpoke.feature.feed.navigation.POKEMON_LIST_ROUTE
import com.example.vkpoke.feature.feed.navigation.pokemonListScreen

@Composable
fun RecipesBookNavHost(
    modifier: Modifier = Modifier,
    appUiState: AppUiState,
    startDestination: String = POKEMON_LIST_ROUTE,
){
    val navController = appUiState.navController

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ){
        pokemonListScreen(
            onPokemonClick = { id -> navController.navigateToPokemonDetails(id) }
        )
        pokemonDetailsScreen(
            onBackButtonPressed = { navController.popBackStack() }
        )
    }
}