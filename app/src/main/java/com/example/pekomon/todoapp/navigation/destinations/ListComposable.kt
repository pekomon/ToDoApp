package com.example.pekomon.todoapp.navigation.destinations

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.pekomon.todoapp.ui.viewmodel.TodoViewModel
import com.example.pekomon.todoapp.ui.views.todolist.TodoListView
import com.example.pekomon.todoapp.util.Consts.LIST_ARGUMENT_KEY
import com.example.pekomon.todoapp.util.Consts.LIST_SCREEN
import com.example.pekomon.todoapp.util.toAction

@ExperimentalMaterialApi
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
        TodoListView(
            onListItemClicked =  navigateToTaskScreen,
            todoViewModel = todoViewModel
        )
    }
}