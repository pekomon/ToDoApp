package com.example.pekomon.todoapp.ui.views.todotask

import android.content.Context
import android.widget.Toast
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.pekomon.todoapp.R
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

    val context = LocalContext.current
    val emptyContentText = stringResource(id = R.string.toast_strings_are_empty)

    Scaffold(
        topBar = {
            TodoTaskAppBar(
                task = task,
                navigateToListScreen = { action ->
                    if (action == Action.NO_ACTION) {
                        navigateToListScreen(action)
                    } else {
                        if (viewModel.validateFields()) {
                            navigateToListScreen(action)
                        } else {
                            displayToast(
                                context = context,
                                text = emptyContentText
                            )
                        }
                    }
                }
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

private fun displayToast(context: Context, text: String) {
    Toast.makeText(
        context,
        text,
        Toast.LENGTH_SHORT
    ).show()
}
