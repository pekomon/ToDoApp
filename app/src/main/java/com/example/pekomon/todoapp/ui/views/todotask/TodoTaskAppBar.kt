package com.example.pekomon.todoapp.ui.views.todotask

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.pekomon.todoapp.R
import com.example.pekomon.todoapp.data.models.Priority
import com.example.pekomon.todoapp.data.models.ToDoTask
import com.example.pekomon.todoapp.extensions.topAppBarBackgroundColor
import com.example.pekomon.todoapp.extensions.topAppBarContentColor
import com.example.pekomon.todoapp.util.Action

@Composable
fun TodoTaskAppBar(
    task: ToDoTask?,
    navigateToListScreen: ((Action) -> Unit)
) {
    if (task == null) {
        NewTodoTaskAppBar(navigateToListScreen = navigateToListScreen)
    } else {
        ExistingTodoTaskAppBar(
            task = task,
            navigateToListScreen = navigateToListScreen
        )
    }

}

@Composable
fun NewTodoTaskAppBar(
    navigateToListScreen: ((Action) -> Unit)
) {
    TopAppBar(
        navigationIcon = {
            BackAction(onBackClicked = navigateToListScreen)
        },
        title = {
            Text(
                text = stringResource(id = R.string.appbar_back_add_task),
                color = MaterialTheme.colors.topAppBarContentColor
            )
        },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
        actions = {
            AddAction(onAddClicked = navigateToListScreen)
        }
    )
}

@Composable
fun ExistingTodoTaskAppBar(
    task: ToDoTask,
    navigateToListScreen: ((Action) -> Unit)
) {
    TopAppBar(
        navigationIcon = {
            CloseAction(onCloseClicked = navigateToListScreen)
        },
        title = {
            Text(
                text = task.title,
                color = MaterialTheme.colors.topAppBarContentColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
        actions = {
            DeleteAction(onDeleteClicked = navigateToListScreen)
            UpdateAction(onUpdateClicked = navigateToListScreen)
        }
    )
}

@Composable
fun BackAction(
    onBackClicked: ((Action) -> Unit)
) {
    IconButton(onClick = { onBackClicked(Action.NO_ACTION) }) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = stringResource(id = R.string.appbar_back_arrow),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
    }
}

@Composable
fun CloseAction(
    onCloseClicked: ((Action) -> Unit)
) {
    IconButton(onClick = { onCloseClicked(Action.NO_ACTION) }) {
        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = stringResource(id = R.string.close_icon_content_description),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
    }
}

@Composable
fun DeleteAction(
    onDeleteClicked: ((Action) -> Unit)
) {
    IconButton(onClick = { onDeleteClicked(Action.DELETE) }) {
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(id = R.string.delete_icon_content_description),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
    }
}

@Composable
fun UpdateAction(
    onUpdateClicked: ((Action) -> Unit)
) {
    IconButton(onClick = { onUpdateClicked(Action.UPDATE) }) {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = stringResource(id = R.string.update_icon_content_description),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
    }
}

@Composable
fun AddAction(
    onAddClicked: ((Action) -> Unit)
) {
    IconButton(onClick = { onAddClicked(Action.ADD) }) {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = stringResource(id = R.string.appbar_back_add_task),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
    }
}

@Preview
@Composable
fun PreviewNewTodoTaskAppBar() {
    NewTodoTaskAppBar(
        navigateToListScreen = {}
    )
}

@Preview
@Composable
fun PreviewExistingTodoTaskAppBar() {
    ExistingTodoTaskAppBar(
        task = ToDoTask(
            id = 0,
            title = "Remember something important",
            description = "Nice thing to remember: Have a cup of coffee",
            priority = Priority.LOW
        ),
        navigateToListScreen = {}
    )
}
