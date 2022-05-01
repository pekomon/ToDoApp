package com.example.pekomon.todoapp.navigation

import androidx.navigation.NavHostController
import com.example.pekomon.todoapp.util.Action
import com.example.pekomon.todoapp.util.Consts.LIST_SCREEN
import com.example.pekomon.todoapp.util.Consts.SPLASH_SCREEN

class Screens(
    navHostController: NavHostController
) {
    val splash: () -> Unit = {
        navHostController.navigate(route = "list/${Action.NO_ACTION}") {
            popUpTo(SPLASH_SCREEN) { inclusive = true }
        }
    }
    val list: ((Action) -> Unit) = { action ->
        navHostController.navigate("list/${action.name}") {
            popUpTo(LIST_SCREEN) {
                inclusive = true
            }
        }
    }
    val task: ((Int) -> Unit) = { taskId ->
        navHostController.navigate("task/${taskId}")
    }
}
