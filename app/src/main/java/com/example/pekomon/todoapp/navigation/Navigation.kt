package com.example.pekomon.todoapp.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.pekomon.todoapp.navigation.destinations.listComposable
import com.example.pekomon.todoapp.navigation.destinations.taskComposable
import com.example.pekomon.todoapp.ui.viewmodel.TodoViewModel
import com.example.pekomon.todoapp.util.Consts.LIST_SCREEN

@ExperimentalMaterialApi
@Composable
fun SetupNavigation(
    navHostController: NavHostController,
    todoViewModel: TodoViewModel
) {
    val screen = remember(navHostController) {
        Screens(navHostController = navHostController)
    }
    
    NavHost(
        navController = navHostController,
        startDestination = LIST_SCREEN
    ) {
        listComposable(
            navigateToTaskScreen = screen.task,
            todoViewModel = todoViewModel
        )
        taskComposable(
            navigateToListScreen = screen.list
        )
    }
}

