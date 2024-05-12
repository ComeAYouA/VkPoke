package com.example.vkpoke.feature.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.vkpoke.core.model.PokemonListItem
import com.example.vkpoke.core.ui.ErrorSnackBar
import com.example.vkpoke.core.utils.getPokemonImageUrl

@Composable
fun PokemonListScreen(
    modifier: Modifier = Modifier,
    viewModel: PokemonListViewModel = hiltViewModel(),
    onPokemonClick: (String) -> Unit = {}
){

    val uiState = viewModel.uiState.collectAsState()

    Scaffold(
        modifier = modifier
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()){
            Column(
                modifier = Modifier
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SearchBar(
                    modifier = Modifier
                        .padding(horizontal = 10.dp, vertical = 12.dp),
                    onSearchQueryChanged = { query -> viewModel.onSearchQueryChanged(query) }
                )

                uiState.value.let { state ->
                    when(state){
                        PokemonListUiState.Loading -> {
                            CircularProgressIndicator()
                        }
                        is PokemonListUiState.Success -> {
                            Feed(
                                state.data,
                                onPokemonClick = onPokemonClick
                            )
                        }
                        else -> {}
                    }
                }
            }

            uiState.value.let { state ->
                if (state is PokemonListUiState.Error) {
                    ErrorSnackBar(
                        modifier = Modifier.align(Alignment.BottomCenter),
                        message = state.message,
                        onRetry = {viewModel.loadPokemonList()}
                    )
                }
            }
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onSearchQueryChanged: (String) -> Unit = {},
    searchQuery: MutableState<String> = rememberSaveable { mutableStateOf("") }
){
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Absolute.SpaceEvenly
    ){
        BasicTextField(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(12.dp))
                .height(40.dp)
                .padding(8.dp)
                .fillMaxWidth(),
            value = searchQuery.value,
            onValueChange = {
                searchQuery.value = it
                onSearchQueryChanged(it)
            },
            singleLine = true,
            textStyle = TextStyle().copy(
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 18.sp
            ),
            cursorBrush = Brush.linearGradient(
                0.0f to MaterialTheme.colorScheme.onBackground,
                1.0f to MaterialTheme.colorScheme.onBackground
            ),
            decorationBox = { innerTextField ->
                Row{
                    Icon(
                        modifier = Modifier
                            .padding(end = 8.dp),
                        imageVector = Icons.Default.Search,
                        contentDescription = ""
                    )
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                    ){
                        innerTextField()
                    }
                }
            }
        )
    }
}

@Composable
fun Feed(
    data: List<PokemonListItem>,
    onPokemonClick: (String) -> Unit
){
    
    if (data.isEmpty()){
        Text(text = "Таких покемонов нет")
    }

    
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(140.dp),
        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 18.dp),
        verticalItemSpacing = 18.dp,
        horizontalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        items(data){ pokemon ->
            Pokemon(
                pokemon = pokemon,
                onPokemonClick = onPokemonClick
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun Pokemon(
    pokemon: PokemonListItem,
    onPokemonClick: (String) -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .clickable { onPokemonClick(pokemon.id) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        GlideImage(
            modifier = Modifier.height(140.dp),
            model = getPokemonImageUrl(pokemon.id),
            contentDescription = "",
            contentScale = ContentScale.Inside
        )

        Text(
            modifier = Modifier,
            text = pokemon.name,
            fontSize = 18.sp
        )
        Text(
            "#${pokemon.id}"
        )
    }
}



