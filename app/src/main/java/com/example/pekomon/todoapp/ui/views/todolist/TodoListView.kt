package com.example.pekomon.todoapp.ui.views.todolist

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.pekomon.todoapp.R
import com.example.pekomon.todoapp.extensions.fabBackgroundColor
import com.example.pekomon.todoapp.ui.viewmodel.TodoViewModel
import com.example.pekomon.todoapp.util.Action
import com.example.pekomon.todoapp.util.Consts.TASK_ID_ADD_NEW
import com.example.pekomon.todoapp.util.SearchAppBarState
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun TodoListView(
    onListItemClicked: ((taskId: Int) -> Unit),
    todoViewModel: TodoViewModel
) {
    LaunchedEffect(key1 = true) {
        todoViewModel.getAllTasks()
    }

    val action by todoViewModel.action

    val allTasks by todoViewModel.allTasks.collectAsState()
    val searchedTasks by todoViewModel.searchedTasks.collectAsState()
    val searchAppBarState: SearchAppBarState by todoViewModel.searchAppBarState
    val searchTextState: String by todoViewModel.searchTextState

    val scaffoldState = rememberScaffoldState()


    HandleDbActionAndDisplaySnackBar(
        scaffoldState = scaffoldState,
        handleDatabaseActions = { todoViewModel.handleDatabaseActions(action) },
        onUndoClicked = {
                        todoViewModel.action.value = it
        },
        taskTitle = todoViewModel.title.value,
        action = action
    )

    Scaffold(
        scaffoldState = scaffoldState,
        content = {
            TodoListContent(
                allTasks = allTasks,
                searchedTasks = searchedTasks,
                searchAppBarState = searchAppBarState,
                onItemClicked = onListItemClicked
            )
        },
        topBar = {
            TodoListAppBar(
                todoViewModel = todoViewModel,
                searchAppBarState = searchAppBarState,
                searchTextState = searchTextState
            )
                 },
        floatingActionButton = {
            ListFab(onClick = onListItemClicked)
        }
    )
}

@Composable
fun ListFab(
    onClick: ((taskId: Int) -> Unit)
) {
    FloatingActionButton(
        onClick = { onClick(TASK_ID_ADD_NEW) },
        backgroundColor = MaterialTheme.colors.fabBackgroundColor
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(id = R.string.fab_add_new_task_content_description),
            tint = Color.White
        )
    }
}

@Composable
fun HandleDbActionAndDisplaySnackBar(
    scaffoldState: ScaffoldState,
    handleDatabaseActions: () -> Unit,
    onUndoClicked: (Action) -> Unit,
    taskTitle: String,
    action: Action
) {
    handleDatabaseActions()

    val actionText = getActionLabel(action = action)
    val messageText = getSnackBarMessage(action = action, taskTitle = taskTitle)
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = action) {
        scope.launch {
            if (action != Action.NO_ACTION) {
                val snackBarResult = scaffoldState.snackbarHostState.showSnackbar(
                    message = messageText,
                    actionLabel = actionText
                )
                handleSnackBarAction(
                    action = action,
                    snackBarResult = snackBarResult,
                    onUndoClicked = onUndoClicked
                )
            }
        }
    }
}

@Composable
private fun getSnackBarMessage(action: Action, taskTitle: String): String {
    return when (action) {
        Action.DELETE_ALL -> stringResource(id = R.string.snack_bar_message_all_tasks_deleted)
        else -> "${action.name}: $taskTitle"
    }
}

@Composable
private fun getActionLabel(action: Action): String {
    return if (action.name == "DELETE") {
        stringResource(id = R.string.action_undo)
    } else {
        stringResource(id = R.string.generic_ok)
    }
}

private fun handleSnackBarAction(
    action: Action,
    snackBarResult: SnackbarResult,
    onUndoClicked: (Action) -> Unit
) {
    if (snackBarResult == SnackbarResult.ActionPerformed &&
            action == Action.DELETE) {
        onUndoClicked(Action.UNDO)
    }
}
