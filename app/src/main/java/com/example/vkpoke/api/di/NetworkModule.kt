package com.example.vkpoke.api.di

import com.example.vkpoke.api.PokemonApi
import com.example.vkpoke.api.PokemonApiImpl
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

@InstallIn(SingletonComponent::class)
@Module
interface NetworkModule {
    @Binds
    fun bindPokemonApiImpl_to_PokemonApi(input: PokemonApiImpl): PokemonApi
    companion object{
        @Provides
        fun provideRetrofit(): Retrofit {
            val contentType = "application/json".toMediaType()
            val json = Json { ignoreUnknownKeys = true }
            return Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(json.asConverterFactory(contentType))
                .build()
        }
    }
}