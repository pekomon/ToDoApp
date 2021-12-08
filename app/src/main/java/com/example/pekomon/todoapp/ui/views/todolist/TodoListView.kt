package com.example.pekomon.todoapp.ui.views.todolist

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TodoListView(
    onClick: ((Int) -> Unit)
) {
    Scaffold(
        floatingActionButton = {
            ListFab(onClick = onClick)
        }
    ) {

    }
}

@Composable
fun ListFab(
    onClick: ((Int) -> Unit)
) {
    FloatingActionButton(
        onClick = { onClick(-1) }
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "Add new task",
            tint = Color.White
        )
    }
}

@Composable
@Preview
fun PreviewTodoListView() {
    TodoListView(onClick = {})
}