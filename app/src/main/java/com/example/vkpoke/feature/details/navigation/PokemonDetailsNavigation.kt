package com.example.vkpoke.feature.details.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.vkpoke.feature.details.PokemonDetailsScreen
import java.net.URLDecoder
import java.net.URLEncoder
import kotlin.text.Charsets.UTF_8

const val POKEMON_ID = "recipeId"

private val URL_CHARACTER_ENCODING = UTF_8.name()

class PokemonDetailsArgs(val pokemonId: String) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(URLDecoder.decode(checkNotNull(savedStateHandle[POKEMON_ID]), URL_CHARACTER_ENCODING))
}

const val POKEMON_DETAILS_ROUTE = "pokemon_details_route"

fun NavController.navigateToPokemonDetails(pokemonId: String) {
    val encodedId = URLEncoder.encode(pokemonId, URL_CHARACTER_ENCODING)
    navigate("$POKEMON_DETAILS_ROUTE/$encodedId") {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.pokemonDetailsScreen(
    onBackButtonPressed: () -> Unit,
) {
    composable(
        route = "$POKEMON_DETAILS_ROUTE/{${POKEMON_ID}}",
        arguments = listOf(
            navArgument(POKEMON_ID) { type = NavType.StringType }
        ),
    ) {
        PokemonDetailsScreen(
            onBackButtonPressed = onBackButtonPressed
        )
    }
}