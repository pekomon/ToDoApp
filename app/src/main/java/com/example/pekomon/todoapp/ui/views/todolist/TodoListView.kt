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
    action: Action,
    onListItemClicked: ((taskId: Int) -> Unit),
    todoViewModel: TodoViewModel
) {
    LaunchedEffect(key1 = action) {
        todoViewModel.handleDatabaseActions(action = action)
    }

    val allTasks by todoViewModel.allTasks.collectAsState()
    val searchedTasks by todoViewModel.searchedTasks.collectAsState()
    val sortState by todoViewModel.sortState.collectAsState()
    val lowPriorityTasks by todoViewModel.lowPriorityTasks.collectAsState()
    val highPriorityTasks by todoViewModel.highPriorityTasks.collectAsState()
    val searchAppBarState: SearchAppBarState by todoViewModel.searchAppBarState
    val searchTextState: String by todoViewModel.searchTextState

    val scaffoldState = rememberScaffoldState()


    DisplaySnackBar(
        scaffoldState = scaffoldState,
        onComplete = { todoViewModel.action.value = it },
        onUndoClicked = {
                        todoViewModel.action.value = it
        },
        taskTitle = todoViewModel.title.value,
        action = action
    )

    Scaffold(
        scaffoldState = scaffoldState,
        content = { padding ->
            TodoListContent(
                padding = padding,
                allTasks = allTasks,
                searchedTasks = searchedTasks,
                lowPriorityTasks = lowPriorityTasks,
                highPriorityTasks = highPriorityTasks,
                sortState = sortState,
                searchAppBarState = searchAppBarState,
                onSwipeToDelete = { action, toDoTask ->
                                  todoViewModel.action.value = action
                    todoViewModel.updateUI(task = toDoTask)
                },
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
fun DisplaySnackBar(
    scaffoldState: ScaffoldState,
    onComplete: (Action) -> Unit,
    onUndoClicked: (Action) -> Unit,
    taskTitle: String,
    action: Action
) {
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
                onComplete(Action.NO_ACTION)
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
