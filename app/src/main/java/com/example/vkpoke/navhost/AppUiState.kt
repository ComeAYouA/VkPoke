package com.example.vkpoke.navhost

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun rememberAppUiState(
    navController: NavHostController = rememberNavController()
): AppUiState {
    return remember{
        AppUiState(navController)
    }
}

@Stable
data class AppUiState(
    val navController: NavHostController
)