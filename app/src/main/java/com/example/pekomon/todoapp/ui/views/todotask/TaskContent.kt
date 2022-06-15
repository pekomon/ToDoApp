package com.example.pekomon.todoapp.ui.views.todotask

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.pekomon.todoapp.R
import com.example.pekomon.todoapp.components.PriorityDropDown
import com.example.pekomon.todoapp.data.models.Priority
import com.example.pekomon.todoapp.ui.theme.PADDING_LARGE
import com.example.pekomon.todoapp.ui.theme.PADDING_MEDIUM

@Composable
fun TaskContent(
    title: String,
    onTitleChanged: ((String) -> Unit),
    description: String,
    onDescriptionChanged: ((String) -> Unit),
    priority: Priority,
    onPriorityChanged: ((Priority) -> Unit),
    paddingValues: PaddingValues = PaddingValues(PADDING_LARGE)
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(
                start = PADDING_LARGE,
                end = PADDING_LARGE,
                top = PADDING_LARGE,
                bottom = paddingValues.calculateBottomPadding()
            )
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = title,
            onValueChange = { onTitleChanged(it) },
            label = { Text(text = stringResource(id = R.string.title_label)) },
            textStyle = MaterialTheme.typography.body1,
            singleLine = true
        )
        Spacer(modifier = Modifier.height(PADDING_MEDIUM))
        PriorityDropDown(
            priority = priority,
            onPrioritySelected = onPriorityChanged
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxSize(),
            value = description,
            onValueChange = { onDescriptionChanged(it) },
            label = { Text(text = stringResource(id = R.string.description_label)) },
            textStyle = MaterialTheme.typography.body1
        )
    }
}

@Preview
@Composable
fun PreviewTaskContent() {
    TaskContent(
        title = "fdsf",
        onTitleChanged = {},
        description = "descr123",
        onDescriptionChanged = {},
        priority = Priority.MEDIUM,
        onPriorityChanged = {}
    )
}