package com.example.pekomon.todoapp.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.pekomon.todoapp.ui.views.splash.SplashScreen
import com.example.pekomon.todoapp.util.Consts

fun NavGraphBuilder.splashComposable(
    navigateToListScreen: () -> Unit
) {
    composable(
        route = Consts.SPLASH_SCREEN
    ) {
        SplashScreen(
            navigateToListScreen = navigateToListScreen
        )
    }
}