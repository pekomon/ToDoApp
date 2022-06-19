package com.example.pekomon.todoapp.navigation.destinations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import com.google.accompanist.navigation.animation.composable
import androidx.navigation.navArgument
import com.example.pekomon.todoapp.ui.viewmodel.TodoViewModel
import com.example.pekomon.todoapp.ui.views.todolist.TodoListView
import com.example.pekomon.todoapp.util.Consts.LIST_ARGUMENT_KEY
import com.example.pekomon.todoapp.util.Consts.LIST_SCREEN
import com.example.pekomon.todoapp.util.toAction

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
fun NavGraphBuilder.listComposable(
    navigateToTaskScreen: ((taskId: Int) -> Unit),
    todoViewModel: TodoViewModel
) {
    composable(
        route = LIST_SCREEN,
        arguments = listOf(navArgument(LIST_ARGUMENT_KEY) {
            type = NavType.StringType
        })
    ) { navBackStackEntry ->
        val action = navBackStackEntry.arguments?.getString(LIST_ARGUMENT_KEY).toAction()
        LaunchedEffect(key1 = action) {
            todoViewModel.action.value = action
        }
        val dbAction by todoViewModel.action
        TodoListView(
            action = dbAction,
            onListItemClicked =  navigateToTaskScreen,
            todoViewModel = todoViewModel
        )
    }
}