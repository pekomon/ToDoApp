package com.example.pekomon.todoapp.ui.views.todolist

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.pekomon.todoapp.R
import com.example.pekomon.todoapp.extensions.fabBackgroundColor
import com.example.pekomon.todoapp.ui.viewmodel.TodoViewModel
import com.example.pekomon.todoapp.util.Consts.TASK_ID_ADD_NEW
import com.example.pekomon.todoapp.util.SearchAppBarState

@ExperimentalMaterialApi
@Composable
fun TodoListView(
    onListItemClicked: ((taskId: Int) -> Unit),
    todoViewModel: TodoViewModel
) {
    LaunchedEffect(key1 = true) {
        todoViewModel.updateAllTAsks()
    }

    val action by todoViewModel.action

    val allTasks by todoViewModel.allTasks.collectAsState()
    val searchAppBarState: SearchAppBarState by todoViewModel.searchAppBarState
    val searchTextState: String by todoViewModel.searchTextState

    todoViewModel.handleDatabaseActions(action = action)

    Scaffold(
        content = {
            TodoListContent(
                tasks = allTasks,
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
