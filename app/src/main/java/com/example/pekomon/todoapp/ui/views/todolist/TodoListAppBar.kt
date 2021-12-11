package com.example.pekomon.todoapp.ui.views.todolist

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.pekomon.todoapp.R
import com.example.pekomon.todoapp.components.PriorityItem
import com.example.pekomon.todoapp.data.models.Priority
import com.example.pekomon.todoapp.extensions.topAppBarBackgroundColor
import com.example.pekomon.todoapp.extensions.topAppBarContentColor
import com.example.pekomon.todoapp.ui.theme.PADDING_LARGE
import com.example.pekomon.todoapp.ui.theme.Typography

@Composable
fun TodoListAppBar() {
    DefaultTodoListAppBar(
        onSearchClicked = {},
        onSortClicked = {},
        onDeleteAllClicked = {}
    )
}

@Composable
fun DefaultTodoListAppBar(
    onSearchClicked: (() -> Unit),
    onSortClicked: ((Priority) -> Unit),
    onDeleteAllClicked: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.appbar_title_tasks),
                color = MaterialTheme.colors.topAppBarContentColor
            )
        },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
        actions = {
            TodoListAppBarActions(
                onSearchClicked = onSearchClicked,
                onSortClicked = onSortClicked,
                onDeleteAllClicked = onDeleteAllClicked
            )
        }
    )
}

@Composable
fun TodoListAppBarActions(
    onSearchClicked: (() -> Unit),
    onSortClicked: ((Priority) -> Unit),
    onDeleteAllClicked: (() -> Unit)

) {
    SearchAction(onClicked = onSearchClicked)
    SortAction(onSortClicked = onSortClicked)
    DeleteAllAction(onClicked = onDeleteAllClicked)
}

@Composable
fun SearchAction(
    onClicked: (() -> Unit)
) {
    IconButton(
        onClick = { onClicked() })
    {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = stringResource(id = R.string.appbar_search_action_content_description),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
    }
}

@Composable
fun SortAction(
    onSortClicked: ((Priority) -> Unit)
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }

    IconButton(
        onClick = { isExpanded = true }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_filter_list_24),
            contentDescription = stringResource(id = R.string.appbar_sort_action_content_description),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false }
        ) {
            DropdownMenuItem(
                onClick = {
                    isExpanded = false
                    onSortClicked(Priority.LOW)
                }
            ) {
                PriorityItem(priority = Priority.LOW)
            }
            DropdownMenuItem(
                onClick = {
                    isExpanded = false
                    onSortClicked(Priority.HIGH)
                }
            ) {
                PriorityItem(priority = Priority.HIGH)
            }
            DropdownMenuItem(
                onClick = {
                    isExpanded = false
                    onSortClicked(Priority.NONE)
                }
            ) {
                PriorityItem(priority = Priority.NONE)
            }
        }
    }
}

@Composable
fun DeleteAllAction(
    onClicked: (() -> Unit)
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }
    IconButton(
        onClick = { isExpanded = true }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_more_vert_24),
            contentDescription = stringResource(
                id = R.string.appbar_delete_all_action_content_description
            ),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false }
        ) {
            DropdownMenuItem(
                onClick = {
                    isExpanded = false
                    onClicked()
                }
            ) {
                Text(
                    modifier = Modifier.padding(start = PADDING_LARGE),
                    text = stringResource(id = R.string.appbar_menu_item_delete_all),
                    style = Typography.subtitle2
                )
            }
        }
    }
}

@Composable
@Preview
fun PreviewDefaultTodoListAppBar() {
    DefaultTodoListAppBar(
        onSearchClicked = {},
        onSortClicked = {},
        onDeleteAllClicked = {}
    )
}
