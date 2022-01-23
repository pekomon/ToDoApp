package com.example.pekomon.todoapp.navigation.destinations

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.pekomon.todoapp.ui.viewmodel.TodoViewModel
import com.example.pekomon.todoapp.ui.views.todotask.TodoTaskView
import com.example.pekomon.todoapp.util.Action
import com.example.pekomon.todoapp.util.Consts
import com.example.pekomon.todoapp.util.Consts.TASK_ARGUMENT_KEY
import com.example.pekomon.todoapp.util.Consts.TASK_ID_ADD_NEW

fun NavGraphBuilder.taskComposable(
    navigateToListScreen: ((Action) -> Unit),
    todoViewModel: TodoViewModel
) {
    composable(
        route = Consts.TASK_SCREEN,
        arguments = listOf(navArgument(Consts.TASK_ARGUMENT_KEY) {
            type = NavType.IntType
        })
    ) { navBackStackEntry ->
        val taskId = navBackStackEntry.arguments?.getInt(TASK_ARGUMENT_KEY) ?: TASK_ID_ADD_NEW
        todoViewModel.getTask(taskId = taskId)
        val selectedTask by todoViewModel.selectedTask.collectAsState()
        
        TodoTaskView(
            task = selectedTask,
            navigateToListScreen = navigateToListScreen
        )
    }
}
