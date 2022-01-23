package com.example.pekomon.todoapp.ui.views.todotask

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.pekomon.todoapp.R
import com.example.pekomon.todoapp.extensions.topAppBarBackgroundColor
import com.example.pekomon.todoapp.extensions.topAppBarContentColor
import com.example.pekomon.todoapp.util.Action

@Composable
fun TodoTaskAppBar(
    navigateToListScreen: ((Action) -> Unit)
) {
    NewTodoTaskAppBar(navigateToListScreen = navigateToListScreen)
}

@Composable
fun NewTodoTaskAppBar(
    navigateToListScreen: ((Action) -> Unit)
) {
    TopAppBar(
        navigationIcon = {
          BackAction(onBackClicked = navigateToListScreen )
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
fun BackAction(
    onBackClicked: ((Action) -> Unit)
 ) {
    IconButton(onClick = { onBackClicked(Action.NO_ACTION) })  {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = stringResource(id = R.string.appbar_back_arrow),
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
fun PreviewTodoTaskAppBar() {
    NewTodoTaskAppBar(
        navigateToListScreen = {}
    )
}
