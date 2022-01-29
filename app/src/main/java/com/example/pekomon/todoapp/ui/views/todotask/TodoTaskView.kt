package com.example.pekomon.todoapp.ui.views.todotask

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import com.example.pekomon.todoapp.data.models.Priority
import com.example.pekomon.todoapp.data.models.ToDoTask
import com.example.pekomon.todoapp.util.Action

@Composable
fun TodoTaskView(
    task: ToDoTask?,
    navigateToListScreen: ((Action) -> Unit)
) {
    Scaffold(
        topBar = {
            TodoTaskAppBar(
                task = task,
                navigateToListScreen = navigateToListScreen
            )
        },
        content = { TaskContent(
            title = "Blag",
            onTitleChanged = {},
            description = "Do it",
            onDescriptionChanged = {},
            priority = Priority.MEDIUM,
            onPriorityChanged = {}
        ) }
    )
}