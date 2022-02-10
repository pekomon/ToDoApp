package com.example.pekomon.todoapp.ui.views.todotask

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.example.pekomon.todoapp.data.models.Priority
import com.example.pekomon.todoapp.data.models.ToDoTask
import com.example.pekomon.todoapp.ui.viewmodel.TodoViewModel
import com.example.pekomon.todoapp.util.Action

@Composable
fun TodoTaskView(
    task: ToDoTask?,
    viewModel: TodoViewModel,
    navigateToListScreen: ((Action) -> Unit)
) {
    val title by viewModel.title
    val description by viewModel.description
    val priority by viewModel.priority

    Scaffold(
        topBar = {
            TodoTaskAppBar(
                task = task,
                navigateToListScreen = navigateToListScreen
            )
        },
        content = {
            TaskContent(
                title = title,
                onTitleChanged = {
                    viewModel.updateTitle(it)
                },
                description = description,
                onDescriptionChanged = {
                    viewModel.description.value = it
                },
                priority = priority,
                onPriorityChanged = {
                    viewModel.priority.value = it
                }
            )
        }
    )
}