package com.example.pekomon.todoapp.ui.views.todolist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.pekomon.todoapp.R
import com.example.pekomon.todoapp.data.models.Priority
import com.example.pekomon.todoapp.data.models.ToDoTask
import com.example.pekomon.todoapp.extensions.taskListItemBackGroundColor
import com.example.pekomon.todoapp.extensions.taskListItemTextColor
import com.example.pekomon.todoapp.ui.theme.*
import com.example.pekomon.todoapp.util.Action
import com.example.pekomon.todoapp.util.Consts.LIST_ITEM_ANIMATION_DURATION_MILLIS
import com.example.pekomon.todoapp.util.Result
import com.example.pekomon.todoapp.util.SearchAppBarState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun TodoListContent(
    // TODO: List selection logic should be done on upper level
    padding: PaddingValues,
    allTasks: Result<List<ToDoTask>>,
    searchedTasks: Result<List<ToDoTask>>,
    lowPriorityTasks: List<ToDoTask>,
    highPriorityTasks: List<ToDoTask>,
    sortState: Result<Priority>,
    searchAppBarState: SearchAppBarState,
    onSwipeToDelete: (Action, ToDoTask) -> Unit,
    onItemClicked: ((taskId: Int) -> Unit)
) {
    if (sortState is Result.Success) {
        when {
            searchAppBarState == SearchAppBarState.TRIGGERED -> {
                if (searchedTasks is Result.Success) {
                    HandleListContent(
                        tasks = searchedTasks.data,
                        onSwipeToDelete = onSwipeToDelete,
                        onItemClicked = onItemClicked
                    )
                }
            }
            sortState.data == Priority.NONE -> {
                if (allTasks is Result.Success) {
                    HandleListContent(
                        tasks = allTasks.data,
                        onSwipeToDelete = onSwipeToDelete,
                        onItemClicked = onItemClicked
                    )
                }
            }
            sortState.data == Priority.LOW -> {
                HandleListContent(
                    tasks = lowPriorityTasks,
                    onSwipeToDelete = onSwipeToDelete,
                    onItemClicked = onItemClicked
                )
            }
            sortState.data == Priority.HIGH -> {
                HandleListContent(
                    tasks = highPriorityTasks,
                    onSwipeToDelete = onSwipeToDelete,
                    onItemClicked = onItemClicked
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HandleListContent(
    tasks: List<ToDoTask>,
    onSwipeToDelete: (Action, ToDoTask) -> Unit,
    onItemClicked: (taskId: Int) -> Unit
) {
    if (tasks.isNotEmpty()) {
        DisplayTasks(
            tasks = tasks,
            onSwipeToDelete = onSwipeToDelete,
            onItemClicked = onItemClicked
        )
    } else {
        EmptyListContent()
    }
}

@OptIn(ExperimentalAnimationApi::class)
@ExperimentalMaterialApi
@Composable
fun DisplayTasks(
    tasks: List<ToDoTask>,
    onSwipeToDelete: (Action, ToDoTask) -> Unit,
    onItemClicked: ((taskId: Int) -> Unit)
) {
    LazyColumn {
        items(
            items = tasks,
            key = { task ->
                task.id
            }
        ) { task ->

            val dismissState = rememberDismissState()
            val dismissDirection = dismissState.dismissDirection
            val isDismissed = dismissState.isDismissed(DismissDirection.EndToStart)
            if (isDismissed && dismissDirection == DismissDirection.EndToStart) {
                val scope = rememberCoroutineScope()
                scope.launch {
                    // Wait for animation
                    delay(LIST_ITEM_ANIMATION_DURATION_MILLIS.toLong())
                    onSwipeToDelete(Action.DELETE, task)
                }

            }

            val degrees by animateFloatAsState(
                targetValue = if (dismissState.targetValue == DismissValue.Default) {
                    0f
                } else {
                    -45f
                }
            )

            var itemAppeared by remember {
                mutableStateOf(false)
            }
            LaunchedEffect(key1 = true) {
                itemAppeared = true
            }

            AnimatedVisibility(
                visible = itemAppeared && ! isDismissed,
                enter = expandVertically(
                    animationSpec = tween(
                        durationMillis = LIST_ITEM_ANIMATION_DURATION_MILLIS
                    )
                ),
                exit = shrinkVertically(
                    animationSpec = tween(
                        durationMillis = LIST_ITEM_ANIMATION_DURATION_MILLIS
                    )
                )
            ) {
                SwipeToDismiss(
                    state = dismissState,
                    directions = setOf(DismissDirection.EndToStart),
                    dismissThresholds = {
                        FractionalThreshold(fraction = 0.2f)
                    },
                    background = {
                        SwipeToDeleteBackground(animationDegrees = degrees)
                    },
                    dismissContent = {
                        TodoListItem(
                            toDoTask = task,
                            onClicked = onItemClicked
                        )
                    }
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun TodoListItem(
    toDoTask: ToDoTask,
    onClicked: ((taskId: Int) -> Unit)
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = MaterialTheme.colors.taskListItemBackGroundColor,
        shape = RectangleShape,
        elevation = LIST_ITEM_ELEVATION,
        onClick = {
            onClicked(toDoTask.id)
        }
    ) {
        Column(
            modifier = Modifier
                .padding(PADDING_LARGE)
                .fillMaxWidth()
        ) {
            Row {
                Text(
                    modifier = Modifier.weight(8f),
                    text = toDoTask.title,
                    color = MaterialTheme.colors.taskListItemTextColor,
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Canvas(
                        modifier = Modifier
                            .size(PRIORITY_INDICATOR_SIZE)
                    ) {
                        drawCircle(
                            color = toDoTask.priority.color
                        )
                    }
                }

            }
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = toDoTask.description,
                color = MaterialTheme.colors.taskListItemTextColor,
                style = MaterialTheme.typography.subtitle1,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun SwipeToDeleteBackground(animationDegrees: Float) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(warningColor)
            .padding(horizontal = PADDING_EXTRA_LARGE),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            modifier = Modifier.rotate(degrees = animationDegrees),
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(id = R.string.delete_icon_content_description),
            tint = Color.White
        )
    }
}

@ExperimentalMaterialApi
@Composable
@Preview
fun PreviewTodoListItem() {
    TodoListItem(
        toDoTask = ToDoTask(
            id = 2,
            title = "My Task",
            description = "Some text here",
            priority = Priority.MEDIUM
        ),
        onClicked = {}
    )
}