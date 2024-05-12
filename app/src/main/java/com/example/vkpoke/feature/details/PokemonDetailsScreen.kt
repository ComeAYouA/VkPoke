package com.example.vkpoke.feature.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.vkpoke.core.model.PokemonDetails
import com.example.vkpoke.core.model.PokemonStat
import com.example.vkpoke.core.model.TypeSlot
import com.example.vkpoke.core.ui.ErrorSnackBar
import com.example.vkpoke.core.utils.getPokemonImageUrl
import com.example.vkpoke.core.utils.typeColorMap

@Composable
fun PokemonDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: PokemonDetailsViewModel = hiltViewModel(),
    onBackButtonPressed: () -> Unit = {}
){
    val uiState = viewModel.uiState.collectAsState()

    Scaffold { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
        ){
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TopBar(
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 12.dp),
                    uiState = uiState.value,
                    onBackButtonPressed = onBackButtonPressed
                )

                uiState.value.let { state ->
                    when(state){
                        PokemonDetailsUiState.Loading -> {
                            CircularProgressIndicator(modifier = Modifier)
                        }
                        is PokemonDetailsUiState.Success -> {
                            Pokemon(
                                modifier = Modifier,
                                pokemonDetails = state.data
                            )
                        }
                        else -> {}
                    }
                }
            }

            uiState.value.let { state ->
                if (state is PokemonDetailsUiState.Error){
                    ErrorSnackBar(
                        modifier = Modifier.align(Alignment.BottomCenter),
                        message = state.message,
                        onRetry = {viewModel.loadPokemonDetails()}
                    )
                }
            }
        }
    }
}


@Composable
internal fun TopBar(
    uiState: PokemonDetailsUiState,
    modifier: Modifier = Modifier,
    onBackButtonPressed: () -> Unit
){

    Box(
        modifier = modifier
            .fillMaxWidth(),
    ){
        IconButton(
            modifier = Modifier.align(Alignment.CenterStart),
            onClick = onBackButtonPressed
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = "back to feed"
            )
        }

        when(uiState){
            is PokemonDetailsUiState.Success -> {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .align(Alignment.Center),
                    text = uiState.data.name,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Black,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            else -> {}
        }
    }
}


@Composable
fun Pokemon(
    modifier: Modifier = Modifier,
    pokemonDetails: PokemonDetails
){
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        pokemonImage(pokemonId = pokemonDetails.id.toString())
        basicInfo(height = pokemonDetails.height, weight = pokemonDetails.weight)
        types(pokemonDetails.types)
        stats(stats = pokemonDetails.stats)
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
fun LazyListScope.pokemonImage(
    pokemonId: String
){
    item{
        GlideImage(
            modifier = Modifier
                .padding(top = 18.dp)
                .size(230.dp),
            model = getPokemonImageUrl(pokemonId = pokemonId),
            contentDescription = null
        )
    }
}

fun LazyListScope.basicInfo(
    height: Int,
    weight: Int
){
    item {
        Row {
            Text(
                modifier = Modifier.padding(end = 10.dp),
                text = "Height: ${height}",
                fontSize = 18.sp
            )
            Text(text = "Weight: ${weight}", fontSize = 18.sp)
        }
    }
}

fun LazyListScope.types(
    types: List<TypeSlot>
){
    item {
        LazyRow (
            modifier = Modifier.padding(top = 6.dp)
        ){
            items(types){ type ->
                Box(
                    modifier = Modifier
                        .padding(end = 6.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            typeColorMap[type.type.name]
                                ?: MaterialTheme.colorScheme.surfaceContainer
                        )
                        .padding(4.dp)
                ){
                    Text(type.type.name)
                }
            }
        }
    }
}

fun LazyListScope.stats(
    stats: List<PokemonStat>
){
    item {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .padding(6.dp)
        ) {
            Text("Stats:", fontSize = 24.sp)
            Column {
                stats.forEach { stat ->
                    Text(
                        "${stat.stat.name}:" +
                                " ${stat.baseStat} " +
                                if (stat.effort != 0)"± ${stat.effort}" else ""
                    )
                }
            }
        }
    }
}