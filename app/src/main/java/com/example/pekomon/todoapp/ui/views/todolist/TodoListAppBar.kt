package com.example.pekomon.todoapp.ui.views.todolist

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.pekomon.todoapp.R
import com.example.pekomon.todoapp.extensions.topAppBarBackgroundColor
import com.example.pekomon.todoapp.extensions.topAppBarColor

@Composable
fun TodoListAppBar() {
    DefaultTodoListAppBar()
}

@Composable
fun DefaultTodoListAppBar() {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.appbar_title_tasks),
                color = MaterialTheme.colors.topAppBarColor
            )
        },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor
    )
}

@Composable
@Preview
fun PreviewDefaultTodoListAppBar() {
    DefaultTodoListAppBar()
}