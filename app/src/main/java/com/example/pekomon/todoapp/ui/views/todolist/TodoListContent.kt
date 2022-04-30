package com.example.pekomon.todoapp.ui.views.todolist

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.pekomon.todoapp.data.models.Priority
import com.example.pekomon.todoapp.data.models.ToDoTask
import com.example.pekomon.todoapp.extensions.taskListItemBackGroundColor
import com.example.pekomon.todoapp.extensions.taskListItemTextColor
import com.example.pekomon.todoapp.ui.theme.LIST_ITEM_ELEVATION
import com.example.pekomon.todoapp.ui.theme.PADDING_LARGE
import com.example.pekomon.todoapp.ui.theme.PRIORITY_INDICATOR_SIZE
import com.example.pekomon.todoapp.util.Result
import com.example.pekomon.todoapp.util.SearchAppBarState

@ExperimentalMaterialApi
@Composable
fun TodoListContent(
    // TODO: List selection logic should be done on upper level
    allTasks: Result<List<ToDoTask>>,
    searchedTasks: Result<List<ToDoTask>>,
    lowPriorityTasks: List<ToDoTask>,
    highPriorityTasks: List<ToDoTask>,
    sortState: Result<Priority>,
    searchAppBarState: SearchAppBarState,
    onItemClicked: ((taskId: Int) -> Unit)
) {
    if (sortState is Result.Success) {
        when {
            searchAppBarState == SearchAppBarState.TRIGGERED -> {
                if (searchedTasks is Result.Success) {
                    HandleListContent(
                        tasks = searchedTasks.data,
                        onItemClicked = onItemClicked
                    )
                }
            }
            sortState.data == Priority.NONE -> {
                if (allTasks is Result.Success) {
                    HandleListContent(
                        tasks = allTasks.data,
                        onItemClicked = onItemClicked
                    )
                }
            }
            sortState.data == Priority.LOW -> {
                HandleListContent(
                    tasks = lowPriorityTasks,
                    onItemClicked = onItemClicked
                )
            }
            sortState.data == Priority.HIGH -> {
                HandleListContent(
                    tasks = highPriorityTasks,
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
    onItemClicked: (taskId: Int) -> Unit
) {
    if (tasks.isNotEmpty()) {
        DisplayTasks(
            tasks = tasks,
            onItemClicked = onItemClicked
        )
    }
    else {
        EmptyListContent()
    }
}

@ExperimentalMaterialApi
@Composable
fun DisplayTasks(
    tasks: List<ToDoTask>,
    onItemClicked: ((taskId: Int) -> Unit)
) {
    LazyColumn {
        items(
            items = tasks,
            key = { task ->
                task.id
            }
        ) { task ->
            TodoListItem(
                toDoTask = task,
                onClicked = onItemClicked
            )
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

@ExperimentalMaterialApi
@Composable
@Preview
fun PreviewTodoListItem()  {
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