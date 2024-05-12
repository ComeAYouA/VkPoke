package com.example.vkpoke.core.utils

fun getPokemonImageUrl(pokemonId: String): String
    = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${pokemonId}.png"
