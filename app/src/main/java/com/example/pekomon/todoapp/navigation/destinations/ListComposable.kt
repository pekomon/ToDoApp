package com.example.pekomon.todoapp.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.pekomon.todoapp.ui.viewmodel.TodoViewModel
import com.example.pekomon.todoapp.ui.views.todolist.TodoListView
import com.example.pekomon.todoapp.util.Consts.LIST_ARGUMENT_KEY
import com.example.pekomon.todoapp.util.Consts.LIST_SCREEN

fun NavGraphBuilder.listComposable(
    navigateToTaskScreen: ((taskId: Int) -> Unit),
    todoViewModel: TodoViewModel
) {
    composable(
        route = LIST_SCREEN,
        arguments = listOf(navArgument(LIST_ARGUMENT_KEY) {
            type = NavType.StringType
        })
    ) {
        TodoListView(
            onClick =  navigateToTaskScreen,
            todoViewModel = todoViewModel
        )
    }
}