package com.example.pekomon.todoapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.pekomon.todoapp.navigation.destinations.listComposable
import com.example.pekomon.todoapp.navigation.destinations.taskComposable
import com.example.pekomon.todoapp.util.Consts.LIST_SCREEN

@Composable
fun SetupNavigation(
    navHostController: NavHostController
) {
    val screen = remember(navHostController) {
        Screens(navHostController = navHostController)
    }
    
    NavHost(
        navController = navHostController,
        startDestination = LIST_SCREEN
    ) {
        listComposable(
            navigateToTaskScreen = screen.task
        )
        taskComposable(
            navigateToListScreen = screen.list
        )
    }
}

