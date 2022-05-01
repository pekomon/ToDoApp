package com.example.pekomon.todoapp.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import com.example.pekomon.todoapp.navigation.destinations.listComposable
import com.example.pekomon.todoapp.navigation.destinations.splashComposable
import com.example.pekomon.todoapp.navigation.destinations.taskComposable
import com.example.pekomon.todoapp.ui.viewmodel.TodoViewModel
import com.example.pekomon.todoapp.util.Consts.SPLASH_SCREEN
import com.google.accompanist.navigation.animation.AnimatedNavHost

@OptIn(ExperimentalAnimationApi::class)
@ExperimentalMaterialApi
@Composable
fun SetupNavigation(
    navHostController: NavHostController,
    todoViewModel: TodoViewModel
) {
    val screen = remember(navHostController) {
        Screens(navHostController = navHostController)
    }

    AnimatedNavHost(
        navController = navHostController,
        startDestination = SPLASH_SCREEN
    ) {
        splashComposable(
            navigateToListScreen = screen.splash
        )
        listComposable(
            navigateToTaskScreen = screen.task,
            todoViewModel = todoViewModel
        )
        taskComposable(
            navigateToListScreen = screen.list,
            todoViewModel = todoViewModel
        )
    }
}

