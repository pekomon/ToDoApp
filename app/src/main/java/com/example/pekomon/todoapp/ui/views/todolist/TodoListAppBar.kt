package com.example.pekomon.todoapp.ui.views.todolist

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.example.pekomon.todoapp.R
import com.example.pekomon.todoapp.components.PriorityItem
import com.example.pekomon.todoapp.data.models.Priority
import com.example.pekomon.todoapp.extensions.topAppBarBackgroundColor
import com.example.pekomon.todoapp.extensions.topAppBarContentColor
import com.example.pekomon.todoapp.ui.theme.PADDING_LARGE
import com.example.pekomon.todoapp.ui.theme.TOP_APP_BAR_HEIGHT
import com.example.pekomon.todoapp.ui.theme.Typography
import com.example.pekomon.todoapp.ui.viewmodel.TodoViewModel
import com.example.pekomon.todoapp.util.SearchAppBarState
import com.example.pekomon.todoapp.util.TrailingIconState

@Composable
fun TodoListAppBar(
    todoViewModel: TodoViewModel,
    searchAppBarState: SearchAppBarState,
    searchTextState: String
) {

    when (searchAppBarState) {
        SearchAppBarState.CLOSED -> {
            DefaultTodoListAppBar(
                onSearchClicked = {
                    todoViewModel.openSearchAppBar()
                },
                onSortClicked = {},
                onDeleteAllClicked = {}
            )
        }
        else -> {
            SearchTodoListAppBar(
                text = searchTextState,
                onTextChanged = { newText ->
                    todoViewModel.searchTextChanged(newText = newText)
                },
                onCloseClicked = {
                    todoViewModel.closeSearchAppBar()
                    todoViewModel.searchTextChanged()
                },
                onSearchClicked = {}
            )
        }
    }
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
fun SearchTodoListAppBar(
    text: String,
    onTextChanged: ((String) -> Unit),
    onCloseClicked: (() -> Unit),
    onSearchClicked: (String) -> Unit
) {

    // TODO: Maybe handle this with boolean
    var trailingIconState by remember {
        mutableStateOf(TrailingIconState.READY_TO_DELETE)
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(TOP_APP_BAR_HEIGHT),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = MaterialTheme.colors.topAppBarBackgroundColor
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = {
                onTextChanged(it)
            },
            placeholder = {
                Text(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    text = stringResource(id = R.string.appbar_search_hint),
                    color = Color.White
                )
            },
            textStyle = TextStyle(
                color = MaterialTheme.colors.topAppBarContentColor,
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(
                    modifier = Modifier
                        .alpha(ContentAlpha.disabled),
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = stringResource(id = R.string.appbar_search_mode_search_icon_content_description),
                        tint = MaterialTheme.colors.topAppBarContentColor
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        when (trailingIconState) {
                            TrailingIconState.READY_TO_DELETE -> {
                                onTextChanged("")
                                trailingIconState = TrailingIconState.READY_TO_CLOSE
                            }
                            TrailingIconState.READY_TO_CLOSE -> {
                                if (text.isNotEmpty()) {
                                    onTextChanged("")
                                } else {
                                    onCloseClicked()
                                    trailingIconState = TrailingIconState.READY_TO_DELETE
                                }

                            }
                        }
                        //onCloseClicked()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = stringResource(id = R.string.close_icon_content_description),
                        tint = MaterialTheme.colors.topAppBarContentColor
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text)
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = MaterialTheme.colors.topAppBarContentColor,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = Color.Transparent
            )
        )
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

@Composable
@Preview
fun PreviewSearchTodoListAppBar() {
    SearchTodoListAppBar(
        text = "",
        onTextChanged = {},
        onCloseClicked = {},
        onSearchClicked = {}
    )
}