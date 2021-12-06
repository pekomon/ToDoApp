package com.example.pekomon.todoapp.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.pekomon.todoapp.util.Action
import com.example.pekomon.todoapp.util.Consts

fun NavGraphBuilder.taskComposable(
    navigateToListScreen: ((Action) -> Unit)
) {
    composable(
        route = Consts.TASK_SCREEN,
        arguments = listOf(navArgument(Consts.TASK_ARGUMENT_KEY) {
            type = NavType.IntType
        })
    ) {
    }
}