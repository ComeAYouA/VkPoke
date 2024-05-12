package com.example.vkpoke.feature.feed.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.example.vkpoke.feature.feed.PokemonListScreen

const val POKEMON_LIST_ROUTE = "pokemon_list_route"
private const val URI_PATTERN_LINK = "https://com.exaple.vkpoke/pokemon_list"

fun NavController.navigateToFeedScreen(navOptions: NavOptions)
        = this.navigate(POKEMON_LIST_ROUTE, navOptions)

fun NavGraphBuilder.pokemonListScreen(
    onPokemonClick: (String) -> Unit
) {
    composable(
        route = POKEMON_LIST_ROUTE,
        deepLinks = listOf(
            navDeepLink { uriPattern =  URI_PATTERN_LINK},
        ),
    ) {
        PokemonListScreen(
            onPokemonClick = onPokemonClick
        )
    }
}