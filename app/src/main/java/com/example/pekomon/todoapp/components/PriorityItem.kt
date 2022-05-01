package com.example.pekomon.todoapp.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.pekomon.todoapp.data.models.Priority
import com.example.pekomon.todoapp.ui.theme.PADDING_LARGE
import com.example.pekomon.todoapp.ui.theme.PRIORITY_INDICATOR_SIZE
import com.example.pekomon.todoapp.ui.theme.Typography

@Composable
fun PriorityItem(priority: Priority) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Canvas(modifier = Modifier.size(PRIORITY_INDICATOR_SIZE)) {
            drawCircle(color = priority.color)
        }
        Text(
            modifier = Modifier.padding(start = PADDING_LARGE),
            text = priority.name,
            style = Typography.subtitle2,
            color = MaterialTheme.colors.onSurface
        )
    }
}

@Composable
@Preview
fun PreviewPriorityItem() {
    Column(
        verticalArrangement = Arrangement.spacedBy(PADDING_LARGE)
    ) {
        PriorityItem(priority = Priority.HIGH)
        PriorityItem(priority = Priority.MEDIUM)
        PriorityItem(priority = Priority.LOW)
        PriorityItem(priority = Priority.NONE)
    }

}