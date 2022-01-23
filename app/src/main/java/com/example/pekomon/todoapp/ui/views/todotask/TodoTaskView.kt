package com.example.pekomon.todoapp.ui.views.todotask

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import com.example.pekomon.todoapp.util.Action

@Composable
fun TodoTaskView(
    navigateToListScreen: ((Action) -> Unit)
) {
    Scaffold(
        topBar = {
            TodoTaskAppBar(navigateToListScreen = navigateToListScreen)
        },
        content = {}
    )
}