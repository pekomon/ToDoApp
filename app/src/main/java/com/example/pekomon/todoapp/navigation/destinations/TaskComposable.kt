package com.example.pekomon.todoapp.navigation.destinations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import com.google.accompanist.navigation.animation.composable
import androidx.navigation.navArgument
import com.example.pekomon.todoapp.ui.viewmodel.TodoViewModel
import com.example.pekomon.todoapp.ui.views.todotask.TodoTaskView
import com.example.pekomon.todoapp.util.Action
import com.example.pekomon.todoapp.util.Consts
import com.example.pekomon.todoapp.util.Consts.TASK_ARGUMENT_KEY
import com.example.pekomon.todoapp.util.Consts.TASK_ID_ADD_NEW

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.taskComposable(
    navigateToListScreen: ((Action) -> Unit),
    todoViewModel: TodoViewModel
) {
    composable(
        route = Consts.TASK_SCREEN,
        arguments = listOf(navArgument(TASK_ARGUMENT_KEY) {
            type = NavType.IntType
        }),
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { fullWidth -> -fullWidth },
                animationSpec = tween(
                    durationMillis = 300
                )
            )
        },
        exitTransition = {
            fadeOut(
                animationSpec = tween(
                    durationMillis = 300
                )
            )
        }
    ) { navBackStackEntry ->
        val taskId = navBackStackEntry.arguments?.getInt(TASK_ARGUMENT_KEY) ?: TASK_ID_ADD_NEW
        LaunchedEffect(key1 = taskId) {
            todoViewModel.getTask(taskId = taskId)
        }
        val selectedTask by todoViewModel.selectedTask.collectAsState()

        LaunchedEffect(key1 = selectedTask) {
            if (selectedTask != null || taskId == -1) {
                todoViewModel.updateUI(task = selectedTask)
            }
        }

        TodoTaskView(
            task = selectedTask,
            viewModel = todoViewModel,
            navigateToListScreen = navigateToListScreen
        )
    }
}
